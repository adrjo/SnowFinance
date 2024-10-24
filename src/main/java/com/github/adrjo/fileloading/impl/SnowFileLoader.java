package com.github.adrjo.fileloading.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.fileloading.TransactionFileLoader;
import com.github.adrjo.transactions.Transaction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnowFileLoader implements TransactionFileLoader {

    @Override
    public int load(File file) throws IOException {
        if (!file.exists()) return 0;

        AtomicInteger count = new AtomicInteger();
        Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).forEach(line -> {
            // Deal with string entries (descriptions may have commas in them, breaking the String.split())
            Pattern pattern = Pattern.compile("\"(.*?)\"");
            Matcher matcher = pattern.matcher(line);

            String lineWithoutStrings = String.join("", pattern.split(line));
            String desc = "";
            while (matcher.find()) {
                desc = matcher.group(1);
            }

            String[] split = lineWithoutStrings.split(",");
            int id = Integer.parseInt(split[0]);
            Transaction transaction = new Transaction(desc, Double.parseDouble(split[2]), Long.parseLong(split[3]));
            SnowFinance.instance.getTransactionManager().add(id, transaction);
            count.getAndIncrement();
        });
        return count.get();
    }
}
