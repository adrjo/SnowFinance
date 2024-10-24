package com.github.adrjo.commands;

public abstract class Command {

    private String name;
    private String desc;
    private int requiredArgs;


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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setRequiredArgs(int num) {
        this.requiredArgs = num;
    }
}
