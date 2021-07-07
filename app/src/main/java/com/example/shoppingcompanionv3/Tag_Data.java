package com.example.shoppingcompanionv3;

public class Tag_Data
{
    String tag;
    int amount;

    public Tag_Data(String tag, int amount) {
        this.tag = tag;
        this.amount = amount;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}