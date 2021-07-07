package com.example.shoppingcompanionv3;

public class Contents
{
    String product, qty, date, desc, tag;
    Boolean checked;

    public Contents()
    {
    }

    public Contents(String product, String qty, String date, String desc, String tag, Boolean checked)
    {
        this.product = product;
        this.qty = qty;
        this.date = date;
        this.desc = desc;
        this.tag = tag;
        this.checked = checked;
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
    public String getTag()
    {
        return tag;
    }
    public Boolean getChecked(){return checked;}

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
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    public void setChecked(Boolean check){this.checked = check;}

    public String ToString() { return "Item Name: " + product + "\n" + "Qty: " + qty; }
}
