package com.github.adrjo.commands.impl;

import com.github.adrjo.commands.RegisterCommand;

@RegisterCommand(
        name = "summary",
        description = "View full transaction summary (balance, expenses, income) or optionally by year, month, day or week"
)
public class SummaryCommand {
}
