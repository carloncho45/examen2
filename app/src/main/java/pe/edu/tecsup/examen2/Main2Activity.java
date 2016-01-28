package pe.edu.tecsup.examen2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.edu.tecsup.examen2.dbSQLite.conexionSQLite;
import pe.edu.tecsup.examen2.structure.PaisStructure;

public class Main2Activity extends AppCompatActivity {
    private ListView listaPais;
    private TextView txtMensaje;
    private conexionSQLite conexion;
    private SQLiteDatabase db;
    private ArrayAdapter<PaisStructure> adapter;
    private ArrayList<PaisStructure> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listaPais=(ListView)findViewById(R.id.listPaisAgreagados);
        txtMensaje=(TextView)findViewById(R.id.text_no_notes);

        conexion=new conexionSQLite(Main2Activity.this);
        db=conexion.getWritableDatabase();
        arrayList=conexion.ExtraerPais(db);

        adapter=new ArrayAdapter<PaisStructure>(Main2Activity.this,android.R.layout.simple_list_item_1,arrayList);
        listaPais.setAdapter(adapter);

        Toast.makeText(Main2Activity.this,String.valueOf(arrayList.size()),Toast.LENGTH_SHORT).show();
        if(arrayList.size()==0){
            txtMensaje.setVisibility(View.VISIBLE);
            listaPais.setVisibility(View.GONE);
        }

        listaPais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),arrayList.get(position).getCodPais(),Toast.LENGTH_SHORT).show();
                String namePais=arrayList.get(position).getNamePais();
                String codPais=arrayList.get(position).getCodPais();
                String latitud=arrayList.get(position).getLatitud();
                String longitud=arrayList.get(position).getLogintud();

                Intent intent=new Intent(Main2Activity.this,view_maps.class);
                intent.putExtra("name",namePais);
                intent.putExtra("cod",codPais);
                intent.putExtra("lati",latitud);
                intent.putExtra("longi",longitud);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(Main2Activity.this,RegistroActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
