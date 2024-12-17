package com.github.adrjo.commands.impl.account.summary;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.annotations.ImplementsMenu;
import com.github.adrjo.commands.annotations.RegisterCommand;
import com.github.adrjo.commands.menus.AccountCommandMenu;
import com.github.adrjo.commands.menus.SummaryCommandMenu;
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
        SnowFinance.instance.getController().setCommandMenu(new AccountCommandMenu());
    }

    private Map<Integer, Transaction> full() {
        return SnowFinance.instance.getTransactionManager().getAllTransactions();
    }
}
