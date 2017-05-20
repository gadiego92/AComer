package com.informatica.ing_software.acomer.json;

import android.support.v4.util.Pair;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Diego on 03/12/2016.
 */

public class JSONParser {
    //static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONObject makeHttpRequest(String link, List<Pair<String, String>> params) {

        // Making HTTP request
        // Codificar varaibles POST
        try {
            String select = "";
            String data = "";
            for (int i = 0; i < params.size(); i++) {
                if (i == params.size() - 1) {
                    data += URLEncoder.encode(params.get(i).first.toString(), "UTF-8") + "="
                            + URLEncoder.encode(params.get(i).second.toString(), "UTF-8");
                } else {
                    data += URLEncoder.encode(params.get(i).first.toString(), "UTF-8") + "="
                            + URLEncoder.encode(params.get(i).second.toString(), "UTF-8") + "&";
                }
            }

            // URL
            URL url = new URL(link);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            OutputStreamWriter wr = new OutputStreamWriter(out);
            wr.write(data);
            wr.flush();
            wr.close();

            InputStream in = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                //sb.append(line);
                //break;
            }

            reader.close();
            conn.disconnect();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Server Error " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;
    }
}