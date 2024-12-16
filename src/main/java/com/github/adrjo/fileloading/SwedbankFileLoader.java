package com.github.adrjo.fileloading;

import com.github.adrjo.SnowFinance;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SwedbankFileLoader implements TransactionFileLoader {

    @Override
    public int load(File file) throws IOException {
        AtomicInteger count = new AtomicInteger();
        Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).stream()
                .skip(2) // skip the header
                .forEach(line -> {
                    try {
                        // Deal with string entries (descriptions may have commas in them, breaking the String.split())
                        Pattern pattern = Pattern.compile("\"(.*?)\"");
                        Matcher matcher = pattern.matcher(line);

                        String lineWithoutStrings = String.join("", pattern.split(line));
                        List<String> strings = new ArrayList<>();
                        while (matcher.find()) {
                            String string = matcher.group(1);
                            strings.add(string);
                        }
                        final String[] entries = lineWithoutStrings.split(",");

                        String dateString = entries[6];
                        String desc = strings.get(2);
                        double amount = Double.parseDouble(entries[10]);

                        long timestamp = new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime();
                        SnowFinance.instance.getTransactionManager().add(desc, amount, timestamp);
                        count.incrementAndGet();
                    } catch (Exception e) {
                        System.err.println("Error parsing transaction: " + e.getMessage());
                    }
                });
        return count.get();
    }
}
