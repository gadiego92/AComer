package com.informatica.ing_software.acomer.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.activities.RestaurantActivity;
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
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USUARIO_EMAIL = "usuario_email";
    private static final String RESTAURANTE_ID = "restaurante_id";
    private static final String BUSQUEDA = "busqueda";
    private static final String TIPO_BUSQUEDA = "tipo_busqueda";
    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_RESTAURANTS = "restaurantes";
    // URL to get favorites restaurants
    //private static String LAST_RESTAURANTS_ADDED = "http://amaterasu.unileon.es/benten/aComerAndroid/p1_restaurantes_search.php";
    //private static String ADD_FAVORITOS = "http://amaterasu.unileon.es/benten/aComerAndroid/p1_add_favorito.php";
    private final String RESTAURANTS_SEARCH = "http://192.168.0.14/proyecto/aComerAndroid/p1_restaurantes_search.php";
    private final String ADD_FAVORITOS = "http://192.168.0.14/proyecto/aComerAndroid/p1_add_favorito.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private OnFragmentInteractionListener mListener;
    // Extras
    private String usuario_email;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email User's email.
     * @return A new instance of fragment SearchFragment.
     */
    public static SearchFragment newInstance(String email) {
        SearchFragment fragment = new SearchFragment();

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

            // Get last 20 added Restaurants ("1")
            new GetRestaurants().execute("1", "");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // Get the listView
        ListView lv = (ListView) view.findViewById(R.id.fSearchListViewSearch);

        // Item Click Listener
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                // Create a new intent
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                // Send the username and the restaurant id to the Restaurant Activity
                intent.putExtra(RESTAURANTE_ID, ((Restaurante) adapterView.getAdapter().getItem(position)).getId());
                intent.putExtra(USUARIO_EMAIL, usuario_email);
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
                builder.setTitle(R.string.addDialogTitle);
                // Add the message
                builder.setMessage("¿Desea añadir el restaurante '"
                        + ((Restaurante) adapterView.getAdapter().getItem(position)).getNombre()
                        + "' a su lista de favoritos?");

                final String restaurante_id = String.valueOf(((Restaurante) adapterView.getAdapter().getItem(position)).getId());

                // Add the positive button
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Add the favourite restaurant from the database
                        new AddFavorito().execute(usuario_email, restaurante_id);
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

        // Create a listener for the search bar
        final SearchView searchBar = (SearchView) view.findViewById(R.id.fSearchSearchView);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.trim().length() > 1) {
                    // Leave the focus
                    searchBar.clearFocus();

                    // Get Restaurant Search (2)
                    new GetRestaurants().execute("2", s.trim());
                } else {
                    Toast.makeText(getActivity(), "Búsqueda demasiado corta", Toast.LENGTH_LONG).show();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchBar.setSubmitButtonEnabled(true);
        searchBar.setIconifiedByDefault(false);

        // return inflate the layout for this fragment
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    // Show a message when a favourite restaurante is added
    public void showFavAddedMessage(int success) {
        if (success == 1) {
            Toast.makeText(getActivity(), "Favorito añadido con exito!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Error añadiendo el favorito!", Toast.LENGTH_LONG).show();
        }
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
     * Background Async Task to load last 20 restaurants added by making HTTP Request
     */
    private class GetRestaurants extends AsyncTask<String, Void, List<Restaurante>> {

        protected List<Restaurante> doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>(TIPO_BUSQUEDA, args[0]));
            params.add(new Pair<String, String>(BUSQUEDA, args[1]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(RESTAURANTS_SEARCH, params);

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
         * After completing background task update the ListView
         **/
        protected void onPostExecute(List<Restaurante> result) {
///////////////////////////////
// PROBAR ESTO SI FUNCIONA
///////////////////////////////
            if (result.size() == 0) {
                Toast.makeText(getActivity(), "La consulta ha devuelto 0 resutlados!", Toast.LENGTH_LONG).show();
            } else {
                // seleccionamos el listView
                ListView lv = (ListView) getActivity().findViewById(R.id.fSearchListViewSearch);

                // cogemos los datos con el ListSearchAdapter y los mostramos
                ListSearchAdapter customAdapter = new ListSearchAdapter(getActivity(), R.layout.fragment_search_item, result);
                lv.setAdapter(customAdapter);
            }
        }
    }

    /**
     * Background Async Task to add a favorite restaurant by making HTTP Request
     */
    private class AddFavorito extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>(USUARIO_EMAIL, args[0]));
            params.add(new Pair<String, String>(RESTAURANTE_ID, args[1]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(ADD_FAVORITOS, params);

            int success = -1;

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
         * After completing background task show a message
         **/
        protected void onPostExecute(String result) {
            // Show a message after delete
            showFavAddedMessage(Integer.parseInt(result));
        }
    }
}