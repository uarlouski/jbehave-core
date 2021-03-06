package org.jbehave.core.annotations;

import static org.jbehave.core.annotations.AfterScenario.Outcome.ANY;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AfterScenario {

    public enum Outcome {
        ANY, SUCCESS, FAILURE
    }
    
    /**
     * Signals that the annotated method should be invoked only upon given outcome
     * 
     * @return An Outcome upon which the method should be invoked 
     */
    Outcome uponOutcome() default ANY;

    /**
     * Signals that the annotated method should be invoked only upon given type
     *
     * @return A ScenarioType upon which the method should be invoked
     */
    ScenarioType uponType() default ScenarioType.ANY;

    /**
     * Lifecycle hooks with the higher order will be executed last
     *
     * @return order value
     */
    int order() default 0;

}
