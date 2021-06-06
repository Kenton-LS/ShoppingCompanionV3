package com.example.shoppingcompanionv3;

public class Contents
{
    String product, qty;

    public Contents()
    {
    }

    public Contents(String product, String qty)
    {
        this.product = product;
        this.qty = qty;
    }

    public String getProduct()
    {
        return product;
    }

    public String getQty()
    {
        return qty;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }

    public void setQty(String qty)
    {
        this.qty = qty;
    }

    public String ToString() { return "Item Name: " + product + "\n" + "Qty: " + qty; }
}
