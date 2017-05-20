package com.informatica.ing_software.acomer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.informatica.ing_software.acomer.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        int preguntaRespuesta = getIntent().getIntExtra("posicion", 0);

        TextView textViewPregunta = (TextView) findViewById(R.id.aHelpTextViewPregunta);
        TextView textViewRespuesta = (TextView) findViewById(R.id.aHelpTextViewRespuesta);

        textViewPregunta.setText(HelpConstants.PREGUNTAS[preguntaRespuesta]);
        textViewRespuesta.setText(HelpConstants.RESPUESTAS[preguntaRespuesta]);
    }
}
