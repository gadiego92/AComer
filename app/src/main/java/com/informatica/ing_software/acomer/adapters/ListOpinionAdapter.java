package com.informatica.ing_software.acomer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.objects.Opinion;

import java.util.List;

/**
 * Created by Diego on 20/05/2017.
 */

public class ListOpinionAdapter extends ArrayAdapter<Opinion> {

    public ListOpinionAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListOpinionAdapter(Context context, int resource, List<Opinion> opiniones) {
        super(context, resource, opiniones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            rowView = vi.inflate(R.layout.activity_opinion_item, null);
        }

        Opinion opiniones = getItem(position);

        if (opiniones != null) {
            TextView textUsuario = (TextView) rowView.findViewById(R.id.aOpinionTextViewUsername);
            TextView textDate = (TextView) rowView.findViewById(R.id.aOpinionTextViewDate);
            TextView textOpinion = (TextView) rowView.findViewById(R.id.aOpinionTextViewOpinion);
            RatingBar ratingValoracion = (RatingBar) rowView.findViewById(R.id.aOpinionRatingBarValoracion);

            if (textUsuario != null) {
                textUsuario.setText(opiniones.getUsername());
            }

            if (textDate != null) {
                textDate.setText(opiniones.getDate());
            }

            if (textOpinion != null) {
                textOpinion.setText(opiniones.getOpinion());
            }

            if (ratingValoracion != null) {
                ratingValoracion.setRating(Integer.parseInt(opiniones.getValoracion()));
            }
        }

        return rowView;
    }
}
