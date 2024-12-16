package com.github.adrjo.commands.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementsMenus {
    ImplementsMenu[] value();
}
