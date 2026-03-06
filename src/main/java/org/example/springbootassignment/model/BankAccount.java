package org.example.springbootassignment.model;

import jakarta.persistence.*;
import org.example.springbootassignment.enums.*;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_number_seq")
    private  long accountNumber;
    private String holderName;
    private double accountBalance;
    private AccountType accountType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer owner;
    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true,mappedBy = "bankAccount")
    private List<Transactions> transactionHistory;

    //for later use - open an account for an existing customer

    public BankAccount( Customer customer,String holderName, double accountBalance, AccountType accountType) {
        this.holderName = holderName;
        this.accountBalance = accountBalance;
        this.accountType = accountType;
        this.owner = customer;
        this.transactionHistory = new ArrayList<>();
    }
    public BankAccount( ) {
        this.transactionHistory = new ArrayList<>();
    }

    @SequenceGenerator(
            name = "account_number_seq",
            sequenceName = "account_number_seq",
            allocationSize = 1,
            initialValue = 1000000000
    )
    public long getAccountNumber() {
        return accountNumber;
    }


    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Transactions> getTransactionHistory() {
        return transactionHistory;
    }


    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than 0.");
            return false;
        }

        this.accountBalance += amount;
        Transactions transaction = new Transactions( amount, TransactionType.Credit);
        transactionHistory.add(transaction);

        System.out.println("Successfully deposited: " + amount);
        System.out.println("Current Balance:" + accountBalance);
        return true;
    }


    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than 0.");
            return false;
        }

        if (amount > accountBalance) {
            System.out.println("Insufficient funds. Current balance: " + accountBalance);
            return false;
        }

        this.accountBalance -= amount;
        Transactions transaction = new Transactions( amount, TransactionType.Debit);
        transactionHistory.add(transaction);

        System.out.println("Successfully withdrawn: " + amount);
        System.out.println("Current Balance: " + accountBalance);
        return true;
    }


    public void viewTransactionHistory() {
        System.out.println("\n" + "-".repeat(100));
        System.out.println("TRANSACTION HISTORY - " + accountNumber);
        System.out.println("Holder: " + holderName);
        System.out.println("-".repeat(100));

        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println(String.format("%-15s | %-25s | %-15s | %-10s", "Transaction ID", "Date & Time", "Amount", "Type"));
            System.out.println("-".repeat(100));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            for (Transactions transaction : transactionHistory) {
                System.out.println(String.format(
                        "%-15s | %-25s | $%-14.2f | %-10s",
                        transaction.getTransactionID(),
                        transaction.getTransactionDate().format(formatter),
                        transaction.getTransactionAmount(),
                        transaction.getTransactionType()
                ));
            }
        }
        System.out.println("-".repeat(100));
        System.out.println("Current Account Balance:" + String.format("%.2f", accountBalance));
        System.out.println("-".repeat(100) + "\n");
    }


    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber=" + accountNumber +
                ", holderName='" + holderName + '\'' +
                ", accountBalance=" + accountBalance +
                ", accountType=" + accountType +
                '}';
    }
}
