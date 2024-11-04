package com.github.adrjo.commands.impl.summary;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.impl.ImplementsMenu;
import com.github.adrjo.commands.annotations.impl.RegisterCommand;
import com.github.adrjo.commands.menus.impl.MainCommandMenu;
import com.github.adrjo.commands.menus.impl.SummaryCommandMenu;
import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.util.TransactionUtil;

import java.util.Map;

@ImplementsMenu(SummaryCommandMenu.class)
@RegisterCommand(name = "5", description = "")
public class FullSummaryCommand extends Command {
    public FullSummaryCommand(String name, String desc, int requiredArgs) {
        super(name, desc, requiredArgs);
    }

    @Override
    public void exec(String[] args) {
        super.exec(args);

        Map<Integer, Transaction> transactions = full();

        TransactionUtil.printTransactionInfoFor(transactions);

        System.out.println();
        TransactionUtil.printStats(transactions);
        SnowFinance.instance.getController().setCommandMenu(new MainCommandMenu());
    }

    private Map<Integer, Transaction> full() {
        return SnowFinance.instance.getTransactionManager().getAllTransactions();
    }
}
