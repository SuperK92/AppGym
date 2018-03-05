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
    private static final int EJERCICIO_MUSCULO_REG = 3;

    private static final int MUSCULO_ONE_REG = 10;
    private static final int MUSCULO_ALL_REGS = 20;

    private static final int ACTIVIDAD_ONE_REG = 100;
    private static final int ACTIVIDAD_ALL_REGS = 200;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "Gym.db";
    private static final int DATABASE_VERSION = 9;

    private static final String MUSCULO_TABLE_NAME = "Musculo";
    private static final String EJERCICIO_TABLE_NAME = "Ejercicio";
    private static final String ACTIVIDAD_TABLE_NAME = "Actividad";

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
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                EJERCICIO_TABLE_NAME + "/MUSCULO",
                EJERCICIO_MUSCULO_REG);

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                MUSCULO_TABLE_NAME,
                MUSCULO_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                MUSCULO_TABLE_NAME + "/#",
                MUSCULO_ONE_REG);

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                ACTIVIDAD_TABLE_NAME,
                ACTIVIDAD_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                ACTIVIDAD_TABLE_NAME + "/#",
                ACTIVIDAD_ONE_REG);

        // Specifies a custom MIME type for the picture URL table

        sMimeTypes.put(
                EJERCICIO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + EJERCICIO_TABLE_NAME);
        sMimeTypes.put(
                EJERCICIO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + EJERCICIO_TABLE_NAME);

        sMimeTypes.put(
                MUSCULO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + MUSCULO_TABLE_NAME);
        sMimeTypes.put(
                MUSCULO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + MUSCULO_TABLE_NAME);

        sMimeTypes.put(
                ACTIVIDAD_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + ACTIVIDAD_TABLE_NAME);
        sMimeTypes.put(
                ACTIVIDAD_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + ACTIVIDAD_TABLE_NAME);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            if (!db.isReadOnly()){
            //Habilitamos la integridad referencial
            db.execSQL("PRAGMA foreign_keys=ON;");
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                    + MUSCULO_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contrato.Musculo.NOMBRE + " TEXT ); "
            );

            db.execSQL("Create table "
                            + EJERCICIO_TABLE_NAME
                            + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                            + Contrato.Ejercicio.NOMBRE + " TEXT , "
                            + Contrato.Ejercicio.ID_MUSCULO + " INTEGER , "
                            + "FOREIGN KEY (" + Contrato.Ejercicio.ID_MUSCULO + ") "
                            + "REFERENCES " + MUSCULO_TABLE_NAME + " (" + Contrato.Musculo._ID + "));"
            );

            db.execSQL("Create table "
                    + ACTIVIDAD_TABLE_NAME
                    + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                    + Contrato.Actividad.ID_EJERCICIO + " INTEGER , "
                    + Contrato.Actividad.SERIES + " INTEGER , "
                    + Contrato.Actividad.REPETICIONES + " INTEGER ); "
            );

            inicializarDatos(db);

        }

        void inicializarDatos(SQLiteDatabase db){

            db.execSQL("INSERT INTO " + MUSCULO_TABLE_NAME + " (" +  Contrato.Musculo._ID + "," + Contrato.Musculo.NOMBRE + ") " +
                    "VALUES (1,'Pecho')");

            db.execSQL("INSERT INTO " + MUSCULO_TABLE_NAME + " (" +  Contrato.Musculo._ID + "," + Contrato.Musculo.NOMBRE + ") " +
                    "VALUES (2,'Espalda')");

            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.ID_MUSCULO + ") " +
                    "VALUES (1,'Press de banca',1)");
            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.ID_MUSCULO + ") " +
                    "VALUES (2,'Dominada',2)");

            db.execSQL("INSERT INTO " + ACTIVIDAD_TABLE_NAME + " (" +  Contrato.Actividad._ID + "," + Contrato.Actividad.ID_EJERCICIO + "," + Contrato.Actividad.SERIES + ","
                    + Contrato.Actividad.REPETICIONES + ") " + "VALUES (1,1,3,15)");

            /*db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.REPETICIONES + ") " +
                    "VALUES (1,'Sentadillas', 15)");
            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.REPETICIONES + ") " +
                    "VALUES (2,'Aperturas de Pecho', 12)");
            db.execSQL("INSERT INTO " + EJERCICIO_TABLE_NAME + " (" +  Contrato.Ejercicio._ID + "," + Contrato.Ejercicio.NOMBRE + "," + Contrato.Ejercicio.REPETICIONES + ") " +
                    "VALUES (3,'Dominada',10)");*/
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + MUSCULO_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + EJERCICIO_TABLE_NAME);
			db.execSQL("DROP TABLE IF EXISTS " + ACTIVIDAD_TABLE_NAME);

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
            case MUSCULO_ALL_REGS:
                table = MUSCULO_TABLE_NAME;
                break;
            case ACTIVIDAD_ALL_REGS:
                table = ACTIVIDAD_TABLE_NAME;
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
            case MUSCULO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musculo._ID + " = "
                        + uri.getLastPathSegment();
                table = MUSCULO_TABLE_NAME;
                break;
            case MUSCULO_ALL_REGS:
                table = MUSCULO_TABLE_NAME;
                break;
            case ACTIVIDAD_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Actividad._ID + " = "
                        + uri.getLastPathSegment();
                table = ACTIVIDAD_TABLE_NAME;
                break;
            case ACTIVIDAD_ALL_REGS:
                table = ACTIVIDAD_TABLE_NAME;
                break;

        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }


    private static final String EJERCICIO_JOIN_MUSCULO = EJERCICIO_TABLE_NAME +
            " INNER JOIN " + MUSCULO_TABLE_NAME +
            " ON " + Contrato.Ejercicio.ID_MUSCULO + " = " + Contrato.Musculo._ID;

    private final String[] proyEjercicioMusculo = new String[]{
            Contrato.Ejercicio._ID,
            Contrato.Ejercicio.NOMBRE,
            Contrato.Musculo.NOMBRE + " AS NOMBRE_MUSCULO"
            };

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
            case EJERCICIO_MUSCULO_REG:

                projection = proyEjercicioMusculo;
                qb.setTables(EJERCICIO_JOIN_MUSCULO);
                break;
            case MUSCULO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musculo._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(MUSCULO_TABLE_NAME);
                break;
            case MUSCULO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Musculo._ID + " ASC";
                qb.setTables(MUSCULO_TABLE_NAME);
                break;
            case ACTIVIDAD_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Actividad._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(ACTIVIDAD_TABLE_NAME);
                break;
            case ACTIVIDAD_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Actividad._ID + " ASC";
                qb.setTables(ACTIVIDAD_TABLE_NAME);
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
            case MUSCULO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Musculo._ID + " = "
                        + uri.getLastPathSegment();
                table = MUSCULO_TABLE_NAME;
                break;
            case MUSCULO_ALL_REGS:
                table = MUSCULO_TABLE_NAME;
                break;
            case ACTIVIDAD_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Actividad._ID + " = "
                        + uri.getLastPathSegment();
                table = ACTIVIDAD_TABLE_NAME;
                break;
            case ACTIVIDAD_ALL_REGS:
                table = ACTIVIDAD_TABLE_NAME;
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
