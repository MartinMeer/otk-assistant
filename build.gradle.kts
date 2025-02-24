plugins {
    java
    //war
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    //checkstyle
    id("com.github.ben-manes.versions") version "0.51.0"
    //id("nu.studer.dotenv") version "3.0.0"
    id("checkstyle")
}

group = "org.martinmeer"
version = "0.0.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}


repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

val mockitoAgent = configurations.create("mockitoAgent")
dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.projectlombok:lombok")

    //Spring-Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")

    configurations {
        all {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        }
    }


    //developmentOnly("org.springframework.boot:spring-boot-devtools")
    //developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")

    // Thymeleaf
    ///implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    implementation("org.springframework.boot:spring-boot-starter-web")
    /*{
        exclude(module = "spring-boot-starter-tomcat")
    }*/
    //implementation("org.springframework.boot:spring-boot-starter-tomcat")
    //providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")


    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    //testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    //testImplementation("org.springframework.security:spring-security-test")
    //testImplementation("org.testcontainers:junit-jupiter")
    //testImplementation("org.testcontainers:postgresql")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation(libs.mockito)
    mockitoAgent(libs.mockito) { isTransitive = false }


    //Logging
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    configurations {
        all {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        }
    }
    implementation("io.github.cdimascio:dotenv-java:3.1.0")
    implementation("com.puppycrawl.tools:checkstyle:10.21.3")

}

//tasks

/*tasks.war {
    archiveFileName.set("otkassist.war") // Название WAR-файла
}*/

tasks.test {
    useJUnitPlatform{
        excludeTags("excludeFromBuild") // Exclude tests with this tag
    }
    systemProperty("spring.profiles.active", "test")


    //environment("DB_USERNAME", System.getenv("DB_USERNAME"))
    //environment("DB_PASSWORD", System.getenv("DB_PASSWORD"))
    outputs.dir(project.extra["snippetsDir"]!!)

    jvmArgs("-javaagent:${mockitoAgent.asPath}")

}

tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    dependsOn(tasks.test)
}

