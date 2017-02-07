package com.marwan.projet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwan on 1/10/2016.
 */
public class MyMenu {

    private List<MyMenuItem> items = new ArrayList<>();

    public List<MyMenuItem> getItems() {
        return items;
    }

    public void setItems(List<MyMenuItem> items) {
        this.items = items;
    }
}
