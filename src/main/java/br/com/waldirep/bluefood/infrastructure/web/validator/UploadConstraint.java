package br.com.waldirep.bluefood.infrastructure.web.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.waldirep.bluefood.util.FileType;

@Documented
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
@Constraint(validatedBy = UploadValidator.class) // Classe que vai trabalhar em conjunto com a annotation
public @interface UploadConstraint {
	
	
	String message() default "Arquivo inválido";
	FileType[] acceptedTypes();
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
