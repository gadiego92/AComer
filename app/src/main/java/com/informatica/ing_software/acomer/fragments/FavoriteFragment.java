package com.informatica.ing_software.acomer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.json.JSONParser;

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
public class FavoriteFragment extends ListFragment {
    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USUARIO_EMAIL = "usuario_email";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RESTAURANTS = "restaurantes";
    // URL to get favorites restaurants
    private static String USUARIOS_FAVORITOS = "http://192.168.0.14/proyecto/p2_usuarios_favoritos.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private String usuario_email;
    private OnFragmentInteractionListener mListener;

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
    // TODO: Rename and change types and number of parameters
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
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Background Async Task to load all favorite restaurants by making HTTP Request
     */
    class GetFavoritos extends AsyncTask<String, Void, List<String>> {

        protected List<String> doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>("email", args[0]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(USUARIOS_FAVORITOS, params);

            int success = -1;
            List<String> list = new ArrayList<String>();

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Get Restaurant Array
                    String restaurantes = json.getString(TAG_RESTAURANTS);
                    // Converto to JSONArray
                    JSONArray jsonArray = new JSONArray(restaurantes);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(((JSONObject) jsonArray.get(i)).getString("nm"));
                    }
                } else {
                    list.add("Lista de favoritos vacia");
                }

                return list;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(List<String> result) {
            // Populate the ListView with the received data
            setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.fragment_favorite_item, R.id.textViewFavorite, result));
        }
    }
}