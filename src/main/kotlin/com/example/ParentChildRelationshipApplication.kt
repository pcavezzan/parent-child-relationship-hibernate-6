package com.example

import jakarta.annotation.PreDestroy
import jakarta.persistence.*
import org.hibernate.annotations.NaturalId
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@SpringBootApplication
class ParentChildRelationshipApplication {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Bean
    fun commandLineRunner() = CommandLineRunner { args ->
        val john = Person().apply {
            name = "John"
            email = "john@example.com"
        }
        personRepository.save(john)
        val foo = Person().apply {
            name = "Foo"
            email = "foo@example.com"
            parent = john
        }
        personRepository.save(foo)
        personRepository.findAll().forEach { println(it.name) }
    }

    @PreDestroy
    fun onExit() {
        LOGGER.info("### Cleaning data on exit ###")
        personRepository.deleteAll()
    }

    private companion object {
        private val LOGGER = LoggerFactory.getLogger(ParentChildRelationshipApplication::class.java)
    }
}


fun main(args: Array<String>) {
    runApplication<ParentChildRelationshipApplication>(*args)
}


@Entity
@Table(name = "person")
class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @NaturalId
    @Column(name = "email", nullable = false, unique = true)
    var email: String = ""

    var name: String = ""

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "parent_id")
    var parent: Person? = null

    @OneToMany(mappedBy = "parent")
    var children: MutableSet<Person> = mutableSetOf()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return "Person(id=$id, email='$email', name='$name')"
    }

}

@Repository
interface PersonRepository : JpaRepository<Person, Int>
