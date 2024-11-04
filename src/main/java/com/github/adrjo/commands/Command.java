package com.github.adrjo.commands;

public abstract class Command {

    private final String name;
    private final String desc;
    private final int requiredArgs;

    public Command(String name, String desc, int requiredArgs) {
        this.name = name;
        this.desc = desc;
        this.requiredArgs = requiredArgs;
    }

    public Command(String name, String desc) {
        this(name, desc, 0);
    }

    public void exec(String[] args) {
        if (args.length < requiredArgs) {
            throw new IllegalArgumentException("Not enough args, needed: " + requiredArgs + "\n"
                    + this.getName() + ": " + this.getDesc());
        }
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
