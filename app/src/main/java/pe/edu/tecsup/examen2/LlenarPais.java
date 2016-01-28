package pe.edu.tecsup.examen2;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pe.edu.tecsup.examen2.structure.PaisStructure;

/**
 * Created by Carlos on 14/01/2016.
 */
public class LlenarPais extends AsyncTask<Void, Void, Void> {
    private static final String URL = "https://restcountries.eu/rest/v1/all";
 //   private static final String URL = "http://www.geognos.com/api/en/countries/info/all.json";
   /// http://www.geognos.com/api/en/countries/flag/GR.png
    //hago referencia a mi clase RegistroActivity.java, para utilizar todos sus controles
    private RegistroActivity CLASE=new RegistroActivity();
    private ListView Lista;
    private ProgressBar Progress;
    public static ArrayList<PaisStructure> arrayPaises=new ArrayList<PaisStructure>();
    private ArrayAdapter<PaisStructure> adapter;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Aqui inicializamos las variables
        //CLASE.REGISTRO_ACTIVITY es el contexto declarado en la clase RegistroActivity.java
        Lista=(ListView)CLASE.REGISTRO_ACTIVITY.findViewById(R.id.listPaises);
        Progress=(ProgressBar)CLASE.REGISTRO_ACTIVITY.findViewById(R.id.progressBarLoader);
        Progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected Void doInBackground(Void... params) {

        //String charset = "UTF-8";

        HttpURLConnection c = null;
        try {
            URL u = new URL(URL);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:

                case 201:
                    //br realiza la peticion a la url, y extrae todos los datos
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder json = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        json.append(line + "\n");
                    }
                    br.close();
                   /// Log.d("datos", json.toString());
                    JSONArray jsonArray = new JSONArray(json.toString());
                    int SizeArray = jsonArray.length();
                 //   Log.d("tamaño: ", String.valueOf(SizeArray));
                    PaisStructure Row=null;
                    //recorre el array de paises y coge solo el nombre del pais
                    for (int x = 0; x < SizeArray; x++) {
                        Row=new PaisStructure();
                        String namePais=jsonArray.getJSONObject(x).getString("name");
                        Row.setNamePais(namePais);

                        String arrayCodPais= jsonArray.getJSONObject(x).getString("altSpellings");
                        JSONArray jsonArray1=new JSONArray(arrayCodPais);
                        String codPais=jsonArray1.getString(0);
                        Row.setCodPais(codPais);
                      //  Log.d("cod_", codPais);
                        Row.setNamePais(namePais);

                        String arrayLatLon=jsonArray.getJSONObject(x).getString("latlng");
                        JSONArray jsonArray2=new JSONArray(arrayLatLon);
                        String latitud=jsonArray2.getString(0);
                        String longitud=jsonArray2.getString(1);
                        Row.setLatitud(latitud);
                        Row.setLogintud(longitud);
                        arrayPaises.add(Row);
                    }

            }

        } catch (MalformedURLException ex) {
            // Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
          /*  if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }*/
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Aqui escribiremos todas las instrucciones de salida; mostrar los datos en una caja de texto
        Progress.setVisibility(View.GONE);
        super.onPostExecute(aVoid);
        adapter=new ArrayAdapter<PaisStructure>(CLASE.REGISTRO_ACTIVITY,android.R.layout.simple_list_item_1,arrayPaises);
        Lista.setAdapter(adapter);

    }
}
