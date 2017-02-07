package com.marwan.projet.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order{
    private String id;
    private int table;
    private BigDecimal total;
    private Date date;
    private List<MyMenuItem> items;
    private String state ="new";


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<MyMenuItem> getItems() {
        return items;
    }
    public void setItems(List<MyMenuItem> items) {
        this.items = items;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public int getTable() {
        return table;
    }
    public void setTable(int table) {
        this.table = table;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void addItem(MyMenuItem item){
        if(items == null) items = new ArrayList<>();
        items.add(item);
    }





}
