package edu.sda26.springcourse.model.enums;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");

    private String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
