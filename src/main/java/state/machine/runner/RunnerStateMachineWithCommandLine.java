package state.machine.runner;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import state.machine.enums.BookEvents;
import state.machine.enums.BookStates;
import state.machine.utils.LoggingUtils;

@Component
public class RunnerStateMachineWithCommandLine implements CommandLineRunner {

    private static final Logger LOGGER = LoggingUtils.LOGGER;

    private final StateMachine<BookStates, BookEvents> stateMachine;

    @Autowired
    public RunnerStateMachineWithCommandLine(StateMachine<BookStates, BookEvents> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void run(String... args){

        stateMachine.start();
        boolean returnAccepted = stateMachine.sendEvent(BookEvents.RETURN);
        LOGGER.info("return accepted: " + returnAccepted);

        boolean borrowAccepted = stateMachine.sendEvent(BookEvents.BORROW);
        LOGGER.info("borrow accepted: " + borrowAccepted);

        stateMachine.stop();
    }
}
