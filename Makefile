.PHONY: test
run-dist:
	./build/install/app/bin/app
mkInstallDist:
	./gradlew clean
	./gradlew installDist
lint:
	./gradlew checkstyleMain
dependency:
	./gradlew dependencyUpdates
doc:
	./gradlew javadoccgc
run-test:
	./gradlew test
	./gradlew checkstyleMain
	./gradlew jacocoTestReport
