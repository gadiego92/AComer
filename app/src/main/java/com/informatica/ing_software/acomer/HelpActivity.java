package com.informatica.ing_software.acomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Intent searchIntent = getIntent();
        int preguntaRespuesta = getIntent().getIntExtra("posicion", 0);

        TextView textViewPregunta = (TextView) findViewById(R.id.textViewPregunta);
        TextView textViewRespuesta = (TextView) findViewById(R.id.textViewRespuesta);

        textViewPregunta.setText(HelpConstants.PREGUNTAS[preguntaRespuesta]);
        textViewRespuesta.setText(HelpConstants.RESPUESTAS[preguntaRespuesta]);
    }
}