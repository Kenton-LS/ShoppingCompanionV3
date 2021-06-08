package com.example.shoppingcompanionv3;

public class Contents
{
    String product, qty, date, desc;

    public Contents()
    {
    }

    public Contents(String product, String qty, String date, String desc)
    {
        this.product = product;
        this.qty = qty;
        this.date = date;
        this.desc = desc;
    }

    public String getProduct()
    {
        return product;
    }
    public String getQty()
    {
        return qty;
    }
    public String getDate()
    {
        return date;
    }
    public String getDesc()
    {
        return desc;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }
    public void setQty(String qty)
    {
        this.qty = qty;
    }
    public void setDate(String date)
    {
        this.date = date;
    }
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String ToString() { return "Item Name: " + product + "\n" + "Qty: " + qty; }
}
