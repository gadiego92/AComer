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
import android.widget.TextView;

import com.informatica.ing_software.acomer.R;
import com.informatica.ing_software.acomer.json.JSONParser;
import com.informatica.ing_software.acomer.objects.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {
    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USUARIO_EMAIL = "usuario_email";
    // JSON Node names
    private final String TAG_SUCCESS = "success";
    private final String TAG_USERS = "usuario";
    // URL to get favorites restaurants
    private final String USUARIOS_INFORMACION = "http://192.168.0.14/proyecto/aComerAndroid/p3_usuarios_informacion.php";
    // Creating JSON Parser object
    private JSONParser jParser = new JSONParser();
    private String usuario_email;
    private OnFragmentInteractionListener mListener;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email User's email.
     * @return A new instance of fragment MyAccountFragment.
     */
    public static MyAccountFragment newInstance(String email) {
        MyAccountFragment fragment = new MyAccountFragment();
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
            new GetInformacionPersonal().execute(usuario_email);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false);
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
     * Background Async Task to load all favorite restaurants by making HTTP Request
     */
    class GetInformacionPersonal extends AsyncTask<String, Void, Usuario> {

        protected Usuario doInBackground(String... args) {
            // Building Parameters
            List<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
            params.add(new Pair<String, String>("email", args[0]));

            // Getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(USUARIOS_INFORMACION, params);

            int success = -1;

            try {
                // Checking for SUCCESS TAG
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // Get Restaurant Array
                    String usuario = json.getString(TAG_USERS);
                    // Converto to JSONArray
                    JSONArray jsonArray = new JSONArray(usuario);

                    String nm = ((JSONObject) jsonArray.get(0)).getString("nm");    // Nombre
                    String ap = ((JSONObject) jsonArray.get(0)).getString("ap");    // Apellido
                    String tl = ((JSONObject) jsonArray.get(0)).getString("tl");    // Telefono
                    String em = ((JSONObject) jsonArray.get(0)).getString("em");    // Email

                    Usuario user = new Usuario(nm, ap, tl, em);

                    return user;
                } else {
                    return null;
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(Usuario result) {
            ((TextView) getActivity().findViewById(R.id.miTextViewNombre2)).setText(result.getNombre());
            ((TextView) getActivity().findViewById(R.id.miTextViewApellido2)).setText(result.getApellido());
            ((TextView) getActivity().findViewById(R.id.miTextViewTelefono2)).setText(result.getTelefono());
            ((TextView) getActivity().findViewById(R.id.miTextViewEmail2)).setText(result.getEmail());
        }
    }
}
