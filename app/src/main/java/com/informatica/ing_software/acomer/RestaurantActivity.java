package com.informatica.ing_software.acomer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.informatica.ing_software.acomer.json.JSONParser;
import com.informatica.ing_software.acomer.objects.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_RESTAURANTS = "restaurantes";
    // URL to get favorites restaurants
    //private final String USUARIOS_FAVORITOS = "http://amaterasu.unileon.es/benten/aComerAndroid/get_restaurantes.php";
    private final String GET_RESTAURANT = "http://192.168.0.14/proyecto/aComerAndroid/get_restaurantes.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private int id_restaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        id_restaurante = getIntent().getIntExtra("restaurante_id", 0);

        if (id_restaurante != 0) {
            // Get Restaurant by ID
            new GetRestaurante().execute(String.valueOf(id_restaurante));
        }
    }

    private void updateUI(Restaurante restaurante) {
        TextView nombreTextView = (TextView) findViewById(R.id.aRestTextViewNombre);
        TextView ciudadTextView = (TextView) findViewById(R.id.aRestTextViewCiudad);
        TextView tipoCocinaTextView = (TextView) findViewById(R.id.aRestTextViewTipoCocina);
        TextView telefonoTextView = (TextView) findViewById(R.id.aRestTextViewTelefono);
        RatingBar valoracionRatingBar = (RatingBar) findViewById(R.id.aRestRatingBarValoracion);
        valoracionRatingBar.setMax(3);
        valoracionRatingBar.setNumStars(3);

        nombreTextView.setText(restaurante.getNombre());
        ciudadTextView.setText(restaurante.getCiudad());
        tipoCocinaTextView.setText("Cocina: " + restaurante.getTipo_cocina());
        telefonoTextView.setText("Contacto: " + restaurante.getTelefono());
        valoracionRatingBar.setRating(Float.parseFloat(restaurante.getValoracion()));
    }

    /**
     * Background Async Task to load all favorite restaurants by making HTTP Request
     */
    private class GetRestaurante extends AsyncTask<String, Void, Restaurante> {

        protected Restaurante doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>("id_restaurante", args[0]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(GET_RESTAURANT, params);

            int success = -1;
            Restaurante restaurante = new Restaurante();

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Get Restaurant Array
                    String restaurantes = json.getString(TAG_RESTAURANTS);
                    // Converto to JSONArray
                    JSONArray jsonArray = new JSONArray(restaurantes);

                    //for (int i = 0; i < jsonArray.length(); i++) {
                        int id = Integer.parseInt(((JSONObject) jsonArray.get(0)).getString("id"));     // Id
                        String nm = ((JSONObject) jsonArray.get(0)).getString("nm");                    // Nombre
                        String cd = ((JSONObject) jsonArray.get(0)).getString("cd");                    // Ciudad
                        String tl = ((JSONObject) jsonArray.get(0)).getString("tl");                    // Telefono
                        String cn = ((JSONObject) jsonArray.get(0)).getString("cn");                    // Cocina
                        String vl = ((JSONObject) jsonArray.get(0)).getString("vl");                    // Valoracion

                        restaurante.setValues(id, nm, cd, tl, cn, vl);
                    //}
                }

                return restaurante;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(Restaurante restaurante) {
            updateUI(restaurante);
        }
    }
}