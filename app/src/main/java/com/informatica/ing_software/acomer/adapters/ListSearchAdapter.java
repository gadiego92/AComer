package com.informatica.ing_software.acomer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.objects.Restaurante;

import java.util.List;

/**
 * Created by Diego on 07/01/2017.
 */

public class ListSearchAdapter extends ArrayAdapter<Restaurante> {

    public ListSearchAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListSearchAdapter(Context context, int resource, List<Restaurante> restaurantes) {
        super(context, resource, restaurantes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            rowView = vi.inflate(R.layout.fragment_search_item, null);
        }

        Restaurante restaurante = getItem(position);

        if (restaurante != null) {
            TextView textNombre = (TextView) rowView.findViewById(R.id.fSearchTextViewNombre);
            TextView textCiudad = (TextView) rowView.findViewById(R.id.fSearchTextViewCiudad);
            TextView textCocina = (TextView) rowView.findViewById(R.id.fSearchTextViewCocina);
            RatingBar ratingValoracion = (RatingBar) rowView.findViewById(R.id.fSearchRatingBarValoracion);

            if (textNombre != null) {
                textNombre.setText(restaurante.getNombre());
            }

            if (textCiudad != null) {
                textCiudad.setText(restaurante.getCiudad());
            }

            if (textCocina != null) {
                textCocina.setText(getContext().getString(R.string.resCocina) + ": " + restaurante.getTipo_cocina());
            }

            if (ratingValoracion != null) {
                ratingValoracion.setRating(Float.parseFloat(restaurante.getValoracion()));
            }
        }

        return rowView;
    }
}