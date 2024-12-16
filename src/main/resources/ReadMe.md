Here are some general comments for the project:

 - Fix pom.xml project name !
 - read a bit about java namespaces

1) We should use this API in the project:

https://github.com/HuobiRDCenter/huobi_Java

2) package names in java are always in lowercase without special characters (underscore allowed in some situations) but discouraged by some standards . Eg.: analysismethods
   https://google.github.io/styleguide/javaguide.html#s5.2.1-package-names


3) ObjectMapper usage is wrong: https://www.baeldung.com/jackson-object-mapper-tutorial

4) Data that you are fetching from REST Apis in JSON format should always be serialized to POJOS for Simplicity: https://www.baeldung.com/java-pojo-class




