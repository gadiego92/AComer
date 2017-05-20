package com.informatica.ing_software.acomer.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.adapters.ListOpinionAdapter;
import com.informatica.ing_software.acomer.json.JSONParser;
import com.informatica.ing_software.acomer.objects.Opinion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpinionActivity extends AppCompatActivity {
    private static final String USUARIO_EMAIL = "usuario_email";
    private static final String RESTAURANTE_ID = "restaurante_id";
    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_OPINIONES = "opiniones";
    // URL to get favorites restaurants
    //private final String GET_OPINION = "http://amaterasu.unileon.es/benten/aComerAndroid/get_opiniones.php";
    private final String GET_OPINION = "http://192.168.0.14/proyecto/aComerAndroid/get_opiniones.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    // Extras
    private int restaurante_id;
    private String usuario_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);

        restaurante_id = getIntent().getIntExtra(RESTAURANTE_ID, 0);
        usuario_email = getIntent().getStringExtra(USUARIO_EMAIL);

        if (restaurante_id != 0) {
            // Get Restaurant Opinions by ID
            new GetOpinion().execute(String.valueOf(restaurante_id));
        }
    }

    // Show a message if the opinion list is empty
    public void showEmptyListMessage() {
        Toast.makeText(this, "Lista de opiniones vacia!", Toast.LENGTH_LONG).show();
    }

    /**
     * Background Async Task to load all restaurant opinions by making HTTP Request
     */
    private class GetOpinion extends AsyncTask<String, Void, List<Opinion>> {

        protected List<Opinion> doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>(RESTAURANTE_ID, args[0]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(GET_OPINION, params);

            int success = -1;
            List<Opinion> lista_opiniones = new ArrayList<Opinion>();

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Get Restaurant Array
                    String opiniones = json.getString(TAG_OPINIONES);
                    // Converto to JSONArray
                    JSONArray jsonArray = new JSONArray(opiniones);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        int id = Integer.parseInt(((JSONObject) jsonArray.get(i)).getString("id"));     // Id Restaurante
                        String us = ((JSONObject) jsonArray.get(i)).getString("us");                    // Username
                        String da = ((JSONObject) jsonArray.get(i)).getString("da");                    // Date
                        String op = ((JSONObject) jsonArray.get(i)).getString("op");                    // Opinion
                        String vl = ((JSONObject) jsonArray.get(i)).getString("vl");                    // Valoration

                        lista_opiniones.add(new Opinion(id, us, da, op, vl));
                    }
                }

                return lista_opiniones;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task update the ListView
         **/
        protected void onPostExecute(List<Opinion> result) {
            if (result.size() == 0) {
                showEmptyListMessage();
            } else {
                // seleccionamos el listView
                ListView lv = (ListView) findViewById(R.id.aOpinionListViewOpinion);

                // cogemos los datos con el ListSearchAdapter y los mostramos
                ListOpinionAdapter customAdapter = new ListOpinionAdapter(getApplication(), R.layout.activity_opinion_item, result);
                lv.setAdapter(customAdapter);
            }
        }
    }
}
