package com.github.adrjo.commands.annotations.impl;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(ImplementsMenus.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementsMenu {
    Class<?> value();
}
