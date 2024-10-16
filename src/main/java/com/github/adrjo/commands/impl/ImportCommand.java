package com.github.adrjo.commands.impl;

import com.github.adrjo.SnowFinance;
import com.github.adrjo.commands.Command;
import com.github.adrjo.commands.RegisterCommand;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RegisterCommand(
        name = "import",
        description = "imports transactions from Swedbank"
)
public class ImportCommand extends Command {

    @Override
    public void exec(String[] args) {
        if (args == null || args.length < 1) {
            System.err.println(this.getName() + ": " + this.getDesc());
            System.out.println("Usage: import path/to/transactionsexport.csv");
            return;
        }

        try {
            String swedbankExport = args[0];
            System.out.println(swedbankExport);

            AtomicInteger count = new AtomicInteger();
            Files.readAllLines(Path.of(swedbankExport), StandardCharsets.UTF_8).stream()
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

            System.out.println("Successfully imported " + count + " transactions");
        } catch (IOException e) {
            System.err.println("Error parsing export: " + e.getMessage());
        }
    }
}
