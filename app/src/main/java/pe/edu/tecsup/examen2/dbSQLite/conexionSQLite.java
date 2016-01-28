package pe.edu.tecsup.examen2.dbSQLite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;

import pe.edu.tecsup.examen2.structure.PaisStructure;

/**
 * Created by karlos on 15/01/2016.
 */
public class conexionSQLite extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String TABLE_USUARIO=
            "CREATE TABLE IF NOT EXISTS PAIS ("
                    + "idPais INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name VARCHAR(100),"
                    + "codPais VARCHAR(10),"
                    + "latitud VARCHAR(50),"
                    + "longitud VARCHAR(50))";
    public conexionSQLite(Context context) {
        super(context, "dbPaises", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USUARIO);
        Log.d("xxx", "se creo db");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertarPais(SQLiteDatabase db,String namePais,String cod,String lat,String lon){
        SQLiteStatement stp =db.compileStatement("INSERT INTO PAIS ( name,codPais,latitud,longitud ) VALUES (?,?,?,?)");
        stp.bindString(1, namePais);
        stp.bindString(2, cod);
        stp.bindString(3, lat);
        stp.bindString(4, lon);
        stp.execute();
        db.close();
        stp.close();
        Log.d("obs registro","se registro el pais");
    }

    public ArrayList<PaisStructure> ExtraerPais(SQLiteDatabase db){
        Cursor c= db.rawQuery("SELECT name, codPais, latitud, longitud FROM PAIS ",null);
        ArrayList<PaisStructure> arrayList =new ArrayList<PaisStructure>();
        PaisStructure row=null;

        if(c.moveToFirst()){
            do{
              row=new PaisStructure();
                row.setNamePais(c.getString(0));
                row.setCodPais(c.getString(1));
                row.setLatitud(c.getString(2));
                row.setLogintud(c.getString(3));
                arrayList.add(row);
            }while(c.moveToNext());
        }
        c.close();
        return  arrayList;
    }
}
