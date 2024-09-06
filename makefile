build-and-test: build test
.Phony: build-and-test

build:
	./mvnw clean install -U -ntp
.Phony: build

test:
	./mvnw clean verify -U -ntp -f examples/maven-example/pom.xml
	open examples/maven-example/target/cluecumber-report/index.html
.Phony: test

show-versions:
	./mvnw versions:display-dependency-updates -U -ntp
	./mvnw versions:display-plugin-updates -U -ntp
.Phony: show-versions