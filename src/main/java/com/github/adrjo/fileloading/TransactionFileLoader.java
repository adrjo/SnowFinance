package com.github.adrjo.fileloading;


import java.io.File;
import java.io.IOException;

public interface TransactionFileLoader {

    /**
     * Loads transactions from a file into the TransactionManager
     * @param file to read from
     * @return amount of transactions loaded
     * @throws IOException
     */
    int load(File file) throws IOException;
}
