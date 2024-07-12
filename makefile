build-and-test:
	./mvnw clean install
	(cd examples/maven-example && ../../mvnw verify -e)

	open examples/maven-example/target/cluecumber-report/index.html

show-versions:
	./mvnw versions:display-dependency-updates -U -ntp
	./mvnw versions:display-plugin-updates -U -ntp