.PHONY: test, build
run-dist:
	./build/install/app/bin/app
mkBuild:
	./gradlew clean
	./gradlew build
lint:
	./gradlew checkstyleMain
dependency:
	./gradlew dependencyUpdates
doc:
	./gradlew javadoccgc
mkTest:
	./gradlew test
	./gradlew checkstyleMain
	./gradlew jacocoTestReport
