package state.machine.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import state.machine.enums.BookEvents;
import state.machine.enums.BookStates;

@Component
public class RunnerStateMachineWithCommandLine implements CommandLineRunner {

    private final StateMachine<BookStates, BookEvents> stateMachine;

    @Autowired
    public RunnerStateMachineWithCommandLine(StateMachine<BookStates, BookEvents> stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void run(String... args){
        stateMachine.start();
        stateMachine.sendEvent(BookEvents.RETURN);
        stateMachine.sendEvent(BookEvents.BORROW);
        stateMachine.stop();
    }
}
