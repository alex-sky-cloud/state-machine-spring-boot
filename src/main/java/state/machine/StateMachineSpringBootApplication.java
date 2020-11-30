package state.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.config.EnableStateMachine;

@SpringBootApplication
@EnableStateMachine
public class StateMachineSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(StateMachineSpringBootApplication.class, args);
    }

}
