package tr.org.povatr.mongodb.annotation;

import tr.org.povatr.mongodb.enums.DeleteMode;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DeleteModeDocument {

    DeleteMode value() default DeleteMode.DEFAULT;
}
