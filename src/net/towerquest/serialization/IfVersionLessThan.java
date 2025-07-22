package net.towerquest.serialization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface IfVersionLessThan {
	int major();
	int minor();
	ScaleBy conditional();
}
