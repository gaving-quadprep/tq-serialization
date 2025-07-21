package net.towerquest.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in an {@code Serializable} to specify that, instead of serializing the field within the object,
 * it should keep it seperate, and just add a link to it.
 * 
 * <p> Example: The {@code FollowingAIComponent} has a {@code target} field, which stores the {@code Entity}
 * that it's following. Instead of saving a copy of the entity, it should use the {@code Pointer} interface
 * to keep track of it. This way, when the level is deserialized, the entity will continue following its target.
 * 
 * <p> Counter-example: An entity uses a {@code Color} to determine what tint should be applied to it.
 * When the entity is created, it sets its color to a default value which is public static final.
 * If it uses the Pointer annotation, and someone tries to change its color, it will change the colors
 * of all other entities.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pointer {
	
}
