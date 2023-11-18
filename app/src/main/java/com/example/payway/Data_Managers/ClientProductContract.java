package com.example.payway.Data_Managers;

import android.provider.BaseColumns;

public class ClientProductContract {
    private ClientProductContract() {} // Private constructor to prevent accidental instantiation

    public static class ProductEntry implements BaseColumns {
        public static final String TABLE_PRODUCTS = "products";
        public static final String COLUMN_PRODUCT_ID = "product_id";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
        public static final String COLUMN_PRODUCT_PRICE = "product_price";
        public static final String COLUMN_PRODUCT_PAST_PRICE = "product_past_price";
        public static final String COLUMN_IS_FAVORITE = "is_favorite"; // Consider using 0/1 or true/false
    }

    public static class ClientEntry implements BaseColumns {
        public static final String TABLE_CLIENTS = "clients";
        public static final String COLUMN_CLIENT_ID = "client_id";
        public static final String COLUMN_CLIENT_NAME = "client_name"; // Verify if this should reference a product
        public static final String COLUMN_CLIENT_NUMBER = "client_number";
    }
}

