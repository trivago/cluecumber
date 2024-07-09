build-and-test:
	mvn clean install
	(cd examples/maven-example && mvn verify -e)
	open examples/maven-example/target/cluecumber-report/pages/scenario-detail/scenario_1.html

show-versions:
	mvn versions:display-dependency-updates -U -ntp
	mvn versions:display-plugin-updates -U -ntp