mvnw clean
mvnw package
mvnw exec:java -Dexec.mainClass="SimpleProducer"
mvnw exec:java -Dexec.mainClass="SimpleConsumer"