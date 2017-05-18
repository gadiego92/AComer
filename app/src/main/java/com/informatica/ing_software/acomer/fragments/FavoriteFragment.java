package com.informatica.ing_software.acomer.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.RestaurantActivity;
import com.informatica.ing_software.acomer.adapters.ListFavouriteAdapter;
import com.informatica.ing_software.acomer.adapters.ListSearchAdapter;
import com.informatica.ing_software.acomer.json.JSONParser;
import com.informatica.ing_software.acomer.objects.Restaurante;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FavoriteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoriteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteFragment extends Fragment {
    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USUARIO_EMAIL = "usuario_email";
    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_RESTAURANTS = "restaurantes";
    // URL to get favorites restaurants
    //private final String USUARIOS_FAVORITOS = "http://amaterasu.unileon.es/benten/aComerAndroid/p2_usuarios_favoritos.php";
    private final String USUARIOS_FAVORITOS = "http://192.168.0.14/proyecto/aComerAndroid/p2_usuarios_favoritos.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private String usuario_email;
    private OnFragmentInteractionListener mListener;
    private ArrayList<String[]> favouritesList;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email User's email.
     * @return A new instance of fragment FavoriteFragment.
     */
    public static FavoriteFragment newInstance(String email) {
        FavoriteFragment fragment = new FavoriteFragment();

        Bundle args = new Bundle();
        args.putString(USUARIO_EMAIL, email);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            usuario_email = getArguments().getString(USUARIO_EMAIL);

            // Get favorite Restaurants
            new GetFavoritos().execute(usuario_email);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        // Get the listView
        ListView lv = (ListView) view.findViewById(R.id.ffListViewFavourite);

        // Item Click Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                // Create a new intent
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                // Send the restaurant id to the Restaurant Activity
                intent.putExtra("restaurante_id", ((Restaurante) adapterView.getAdapter().getItem(position)).getId());
                // Start Restaurant Activity
                startActivity(intent);
            }
        });

        // Item Long Click Listener
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                // Create the AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Add the icon
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                // Add the title
                builder.setTitle(R.string.dialogTitle);
                // Add the message
                builder.setMessage("¿Desea eliminar el restaurante '"
                        + ((Restaurante) adapterView.getAdapter().getItem(position)).getNombre()
                        + "' de su lista de favoritos?");

                // Add the positive button
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Delete the favourite restaurant from the database
                    }
                });

                // Add the negative button
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Do nothing
                    }
                });

                // Show the AlertDialog
                builder.show();

                // To avoid onItemClick event after onItemLongClick
                return true;
            }
        });

        // return inflate the layout for this fragment
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // Show a message if the favourite restaurants list is empty
    public void showEmptyListMessage() {
        Toast.makeText(getActivity(), "Lista de favoritos vacia!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Background Async Task to load all favorite restaurants by making HTTP Request
     */
    private class GetFavoritos extends AsyncTask<String, Void, List<Restaurante>> {

        protected List<Restaurante> doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>("email", args[0]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(USUARIOS_FAVORITOS, params);

            int success = -1;
            List<Restaurante> lista_restaurantes = new ArrayList<Restaurante>();

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Get Restaurant Array
                    String restaurantes = json.getString(TAG_RESTAURANTS);
                    // Converto to JSONArray
                    JSONArray jsonArray = new JSONArray(restaurantes);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        int id = Integer.parseInt(((JSONObject) jsonArray.get(i)).getString("id"));     // Id
                        String nm = ((JSONObject) jsonArray.get(i)).getString("nm");                    // Nombre
                        String cd = ((JSONObject) jsonArray.get(i)).getString("cd");                    // Ciudad
                        String tl = ((JSONObject) jsonArray.get(i)).getString("tl");                    // Telefono
                        String cn = ((JSONObject) jsonArray.get(i)).getString("cn");                    // Cocina
                        String vl = ((JSONObject) jsonArray.get(i)).getString("vl");                    // Valoracion
                        // list.add(((JSONObject) jsonArray.get(i)).getString("nm"));

                        lista_restaurantes.add(new Restaurante(id, nm, cd, tl, cn, vl));
                    }
                }

                return lista_restaurantes;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(List<Restaurante> result) {
            // seleccionamos el listView
            ListView lv = (ListView) getActivity().findViewById(R.id.ffListViewFavourite);

            // cogemos los datos con el ListSearchAdapter y los mostramos
            ListFavouriteAdapter customAdapter = new ListFavouriteAdapter(getActivity(), R.layout.fragment_favorite_item, result);
            lv.setAdapter(customAdapter);

            if(result.size() == 0) {
                showEmptyListMessage();
            }
        }
    }
}