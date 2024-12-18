package com.github.adrjo.transactions;

public record Transaction(String description, double amount, long timestamp) {
}
