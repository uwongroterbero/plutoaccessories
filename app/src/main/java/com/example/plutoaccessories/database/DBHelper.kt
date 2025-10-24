package com.example.plutoaccessories.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db_pluto_9.db";
    private static final int DATABASE_VERSION = 1;

    // Nama tabel
    public static final String TABLE_USERS = "users";
    public static final String TABLE_PRODUCTS = "products";
    public static final String TABLE_ORDERS = "orders";
    public static final String TABLE_ORDER_ITEMS = "order_items";
    public static final String TABLE_WALLET = "wallet";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // ================= USERS =================
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                "id_user INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "alamat TEXT, " +
                "no_hp TEXT, " +
                "role TEXT DEFAULT 'user');");

        // ================= PRODUCTS =================
        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                "id_produk INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama_produk TEXT, " +
                "deskripsi TEXT, " +
                "harga REAL, " +
                "stok INTEGER, " +
                "gambar TEXT, " +
                "merk TEXT, " +
                "bahan TEXT);");

        // ================= ORDERS =================
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                "id_order INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER, " +
                "tanggal TEXT, " +
                "total_harga REAL, " +
                "status TEXT, " +
                "FOREIGN KEY (id_user) REFERENCES users(id_user));");

        // ================= ORDER ITEMS =================
        db.execSQL("CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
                "id_item INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_order INTEGER, " +
                "id_produk INTEGER, " +
                "jumlah INTEGER, " +
                "subtotal REAL, " +
                "FOREIGN KEY (id_order) REFERENCES orders(id_order), " +
                "FOREIGN KEY (id_produk) REFERENCES products(id_produk));");

        // ================= WALLET =================
        db.execSQL("CREATE TABLE " + TABLE_WALLET + " (" +
                "id_wallet INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_user INTEGER, " +
                "saldo REAL DEFAULT 0, " +
                "FOREIGN KEY (id_user) REFERENCES users(id_user));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLET);
        onCreate(db);
    }

    // ================= CRUD USERS =================
    public boolean registerUser(String nama, String email, String password, String alamat, String no_hp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("email", email);
        values.put("password", password);
        values.put("alamat", alamat);
        values.put("no_hp", no_hp);

        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result != -1;
    }

    public Cursor loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? AND password=?",
                new String[]{email, password});
    }

    // ================= CRUD PRODUCTS =================
    public boolean addProduct(String nama, String deskripsi, double harga, int stok, String gambar, String merk, String bahan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama_produk", nama);
        values.put("deskripsi", deskripsi);
        values.put("harga", harga);
        values.put("stok", stok);
        values.put("gambar", gambar);
        values.put("merk", merk);
        values.put("bahan", bahan);

        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return result != -1;
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

    // ================= WALLET =================
    public void createWallet(int id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_user", id_user);
        values.put("saldo", 0);
        db.insert(TABLE_WALLET, null, values);
        db.close();
    }

    public double getSaldo(int id_user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT saldo FROM " + TABLE_WALLET + " WHERE id_user=?",
                new String[]{String.valueOf(id_user)});
        if (cursor.moveToFirst()) {
            double saldo = cursor.getDouble(0);
            cursor.close();
            return saldo;
        }
        return 0;
    }

    public void updateSaldo(int id_user, double saldoBaru) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("saldo", saldoBaru);
        db.update(TABLE_WALLET, values, "id_user=?", new String[]{String.valueOf(id_user)});
        db.close();
    }
}
