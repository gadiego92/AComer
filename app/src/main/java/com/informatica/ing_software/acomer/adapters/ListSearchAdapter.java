package com.informatica.ing_software.acomer.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.informatica.ing_software.acomer.R;

/**
 * Created by Diego on 07/01/2017.
 */

public class ListSearchAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] restaurantes_nombre, restaurantes_ciudad, restaurantes_cocina, restaurantes_valoracion;

    public ListSearchAdapter(Activity context, String[] res_nombre, String[] res_ciudad, String[] res_cocina, String[] res_valoracion) {
        super(context, R.layout.fragment_search_item);

        this.context = context;
        this.restaurantes_nombre = res_nombre;
        this.restaurantes_ciudad = res_ciudad;
        this.restaurantes_cocina = res_cocina;
        this.restaurantes_valoracion = res_valoracion;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.fragment_search_item, null, true);

        TextView textNombre = (TextView) rowView.findViewById(R.id.textViewNombre);
        TextView textCiudad = (TextView) rowView.findViewById(R.id.textViewCiudad);
        TextView textCocina = (TextView) rowView.findViewById(R.id.textViewCocina);
        RatingBar ratingValoracion = (RatingBar) rowView.findViewById(R.id.ratingBarValoracion);

        textNombre.setText(restaurantes_nombre[position]);
        textCiudad.setText(restaurantes_ciudad[position]);
        textCocina.setText(restaurantes_cocina[position]);
        ratingValoracion.setRating(Float.parseFloat(restaurantes_valoracion[position]));

        return rowView;
    }
}