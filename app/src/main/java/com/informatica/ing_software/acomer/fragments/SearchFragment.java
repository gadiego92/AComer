package com.informatica.ing_software.acomer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.adapters.ListSearchAdapter;
import com.informatica.ing_software.acomer.json.JSONParser;

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
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_RESTAURANTS = "restaurantes";
    // URL to get favorites restaurants
    private static String restaurantes_search = "http://192.168.1.105/proyecto/p1_restaurantes_search.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private String usuario_email;
    private OnFragmentInteractionListener mListener;

    private ListView lv;

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
    // TODO: Rename and change types and number of parameters
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
        return inflater.inflate(R.layout.fragment_search, container, false);
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
     * Background Async Task to load last 20 restaurants by making HTTP Request
     */
    class GetRestaurants extends AsyncTask<String, Void, List<String[]>> {

        protected List<String[]> doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = null;

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(restaurantes_search, params);

            int success = -1;
            List<String[]> list = new ArrayList<String[]>();

            String[] uno = new String[20];
            String[] dos = new String[20];
            String[] tres = new String[20];
            String[] cuatro = new String[20];
            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Get Restaurant Array
                    String restaurantes = json.getString(TAG_RESTAURANTS);
                    // Converto to JSONArray
                    JSONArray jsonArray = new JSONArray(restaurantes);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        List<String> tmp = new ArrayList<String>();
                        uno[i] = ((JSONObject) jsonArray.get(i)).getString("nm");   // Nombre
                        dos[i] = ((JSONObject) jsonArray.get(i)).getString("cd");   // Ciudad
                        tres[i] = ((JSONObject) jsonArray.get(i)).getString("cn");  // Cocina
                        cuatro[i] = ((JSONObject) jsonArray.get(i)).getString("vl");// Valoracion

                        list.add(uno);
                        list.add(dos);
                        list.add(tres);
                        list.add(cuatro);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (success == 1)
                return list;
            else
                return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(List<String[]> result) {
            // Populate the ListView with the received data
            //setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.fragment_favorite_item, R.id.textViewFavorite, result));
            ListSearchAdapter lsAdapter = new ListSearchAdapter(getActivity(), result.get(0), result.get(1), result.get(2), result.get(3));

            // selecting single ListView item
            lv = (ListView) getActivity().findViewById(R.id.ListViewRestaurantes);
            lv.setAdapter(lsAdapter);
        }
    }
}