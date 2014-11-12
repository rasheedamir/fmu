package se.inera.fmu.interfaces.managing.rest.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@NotBlank
@NotNull
@Size(min=1, max=25)
@Pattern(regexp = "^[a-zA-Z_][a-zA-Z0-9_]*$")
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.LOCAL_VARIABLE }) // specifies where this validation can be used (Field, Method, Parameter etc)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
@ReportAsSingleViolation
// specifies if any of the validation fails, it will be reported as single validation
public @interface ValidateSortKey {
 
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
