package pe.edu.tecsup.examen2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.tecsup.examen2.dbSQLite.conexionSQLite;
import pe.edu.tecsup.examen2.structure.PaisStructure;

public class RegistroActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView ListItem;
    private ImageButton BtnBuscar;
    private EditText EditPais;
    private ProgressBar Progress;
    //HACEMOS REFERENCIA ALA CLASE QUE IMPLEMENTA LA BASE DE DATOS..
    private conexionSQLite conn =new conexionSQLite(RegistroActivity.this);
    private SQLiteDatabase db;
    public static Activity REGISTRO_ACTIVITY = null;
    //EXTRAE LA VARIABLE arrayPaises de la clase  LlenarPiis
    private ArrayList<PaisStructure> arrayList=LlenarPais.arrayPaises;
    private ArrayList<PaisStructure>arrayListBuca=new ArrayList<PaisStructure>();
    private ArrayAdapter<PaisStructure> adapter;
    private int textlength = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        EditPais=(EditText)findViewById(R.id.editBuscarPais);
        ListItem=(ListView)findViewById(R.id.listPaises);
        ListItem.setOnItemClickListener(this);
        BtnBuscar=(ImageButton)findViewById(R.id.buttonBuscar);
        Progress=(ProgressBar)findViewById(R.id.progressBarLoader);
        REGISTRO_ACTIVITY=this;

        registerForContextMenu(ListItem);
        new LlenarPais().execute();
        EditPais.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = EditPais.getText().length();
                PaisStructure row= null;
                arrayListBuca.clear();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (textlength <= arrayList.get(i).getNamePais().toString().length()) {
                        if (EditPais.getText().toString().equalsIgnoreCase((String)
                                arrayList.get(i).getNamePais().subSequence(0, textlength))) {
                            row=new PaisStructure();
                           row.setNamePais(arrayList.get(i).getNamePais().toLowerCase());
                            arrayListBuca.add(row);
                        }
                    }
                }

                adapter =new ArrayAdapter<PaisStructure>(RegistroActivity.this, android.R.layout.simple_list_item_1,arrayListBuca);
                ListItem.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       // Toast.makeText(getApplicationContext(),String.valueOf(arrayList.get(1).getNamePais()),Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.I_AGREGAR:
                if(arrayList!=null){
                    Toast.makeText(getApplicationContext(),arrayList.get(info.position).getNamePais()+"**"+
                    arrayList.get(info.position).getLatitud()+"**"+arrayList.get(info.position).getLogintud()+"**"+
                            arrayList.get(info.position).getCodPais(),Toast.LENGTH_SHORT).show();
                    db=conn.getWritableDatabase();
                    String namePais=arrayList.get(info.position).getNamePais();
                    String codPais=arrayList.get(info.position).getCodPais();
                    String latitud=arrayList.get(info.position).getLatitud();
                    String longitud=arrayList.get(info.position).getLogintud();
                    conn.InsertarPais(db,namePais,codPais,latitud,longitud);

                    Toast.makeText(getApplicationContext(),namePais+" SE AGREGO ....",Toast.LENGTH_LONG).show();
                }else{
                    Log.d("null","XXX");
                }

                return true;
            case R.id.I_ELIMINAR:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if (v.getId() == R.id.listPaises) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("MENU CONETEXTUAL");
            inflater.inflate(R.menu.men_contextual, menu);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(RegistroActivity.this,parent.toString() , Toast.LENGTH_SHORT).show();
    }

}
