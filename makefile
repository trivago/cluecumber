build-and-test:
	mvn clean install -DskipTests=true
	mvn verify -f=examples/maven-example
	open examples/maven-example/target/cluecumber-report/index.html