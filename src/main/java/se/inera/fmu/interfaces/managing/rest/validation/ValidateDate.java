package se.inera.fmu.interfaces.managing.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NotNull
@Min(1)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE }) // specifies where this validation can be used (Field, Method, Parameter etc)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
// specifies if any of the validation fails, it will be reported as single validation
public @interface ValidateDate {
 
    /**
     * This is the key to message will that will be looked in validation.properties for validation
     * errors
     * 
     * @return the string
     */
    String message() default "{invalid.landsting.kod}";
    
 
    Class[] groups() default {};
 
    Class[] payload() default {};
}
