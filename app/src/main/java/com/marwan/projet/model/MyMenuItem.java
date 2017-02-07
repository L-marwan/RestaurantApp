package com.marwan.projet.model;

import java.math.BigDecimal;

/**
 * Created by marwan on 1/10/2016.
 */
public class MyMenuItem {

    private String id;
    private String name;
    private String desc;
    private BigDecimal price;
    private String categorie;

    public MyMenuItem() {
        // TODO Auto-generated constructor stub
    }

    public MyMenuItem(String id, String name, String desc, BigDecimal price ,String categorie) {
        super();
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.categorie = categorie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}