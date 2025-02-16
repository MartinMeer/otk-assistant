plugins {
    java
    war
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    //checkstyle
    id("com.github.ben-manes.versions") version "0.51.0"
}

group = "org.martinmeer"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.war {
    archiveFileName.set("otkassist.war") // Название WAR-файла
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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-freemarker")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    //developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("org.postgresql:postgresql")

    //providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

    // Thymeleaf
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    //Spring-core
    //Spring-web
    //Jackson
    //JDBC
    providedRuntime("org.apache.tomcat.embed:tomcat-embed-core") // Удаляем встроенный Tomcat
    providedCompile("javax.servlet:javax.servlet-api:4.0.1") // Добавляем сервлет API
    //JPA

    //Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
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

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    outputs.dir(project.extra["snippetsDir"]!!)

    jvmArgs("-javaagent:${mockitoAgent.asPath}")

}

tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    dependsOn(tasks.test)
}
