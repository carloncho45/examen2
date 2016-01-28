package pe.edu.tecsup.examen2;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import pe.edu.tecsup.examen2.dbSQLite.conexionSQLite;

/**
 * Created by carlos.lopez on 28/01/2016.
 */
public class view_maps extends Activity implements OnMapReadyCallback {
    private ImageView imagBandera;
    double lat_;
    double lon_;
    private static final String urlBandera="http://www.geognos.com/api/en/countries/flag/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String name=getIntent().getStringExtra("name");
        String cod=getIntent().getStringExtra("cod");
        imagBandera=(ImageView)findViewById(R.id.imageBandera);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        double lat_=Double.parseDouble(getIntent().getStringExtra("lati"));
        double lon_=Double.parseDouble(getIntent().getStringExtra("longi"));
       gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
               new LatLng(lat_, lon_), 8));
        new bandera(gMap,lat_,lon_).execute(getIntent().getStringExtra("cod"));

    }




    private class bandera extends AsyncTask<String,String,Bitmap>{
        URL image;
        Bitmap bitmap;
        GoogleMap googleMap;
        double lat;
        double lon;

        public bandera(GoogleMap googleMap, double lat, double lon) {
            this.googleMap = googleMap;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                image=new URL(urlBandera+params[0].toString()+".png");
                HttpURLConnection conn = (HttpURLConnection) image.openConnection();
                conn.connect();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
          /*  LatLng mapCenter = new LatLng(lat, lon);
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                    .position(mapCenter)
                    .flat(true)
                    .rotation(0));*/

            imagBandera.setImageBitmap(bitmap);
        }
    }
}
