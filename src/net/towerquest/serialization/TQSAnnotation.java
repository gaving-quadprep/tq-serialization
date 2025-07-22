package net.towerquest.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.ANNOTATION_TYPE)
public abstract @interface TQSAnnotation {}
