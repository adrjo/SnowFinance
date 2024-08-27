package com.github.adrjo.commands;

public abstract class Command {

    private final String name;
    private final String desc;

    public Command(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public abstract void exec(String[] args);

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
