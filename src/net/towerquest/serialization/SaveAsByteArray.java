package net.towerquest.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* for more compact storage */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SaveAsByteArray {

}
