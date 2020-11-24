package com.example.moreaqui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Banco extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String BANCO_IMOVEL = "bd_imovel";
    private static final String TABELA_IMOVEL = "tb_imovel";
    private static final String ID_COLUMN = "id";
    private static final String PHONE_COLUMN = "phone";
    private static final String TYPE_COLUMN = "type";
    private static final String SIZE_COLUMN = "size";
    private static final String BUILDING_COLUMN = "building";



    public Banco(Context context) {
        super(context, BANCO_IMOVEL, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUMN = "CREATE TABLE " + TABELA_IMOVEL + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY, " + PHONE_COLUMN + " TEXT,"
                + TYPE_COLUMN + " TEXT, " + SIZE_COLUMN + " TEXT, " + BUILDING_COLUMN + " TEXT) ";

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

        db.insert(TABELA_IMOVEL, null, values);
        db.close();
    }

    Imovel selecionarImovel(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_IMOVEL, new String[] {ID_COLUMN, PHONE_COLUMN, TYPE_COLUMN, SIZE_COLUMN, BUILDING_COLUMN},
                ID_COLUMN + " = ?", new String[] {String.valueOf(codigo)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Imovel imovel = new Imovel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

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

                listaImovel.add(imovel);

            } while(i.moveToNext());


        }
        return listaImovel;

    }

}
