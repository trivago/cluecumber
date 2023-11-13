build-and-test:
	mvn clean install
	mvn verify -f=examples/maven-example
	open examples/maven-example/target/cluecumber-report/index.html