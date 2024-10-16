package com.github.adrjo.commands;

public abstract class Command {

    private String name;
    private String desc;

    public abstract void exec(String[] args);

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }
}
