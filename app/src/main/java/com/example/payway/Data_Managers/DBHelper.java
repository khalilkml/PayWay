package com.example.payway.Data_Managers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Product_user.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Define the SQL statement to create the table with the new column

    private static final String SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE " + ClientProductContract.ProductEntry.TABLE_PRODUCTS+ " (" +
                    ClientProductContract.ProductEntry.COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY," +
                    ClientProductContract.ProductEntry.COLUMN_PRODUCT_NAME + " TEXT," +
                    ClientProductContract.ProductEntry.COLUMN_PRODUCT_DESCRIPTION + " TEXT," +
                    ClientProductContract.ProductEntry.COLUMN_PRODUCT_PRICE + " TEXT," +
                    ClientProductContract.ProductEntry.COLUMN_PRODUCT_PAST_PRICE + " TEXT," +
                    ClientProductContract.ProductEntry.COLUMN_IS_FAVORITE + " TEXT)" ;

    private static final String SQL_CREATE_CLIENTS_TABLE =
            "CREATE TABLE " + ClientProductContract.ClientEntry.TABLE_CLIENTS + " (" +
                    ClientProductContract.ClientEntry.COLUMN_CLIENT_ID + " INTEGER PRIMARY KEY," +
                    ClientProductContract.ClientEntry.COLUMN_CLIENT_NAME + " TEXT," +
                    ClientProductContract.ClientEntry.COLUMN_CLIENT_NUMBER + " TEXT)";

    private static final String SQL_DELETE_PRODUCTS_TABLE =
            "DROP TABLE IF EXISTS " + ClientProductContract.ProductEntry.TABLE_PRODUCTS;

    private static final String SQL_DELETE_CLIENTS_TABLE =
            "DROP TABLE IF EXISTS " + ClientProductContract.ClientEntry.TABLE_CLIENTS;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
        db.execSQL(SQL_CREATE_CLIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PRODUCTS_TABLE);
        db.execSQL(SQL_DELETE_CLIENTS_TABLE);
        onCreate(db);
    }
}
