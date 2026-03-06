package org.example.springbootassignment.enums;

public enum AccountType {
    SavingsAccount("Savings Account"),
    CurrentAccounts("Current Accounts"),
    FixedDeposits("Fixed Deposits"),
    Investment("Investment");



    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }
}
