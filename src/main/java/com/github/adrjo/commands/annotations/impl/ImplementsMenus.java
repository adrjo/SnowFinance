package com.github.adrjo.commands.annotations.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ImplementsMenus {
    ImplementsMenu[] value();
}
