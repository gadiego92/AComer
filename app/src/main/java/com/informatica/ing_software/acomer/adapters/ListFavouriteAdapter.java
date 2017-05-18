package com.informatica.ing_software.acomer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.objects.Restaurante;

import java.util.List;

/**
 * Created by Diego on 18/05/2017.
 */

////////////////////////////////////////////////////////
// PROBAR ESTO
////////////////////////////////////////////////////////

public class ListFavouriteAdapter extends ArrayAdapter<Restaurante> {

    public ListFavouriteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListFavouriteAdapter(Context context, int resource, List<Restaurante> restaurantes) {
        super(context, resource, restaurantes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            rowView = vi.inflate(R.layout.fragment_favorite_item, null);
        }

        Restaurante restaurante = getItem(position);

        if (restaurante != null) {
            TextView textNombre = (TextView) rowView.findViewById(R.id.textViewFavourite);

            if (textNombre != null) {
                textNombre.setText(restaurante.getNombre());
            }
        }

        return rowView;
    }
}