# StackOverFlow problem in hibernate 6.x with a parent child relationship using a fetch by @NaturalId

## Problem

We upgraded our stack from spring boot 2.7.x to 3.1.x in order to get an up-to-date technical stack.
As we know that hibernate 6.x is a big update, we are not surprised to get some problems.

While most of our upgrade went smoothly, we just detected a problem in our case because we're using a parent child
relationship with @NaturalId. Unfortunately, we noticed that while we're using a parent child entity and try to load an entity,
we get a stack overflow error:

```
java.lang.StackOverflowError
	at org.hibernate.sql.ast.tree.from.LazyTableGroup.getTableReference(LazyTableGroup.java:252)
	at org.hibernate.sql.ast.tree.from.ColumnReferenceQualifier.resolveTableReference(ColumnReferenceQualifier.java:37)
	at org.hibernate.metamodel.mapping.internal.BasicEntityIdentifierMappingImpl.resolveSqlSelection(BasicEntityIdentifierMappingImpl.java:267)
	at org.hibernate.metamodel.mapping.internal.BasicEntityIdentifierMappingImpl.generateFetch(BasicEntityIdentifierMappingImpl.java:416)
	at org.hibernate.sql.results.graph.FetchParent.generateFetchableFetch(FetchParent.java:108)
	at org.hibernate.sql.results.graph.DomainResultCreationState.visitIdentifierFetch(DomainResultCreationState.java:96)
	at org.hibernate.sql.results.graph.entity.AbstractEntityResultGraphNode.afterInitialize(AbstractEntityResultGraphNode.java:48)
	at org.hibernate.sql.results.graph.entity.internal.EntityFetchJoinedImpl.<init>(EntityFetchJoinedImpl.java:58)
	at org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping.buildEntityFetchJoined(ToOneAttributeMapping.java:1341)
	at org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping.lambda$generateFetch$0(ToOneAttributeMapping.java:1500)
	at org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping.withRegisteredAssociationKeys(ToOneAttributeMapping.java:1801)
	at org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping.generateFetch(ToOneAttributeMapping.java:1475)
	at org.hibernate.metamodel.mapping.internal.ToOneAttributeMapping.generateFetch(ToOneAttributeMapping.java:108)
	at org.hibernate.sql.results.graph.FetchParent.generateFetchableFetch(FetchParent.java:108)
	at org.hibernate.loader.ast.internal.AbstractNaturalIdLoader.visitFetches(AbstractNaturalIdLoader.java:369)
	at org.hibernate.loader.ast.internal.LoaderSqlAstCreationState.visitFetches(LoaderSqlAstCreationState.java:150)
	at org.hibernate.sql.results.graph.AbstractFetchParent.afterInitialize(AbstractFetchParent.java:32)
	at org.hibernate.sql.results.graph.entity.AbstractEntityResultGraphNode.afterInitialize(AbstractEntityResultGraphNode.java:65)
	at org.hibernate.sql.results.graph.entity.internal.EntityFetchJoinedImpl.<init>(EntityFetchJoinedImpl.java:58)
```

## How to reproduce

We created a simple project to reproduce the problem. The project is available on github.

### Requirements

* Docker installed because this project use testcontainers,
* Maven,
* Java 17

### Running the example

```shell
git clone https://github.com/pcavezzan/parent-child-relationship-hibernate-6.git
cd parent-child-relationship-hibernate-6
mvn clean test
```

On main branch, you should get the error. If you go back by one commit, you should not get the error. (configured with
hibernate 5.6.x)

```shell
git co HEAD^1
mvn clean test
```
