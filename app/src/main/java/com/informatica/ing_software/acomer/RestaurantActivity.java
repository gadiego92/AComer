package com.informatica.ing_software.acomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantActivity extends AppCompatActivity {

    private int id;
    private String nombre, ciudad, telefono, tipo_cocina, valoracion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Intent searchIntent = getIntent();
        id = getIntent().getIntExtra("restaurante_id", 0);
        nombre = getIntent().getStringExtra("restaurante_nombre");
        ciudad = getIntent().getStringExtra("restaurante_ciudad");
        telefono = getIntent().getStringExtra("restaurante_telefono");
        tipo_cocina = getIntent().getStringExtra("restaurante_tipo_cocina");
        valoracion = getIntent().getStringExtra("restaurante_valoracion");

        TextView nombreTextView = (TextView) findViewById(R.id.textViewNombre);
        nombreTextView.setText(nombre);
        TextView ciudadTextView = (TextView) findViewById(R.id.textViewCiudad);
        ciudadTextView.setText(ciudad);
        TextView tipoCocinaTextView = (TextView) findViewById(R.id.textViewTipoCocina);
        tipoCocinaTextView.setText("Cocina: " + tipo_cocina);
        TextView telefonoTextView = (TextView) findViewById(R.id.textViewTelefono);
        telefonoTextView.setText("Contacto: " + telefono);

        RatingBar valoracionRatingBar = (RatingBar) findViewById(R.id.ratingBarRestaurante);
        //valoracionRatingBar.setMax(3);
        //valoracionRatingBar.setNumStars(3);
        valoracionRatingBar.setRating(Float.parseFloat(valoracion) % 3);
    }
}