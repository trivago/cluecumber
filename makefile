help: ## Show this help.
	@grep -hE '^[A-Za-z0-9_ \-]*?:.*##.*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
.PHONY: help

build-and-test: build test ## Build all and run demo tests
.Phony: build-and-test

build: ## Build all Cluecumber components
	./mvnw clean install -U -ntp
.Phony: build

test: ## Test locally and open the report
	./mvnw clean verify -U -ntp -f examples/maven-example/pom.xml
	open examples/maven-example/target/cluecumber-report/index.html
.Phony: test

show-versions: ## Show most recent dependency versions
	./mvnw versions:display-dependency-updates -U -ntp
	./mvnw versions:display-plugin-updates -U -ntp
.Phony: show-versions

set-maven-version: ## Change the version of the Maven wrapper
	@if test -z "$(MAVEN_VERSION)"; then echo "No MAVEN_VERSION set!"; exit 1; fi
	mvn wrapper:wrapper -Dmaven=${MAVEN_VERSION}
	@./mvnw --version
.PHONY: set-maven-version