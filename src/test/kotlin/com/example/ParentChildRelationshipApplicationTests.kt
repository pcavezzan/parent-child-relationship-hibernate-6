package com.example

import org.assertj.core.api.Assertions.assertThat
import org.hibernate.Session
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class ParentChildRelationshipApplicationTests {

	@Autowired
	private lateinit var entityManager: EntityManager


	@Test
	fun contextLoads() {
	}

	@Test
	fun `should find by primary key a parent entity with a parent child relationship with himself`() {
		val find = entityManager.find(Person::class.java, 1)

		assertThat(find).isNotNull
		assertThat(find.parent).isNull()
		assertThat(find.children).isNotEmpty
	}

	@Test
	fun `should load using natural id a parent entity with a parent child relationship with himself`() {
		val loadOptional = entityManager.unwrap(Session::class.java)
			.byNaturalId(Person::class.java)
			.using("email", "john@example.com")
			.loadOptional()

		assertThat(loadOptional).hasValueSatisfying {
			assertThat(it.parent).isNull()
			assertThat(it.children).isNotEmpty
		}
	}

	@Test
	fun `should request with jpql a parent entity with a parent child relationship with himself`() {
		val person = entityManager.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person::class.java)
			.setParameter("email", "john@example.com")
			.singleResult

		assertThat(person).isNotNull
		assertThat(person.parent).isNull()
		assertThat(person.children).isNotEmpty
	}

	@Test
	fun `should find by primary key a child entity with a parent child relationship with himself`() {
		val find = entityManager.find(Person::class.java, 2)

		assertThat(find).isNotNull
		assertThat(find.parent).isNotNull
		assertThat(find.children).isEmpty()
	}

	@Test
	fun `should load using natural id a child entity with a parent child relationship with himself`() {
		val loadOptional = entityManager.unwrap(Session::class.java)
			.byNaturalId(Person::class.java)
			.using("email", "foo@example.com")
			.loadOptional()

		assertThat(loadOptional).hasValueSatisfying {
			assertThat(it.parent).isNotNull
			assertThat(it.children).isEmpty()
		}
	}

	@Test
	fun `should request with jpql a child entity with a parent child relationship with himself`() {
		val person = entityManager.createQuery("SELECT p FROM Person p WHERE p.email = :email", Person::class.java)
			.setParameter("email", "foo@example.com")
			.singleResult

		assertThat(person).isNotNull
		assertThat(person.parent).isNotNull
		assertThat(person.children).isEmpty()
	}
}
