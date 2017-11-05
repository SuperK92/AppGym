package com.example.kelly.appgym.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.SparseArray;

public class ProveedorDeContenido extends ContentProvider {


    private static final int EJERCICIO_ONE_REG = 1;
    private static final int EJERCICIO_ALL_REGS = 2;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "Gym.db";
    private static final int DATABASE_VERSION = 3;

    private static final String EJERCICIO_TABLE_NAME = "Ejercicio";

    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;

    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                EJERCICIO_TABLE_NAME,
                EJERCICIO_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                EJERCICIO_TABLE_NAME + "/#",
                EJERCICIO_ONE_REG);

        // Specifies a custom MIME type for the picture URL table

        sMimeTypes.put(
                EJERCICIO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + EJERCICIO_TABLE_NAME);
        sMimeTypes.put(
                EJERCICIO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + EJERCICIO_TABLE_NAME);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            //if (!db.isReadOnly()){
            //Habilitamos la integridad referencial
            db.execSQL("PRAGMA foreign_keys=ON;");
            //}
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                            + EJERCICIO_TABLE_NAME
                            + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                            + Contrato.Ejercicio.NOMBRE + " TEXT , "
                            + Contrato.Ejercicio.REPETICIONES + " INTEGER ); "
            );

            inicializarDatos(db);

        }

        void inicializarDatos(SQLiteDatabase db){

            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.REPETICIONES + ") " +
                    "VALUES (1,'Sentadillas', 15)");
            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.REPETICIONES + ") " +
                    "VALUES (2,'Aperturas de Pecho', 12)");
            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.REPETICIONES + ") " +
                    "VALUES (3,'Dominada',10)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + EJERCICIO_TABLE_NAME);

            onCreate(db);
        }

    }

    public ProveedorDeContenido() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case EJERCICIO_ALL_REGS:
                table = EJERCICIO_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case EJERCICIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Ejercicio._ID + " = "
                        + uri.getLastPathSegment();
                table = EJERCICIO_TABLE_NAME;
                break;
            case EJERCICIO_ALL_REGS:
                table = EJERCICIO_TABLE_NAME;
                break;
        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (sUriMatcher.match(uri)) {
            case EJERCICIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Ejercicio._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(EJERCICIO_TABLE_NAME);
                break;
            case EJERCICIO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Ejercicio._ID + " ASC";
                qb.setTables(EJERCICIO_TABLE_NAME);
                break;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null,
                        sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case EJERCICIO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Ejercicio._ID + " = "
                        + uri.getLastPathSegment();
                table = EJERCICIO_TABLE_NAME;
                break;
            case EJERCICIO_ALL_REGS:
                table = EJERCICIO_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to update row into " + uri);
    }
}
