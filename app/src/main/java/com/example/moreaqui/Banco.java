package com.example.moreaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Banco extends SQLiteOpenHelper {

    private static Banco sInstance;
    private static final int VERSION = 1;
    private static final String BANCO_IMOVEL = "bd_imovel";
    private static final String TABELA_IMOVEL = "tb_imovel";
    private static final String ID_COLUMN = "id";
    private static final String PHONE_COLUMN = "phone";
    private static final String TYPE_COLUMN = "type";
    private static final String SIZE_COLUMN = "size";
    private static final String BUILDING_COLUMN = "building";
    private static final String OCCUPY_COLUMN = "occupy";
    private static final String LATITUDE_COLUMN = "latitude";
    private static final String LONGITUDE_COLUMN = "longitude";


    public Banco(Context context) {
        super(context, BANCO_IMOVEL, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUMN = "CREATE TABLE " + TABELA_IMOVEL + "(" + ID_COLUMN + " INTEGER PRIMARY KEY, " + PHONE_COLUMN + " TEXT," + TYPE_COLUMN + " TEXT, " + SIZE_COLUMN + " TEXT, " + BUILDING_COLUMN + " TEXT, " + OCCUPY_COLUMN + " TEXT, " + LATITUDE_COLUMN + " DOUBLE, " + LONGITUDE_COLUMN + " DOUBLE)";

        db.execSQL(QUERY_COLUMN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void  addImovel(Imovel imovel){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PHONE_COLUMN, imovel.getPhone());
        values.put(TYPE_COLUMN, imovel.getType());
        values.put(SIZE_COLUMN, imovel.getSize());
        values.put(BUILDING_COLUMN, imovel.getBuilding());
        values.put(OCCUPY_COLUMN, imovel.getOccupy());
        values.put(LATITUDE_COLUMN, imovel.getLatitude());
        values.put(LONGITUDE_COLUMN, imovel.getLongitude());

        db.insert(TABELA_IMOVEL, null, values);
        db.close();
    }

    Imovel selecionarImovel(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_IMOVEL, new String[] {ID_COLUMN, PHONE_COLUMN, TYPE_COLUMN, SIZE_COLUMN, BUILDING_COLUMN, OCCUPY_COLUMN, LATITUDE_COLUMN, LONGITUDE_COLUMN},
                ID_COLUMN + " = ?", new String[] {String.valueOf(codigo)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Imovel imovel = new Imovel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getDouble(6), cursor.getDouble(7));

        return imovel;

    }

    void deleteImovel(Imovel imovel){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_IMOVEL, ID_COLUMN + " = ?", new String[]{ String.valueOf(imovel.getId())});

        db.close();
    }

    public List<Imovel> listaImoveis(){

        List<Imovel> listaImovel = new ArrayList<Imovel>();

        String query = " SELECT * FROM " + TABELA_IMOVEL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor i = db.rawQuery(query, null);

        if(i.moveToFirst()){

            do{

                Imovel imovel = new Imovel();
                imovel.setId(Integer.parseInt(i.getString(0)));
                imovel.setPhone(i.getString(1));
                imovel.setType(i.getString(2));
                imovel.setSize(i.getString(3));
                imovel.setBuilding(i.getString(4));
                imovel.setOccupy(i.getString(5));
                imovel.setLatitude(i.getDouble(6));
                imovel.setLongitude(i.getDouble(7));

                listaImovel.add(imovel);

            } while(i.moveToNext());


        }
        return listaImovel;

    }

    public static synchronized Banco getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new Banco(context.getApplicationContext());
        }
        return sInstance;
    }

    public List<LocationEstate> getAllEstates() {
        List<LocationEstate> estates = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s", TABELA_IMOVEL);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    LocationEstate newEstate = new LocationEstate(cursor.getString(cursor.getColumnIndex(TYPE_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(SIZE_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(PHONE_COLUMN)),
                            cursor.getString(cursor.getColumnIndex(BUILDING_COLUMN)),
                            cursor.getDouble(cursor.getColumnIndex(LATITUDE_COLUMN)),
                            cursor.getDouble(cursor.getColumnIndex(LONGITUDE_COLUMN)));
                    estates.add(newEstate);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get estates from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return estates;
    }

}
