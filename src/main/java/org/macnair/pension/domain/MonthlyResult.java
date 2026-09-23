package org.macnair.pension.domain;

public class MonthlyResult {
    private int month;
    private double balance;

    public MonthlyResult(int month, double balance) {
        this.month = month;
        this.balance = balance;
    }

    public int getMonth() { return month; }
    public double getBalance() { return balance; }
}
