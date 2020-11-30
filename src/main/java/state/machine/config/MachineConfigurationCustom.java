package state.machine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import state.machine.enums.BookEvents;
import state.machine.enums.BookStates;
import state.machine.logging.LoggingMachineListener;

import java.util.EnumSet;

@Configuration
public class MachineConfigurationCustom extends EnumStateMachineConfigurerAdapter<BookStates, BookEvents> {

    @Bean
    public StateMachine<BookStates, BookEvents> stateMachine(StateMachineListener<BookStates, BookEvents> listener) throws Exception {

        StateMachineBuilder.Builder<BookStates, BookEvents>  builder =
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
        stateMachine.addStateListener(listener);
        return stateMachine;
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<BookStates, BookEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new LoggingMachineListener());
    }

}
