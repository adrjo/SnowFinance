import com.github.adrjo.transactions.Transaction;
import com.github.adrjo.transactions.TransactionManager;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Test
    void balanceGetTest() {
        TransactionManager manager = new TransactionManager();

        manager.addNow("Test1", 800);
        manager.addNow("Test2", -700);

        assertEquals(100, manager.getBalance());
    }

    @Test
    void testAddTransaction() {
        TransactionManager manager = new TransactionManager();
        int listSize = manager.getAllTransactions().size();
        manager.addNow("Test", 1000);

        assertEquals(listSize + 1, manager.getAllTransactions().size());
    }

    @Test
    void newTransactionTest() {
        TransactionManager manager = new TransactionManager();
        Transaction transaction = new Transaction("Test", 100, 1000);
        manager.add("Test", 100, 1000);

        Transaction transactionFromManager = manager.get(0);
        assertEquals(transaction.desc(), transactionFromManager.desc());
        assertEquals(transaction.amt(), transactionFromManager.amt());
        assertEquals(transaction.timestamp(), transactionFromManager.timestamp());
    }


    @Test
    void testDateRange() {
        TransactionManager manager = new TransactionManager();
        manager.add("Test", 1000, 1000);
        manager.add("Test2", 200, 1999);
        manager.add("Test3", 300, 2000);

        Map<Integer, Transaction> filteredTransactions = manager.getTransactionsBetween(1500, 2000);

        // Test2 should be the only entry in the map
        assertEquals(1, filteredTransactions.size());
        assertEquals("Test2", filteredTransactions.values().iterator().next().desc());
    }

    @Test
    public void testRemoveTransaction() {
        TransactionManager manager = new TransactionManager();
        manager.addNow("Test", 1500);
        int id = manager.getAllTransactions().entrySet().iterator().next().getKey();

        assertEquals(1500, manager.getBalance());
        // remove transaction
        manager.remove(id);

        //check that transaction map and balance is empty
        assertEquals(0, manager.getAllTransactions().size());
        assertEquals(0, manager.getBalance());
    }
}
