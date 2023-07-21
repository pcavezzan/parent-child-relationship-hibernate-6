# StackOverFlow problem in hibernate 6.x with a parent child relationship using a fetch by @NaturalId

## Problem Solved ✅

> Just don't forget to use all-open kotlin plugin
[Kotlin with JPA Hibernate](https://stackoverflow.com/questions/37733629/kotlin-with-jpa-hibernate-no-lazy-loading-without-open)
```xml
<plugin>
    <groupId>org.jetbrains.kotlin</groupId>
    <artifactId>kotlin-maven-plugin</artifactId>
    <configuration>
        <args>
            <arg>-Xjsr305=strict</arg>
        </args>
        <compilerPlugins>
            <plugin>spring</plugin>
            <plugin>jpa</plugin>
            <plugin>all-open</plugin>
        </compilerPlugins>
        <pluginOptions>
            <option>all-open:annotation=jakarta.persistence.Entity</option>
            <option>all-open:annotation=jakarta.persistence.Embeddable</option>
            <option>all-open:annotation=jakarta.persistence.MappedSuperclass</option>
        </pluginOptions>
    </configuration>
    <dependencies>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-allopen</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jetbrains.kotlin</groupId>
            <artifactId>kotlin-maven-noarg</artifactId>
            <version>${kotlin.version}</version>
        </dependency>
    </dependencies>
</plugin>
```
