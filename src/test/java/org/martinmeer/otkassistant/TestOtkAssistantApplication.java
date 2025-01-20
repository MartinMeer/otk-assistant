package org.martinmeer.otkassistant;

import org.springframework.boot.SpringApplication;

public class TestOtkAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.from(OtkAssistantApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
