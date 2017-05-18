package com.informatica.ing_software.acomer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.informatica.ing_software.acomer.json.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    // url to register
    private static String USUARIOS_REGISTRO = "http://amaterasu.unileon.es/benten/aComerAndroid/p0_usuarios_registro.php";
    // JSON Node names
    private final String TAG_SUCCESS = "success";
    //private final String USUARIOS_REGISTRO = "http://192.168.0.14/proyecto/aComerAndroid/p0_usuarios_registro.php";
    // Tipo de Usuario para el registro (C - Cliente)
    private final String TIPO_USUARIO_CLIENTE = "C";
    // Progress Register Dialog
    private ProgressDialog pDialog;
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    // Procedemos con el registro al pulsar el boton "Registro"
    public void registrarse(View view) {
        // Cogemos toda la informacion introducida por el usuario en la pantalla de "Registro"
        String email = ((EditText) findViewById(R.id.registroEditTextEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.registroEditTextPassword)).getText().toString();
        String password2 = ((EditText) findViewById(R.id.registroEditTextPassword2)).getText().toString();
        String nombre = ((EditText) findViewById(R.id.registroEditTextNombre)).getText().toString();
        String apellido = ((EditText) findViewById(R.id.registroEditTextApellido)).getText().toString();
        String fechaNacimiento = ((EditText) findViewById(R.id.registroEditTextFechaNacimiento)).getText().toString();
        String telefono = ((EditText) findViewById(R.id.registroEditTextTelefono)).getText().toString();
        String ciudad = ((EditText) findViewById(R.id.registroEditTextCiudad)).getText().toString();
        String provincia = ((EditText) findViewById(R.id.registroEditTextProvincia)).getText().toString();
        String pais = ((EditText) findViewById(R.id.registroEditTextPais)).getText().toString();
        String codigoPostal = ((EditText) findViewById(R.id.registroEditTextCodigoPostal)).getText().toString();

        // Comprobamos que no haya ningun campo vacio
        if (!campoVacio(email) && !campoVacio(password) && !campoVacio(password2) && !campoVacio(nombre) && !campoVacio(apellido)
                && !campoVacio(fechaNacimiento) && !campoVacio(telefono) && !campoVacio(ciudad) && !campoVacio(provincia)
                && !campoVacio(pais) && !campoVacio(codigoPostal)) {
            // Comprobamos que las dos contraseñas introducidas coincidan
            if (password.equals(password2)) {
                // Contraseñas coinciden. Creamos el registro
                new Registro().execute(email, password, nombre, apellido, fechaNacimiento, telefono, ciudad, provincia, pais, codigoPostal);
            } else {
                // Contraseñas NO coinciden
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Algun campo esta VACIO
            Toast.makeText(this, "Completar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo que comprueba que no haya campos vacios
    private boolean campoVacio(String campo) {
        return campo.equals("");
    }

    /**
     * Background Async Task to try register by making HTTP Request
     */
    class Registro extends AsyncTask<String, String, String> {
        String usuario_email;

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Intentando registro...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * trying register
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>("email", args[0]));
            params.add(new Pair<String, String>("password", args[1]));
            params.add(new Pair<String, String>("tipo_usuario", "C"));
            params.add(new Pair<String, String>("nombre", args[2]));
            params.add(new Pair<String, String>("apellido", args[3]));
            params.add(new Pair<String, String>("fecha_nacimiento", args[4]));
            params.add(new Pair<String, String>("telefono", args[5]));
            params.add(new Pair<String, String>("ciudad", args[6]));
            params.add(new Pair<String, String>("provincia", args[7]));
            params.add(new Pair<String, String>("pais", args[8]));
            params.add(new Pair<String, String>("codigo_postal", args[9]));

            int success = 0;

            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(USUARIOS_REGISTRO, params);

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                return String.valueOf(success);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String result) {
            // dismiss the dialog after login
            pDialog.dismiss();

            if (result.equals("1")) {
                Toast.makeText(getApplicationContext(), "Registro completado con exito", Toast.LENGTH_LONG).show();

                // navigate to LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                // Registro incorrecto
                Toast.makeText(getApplicationContext(), "El registro ha fallado", Toast.LENGTH_LONG).show();
            }
        }
    }
}