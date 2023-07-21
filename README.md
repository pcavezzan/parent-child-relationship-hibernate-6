# StackOverFlow problem in hibernate 6.x with a parent child relationship using a fetch by @NaturalId

## Problem

We upgraded our stack from spring boot 2.7.x to 3.1.x in order to get an up-to-date technical stack.
As we know that hibernate 6.x is a big update, we are not surprised to get some problems.

While most of our upgrade went smoothly, we just detected a problem in our case because we're using a parent child
relationship with @NaturalId. Unfortunately, we noticed that while we're using a parent child entity and try to load an entity,
we get a stack overflow error.

## How to reproduce

We created a simple project to reproduce the problem. The project is available on github.

```shell
git clone 
mvn clean install
```

On main branch, you should get the error. If you go back by one commit, you should not get the error. (configured with
hibernate 5.6.x)
