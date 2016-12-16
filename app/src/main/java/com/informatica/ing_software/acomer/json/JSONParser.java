package com.informatica.ing_software.acomer.json;

import android.support.v4.util.Pair;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
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

            // Codificar varaibles POST
            // String data = URLEncoder.encode("select", "UTF-8") + "=" + URLEncoder.encode(select, "UTF-8");
            //data  += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(select, "UTF-8");
            //data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(select, "UTF-8");
            URL url = new URL(link);

            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            InputStreamReader isr = new InputStreamReader(conn.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
                //sb.append(line);
                //break;
            }

            //is.close();
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