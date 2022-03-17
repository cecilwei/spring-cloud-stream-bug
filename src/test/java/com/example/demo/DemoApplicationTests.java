package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.support.MessageBuilder;
import org.xml.sax.InputSource;

import java.util.logging.Logger;

class DemoApplicationTests {
    @Test
    void test() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        DemoApplication.class))
                .run("--spring.profile.active=test",
                        "--spring.cloud.function.definition=function|sink",
                        "--spring.cloud.stream.function.bindings.function|sink-in-0=input",
                        "--spring.cloud.stream.function.bindings.function|sink-out-0=output")) {
            PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().setName("Bob").build();
            InputDestination source = context.getBean(InputDestination.class);
            OutputDestination output = context.getBean(OutputDestination.class);
            source.send(MessageBuilder.withPayload(person.toByteArray()).build());
        }
    }
}
