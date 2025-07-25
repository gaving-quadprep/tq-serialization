package net.towerquest.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UseOrdinal {
	/**
	 * Specifies that, instead of an enum being saved as a string representation,
	 * it should be saved as an int using ordinal().
	 */
}
