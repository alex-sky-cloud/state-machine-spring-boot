package state.machine.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import state.machine.enums.BookEvents;
import state.machine.enums.BookStates;
import state.machine.logging.LoggingMachineListener;
import state.machine.utils.LoggingUtils;

import java.util.EnumSet;

import static state.machine.utils.LoggingUtils.getStateInfo;

@Configuration
public class MachineConfigurationCustom
        extends EnumStateMachineConfigurerAdapter<BookStates, BookEvents> {

    private static final Logger LOGGER = LoggingUtils.LOGGER;

    @Bean
    public StateMachine<BookStates, BookEvents>
    stateMachine(StateMachineListener<BookStates, BookEvents> listener)
            throws Exception {

        StateMachineBuilder.Builder<BookStates, BookEvents> builder =
                StateMachineBuilder.builder();

        builder.configureStates()
                .withStates()
                .initial(BookStates.AVAILABLE)
                .states(EnumSet.allOf(BookStates.class));

        builder.configureTransitions()
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.BORROWED)
                .event(BookEvents.BORROW)
                .and()
                .withExternal()
                .source(BookStates.BORROWED)
                .target(BookStates.AVAILABLE)
                .event(BookEvents.RETURN)
                .and()
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.IN_REPAIR)
                .event(BookEvents.START_REPAIR)
                .and()
                .withExternal()
                .source(BookStates.IN_REPAIR)
                .target(BookStates.AVAILABLE)
                .event(BookEvents.END_REPAIR);

        StateMachine<BookStates, BookEvents> stateMachine = builder.build();
        stateMachine.addStateListener(new LoggingMachineListener());
        return stateMachine;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<BookStates, BookEvents> config)
            throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }


    /*@Override
    public void configure(StateMachineStateConfigurer<BookStates, BookEvents> states) throws Exception {
        states.withStates().initial(BookStates.AVAILABLE)
                .state(BookStates.AVAILABLE, entryAction(), exitAction())
                .state(BookStates.BORROWED, entryAction(), exitAction())
                .state(BookStates.IN_REPAIR, entryAction(), exitAction());
    }

    @Bean
    public Action<BookStates, BookEvents> entryAction() {
        return eventsStateContext -> LOGGER.info("Entry action {} to get from {} to {}",
                eventsStateContext.getEvent(),
                getStateInfo(eventsStateContext.getSource()),
                getStateInfo(eventsStateContext.getTarget()));
    }

    @Bean
    public Action<BookStates, BookEvents> exitAction() {
        return eventsStateContext -> LOGGER.info("Exit action {} to get from {} to {}",
                eventsStateContext.getEvent(),
                getStateInfo(eventsStateContext.getSource()),
                getStateInfo(eventsStateContext.getTarget()));
    }*/

}


  /*  @Override
    public void configure(StateMachineTransitionConfigurer<BookStates, BookEvents> transitions)
            throws Exception {
        transitions
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.BORROWED)
                .event(BookEvents.BORROW)
                .and()
                .withExternal()
                .source(BookStates.BORROWED)
                .target(BookStates.AVAILABLE)
                .event(BookEvents.RETURN)
                .and()
                .withExternal()
                .source(BookStates.AVAILABLE)
                .target(BookStates.IN_REPAIR)
                .event(BookEvents.START_REPAIR)
                .and()
                .withExternal()
                .source(BookStates.IN_REPAIR)
                .target(BookStates.AVAILABLE)
                .event(BookEvents.END_REPAIR);
    }

*/