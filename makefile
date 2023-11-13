build-and-test:
	mvn clean install
	mvn verify -f=examples/maven-example -e
	open examples/maven-example/target/cluecumber-report/index.html