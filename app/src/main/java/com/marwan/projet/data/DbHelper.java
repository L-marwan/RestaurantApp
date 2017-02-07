package com.marwan.projet.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.marwan.projet.model.MyMenu;
import com.marwan.projet.model.MyMenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *
 * Helper class that offers all the methods needed to access to the dataBase
 *
 * Created by marwan on 2/7/2016.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "menu";

    private static final String TABLE_ITEMS = "items";

    private static final String KEY_ID ="id";
    private static final String KEY_ITEM_NAME ="ItemName";
    private static final String KEY_DESC = "description";
    private static final String KEY_PRICE = "price";
    private static final String KEY_CAT = "category";



    public DbHelper (Context context){
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //the price will be stored as int, as the smallest currency, in this case "centime"
        // example : price is 15.5 DH it will be stored as 155
        // this seems to be the best practice to store pricing
        String sql= "CREATE TABLE IF NOT EXISTS " + TABLE_ITEMS+ "("
                +KEY_ID + " TEXT PRIMARY KEY, "
                +KEY_ITEM_NAME + " TEXT, "
                +KEY_DESC +" TEXT, "
                +KEY_PRICE +" INTEGER ,"
                +KEY_CAT +" TEXT)";
        db.execSQL(sql);
        Log.e("DBHELPER","db created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ITEMS);
        onCreate(db);
    }


    //********** helper methods ********//

    /**
     * helper method to add one item to the DB
     * @param item
     */
    private void addItem (MyMenuItem item){

        SQLiteDatabase db =getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,item.getId());
        values.put(KEY_ITEM_NAME,item.getName());
        values.put(KEY_DESC,item.getDesc());
        values.put(KEY_PRICE,FromPrice(item.getPrice()));
        values.put(KEY_CAT ,item.getCategorie());

        db.insert(TABLE_ITEMS, null, values);
        db.close();
        Log.e("DBHELPER","item"+item.getName()+"added");

    }

    /**
     * add all the items inside the menu object
     * @param menu
     */
    public void addItems (MyMenu menu){
        //remove all old rows
        getWritableDatabase().execSQL("delete from "+ TABLE_ITEMS);

        for (MyMenuItem item : menu.getItems()){
            addItem(item);
        }
    }

    /**
     * get all the items of a certain category
     * @param cat
     * @return
     */
    public ArrayList<MyMenuItem> getItemsByCategory(String cat){
        ArrayList<MyMenuItem> res = new ArrayList<>();
        Log.e("DBHELPER","here");
        SQLiteDatabase db = getWritableDatabase();
        String sql= "SELECT * FROM " + TABLE_ITEMS + " WHERE " + KEY_CAT +" = ?";
        String [] args = {cat};
        Log.e("DBHELPER","start of getbycat");
        Cursor c = db.rawQuery(sql,args);
        if(c.moveToFirst()){
            do{
                MyMenuItem item = new MyMenuItem();
                item.setId(c.getString(0));

                Log.e("GETBY",c.getString(1));

                item.setName(c.getString(1));
                item.setDesc(c.getString(2));
                item.setPrice(toPrice(c.getInt(3)));
                item.setCategorie(c.getString(4));
                // forgetting this "add"  made me waste 2 hours of my life gg
                res.add(item);
            } while(c.moveToNext());
        }
        db.close();
        return res;
    }

    public ArrayList<String> getCategories(){
        ArrayList<String> res = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT DISTINCT "+KEY_CAT +" FROM "+TABLE_ITEMS;
        Cursor c = db.rawQuery(sql,null);

        if(c.moveToFirst()){
            do{
                res.add(c.getString(0));
            } while(c.moveToNext());
        }

        return res;
    }





    private int FromPrice(BigDecimal price){
        return (int)(price.doubleValue()*100);
    }

    private BigDecimal toPrice (int i){
        return new BigDecimal(i/100.0);
    }
























}
