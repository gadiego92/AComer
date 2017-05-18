package com.informatica.ing_software.acomer.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.RestaurantActivity;
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

    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_RESTAURANTS = "restaurantes";
    // URL to get favorites restaurants
    //private static String RESTAURANTES_SEARCH = "http://amaterasu.unileon.es/benten/aComerAndroid/p1_restaurantes_search.php";
    private final String RESTAURANTES_SEARCH = R.string.urlServer + "p1_restaurantes_search.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private String usuario_email;
    private OnFragmentInteractionListener mListener;

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

            // Get favorite Restaurants
            new GetRestaurants().execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ListView lv = (ListView) view.findViewById(R.id.ListViewRestaurantes);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
////////////////////////////////////////////////////////
// PROBAR ESTO
////////////////////////////////////////////////////////
                Intent intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra("restaurante_id", ((Restaurante) adapterView.getAdapter().getItem(position)).getId());
                /*
                intent.putExtra("restaurante_id", ((Restaurante) arg0.getAdapter().getItem(position)).getId());
                intent.putExtra("restaurante_nombre", ((Restaurante) arg0.getAdapter().getItem(position)).getNombre());
                intent.putExtra("restaurante_ciudad", ((Restaurante) arg0.getAdapter().getItem(position)).getCiudad());
                intent.putExtra("restaurante_telefono", ((Restaurante) arg0.getAdapter().getItem(position)).getTelefono());
                intent.putExtra("restaurante_tipo_cocina", ((Restaurante) arg0.getAdapter().getItem(position)).getTipo_cocina());
                intent.putExtra("restaurante_valoracion", ((Restaurante) arg0.getAdapter().getItem(position)).getValoracion());
                */
                startActivity(intent);
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
     * Background Async Task to load last 20 restaurants by making HTTP Request
     */
    class GetRestaurants extends AsyncTask<String, Void, List<Restaurante>> {

        protected List<Restaurante> doInBackground(String... args) {
            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(RESTAURANTES_SEARCH, new ArrayList<Pair<String, String>>());

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
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(List<Restaurante> result) {
            // seleccionamos el listView
            ListView lv = (ListView) getActivity().findViewById(R.id.ListViewRestaurantes);

            // cogemos los datos con el ListSearchAdapter y los mostramos
            ListSearchAdapter customAdapter = new ListSearchAdapter(getActivity(), R.layout.fragment_search_item, result);
            lv.setAdapter(customAdapter);
        }
    }
}