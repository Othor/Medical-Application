package com.example.jamie.autosearch_test1;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.jamie.autosearch_test1.settings.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Jamie on 9/8/2017.
 */
public class SignupActivity extends AsyncTask<String, Void, String> {
    static String data;
    public static String link = "/login";
    private Context context;

    Settings settings = new Settings();

    public SignupActivity(Context context) {
        this.context = context;
    }
    protected void onPreExecute() {
    }

    /**
     * receives arguments and encodes to request
     * @param arg0
     * @return http url result
     */
    @Override
    protected String doInBackground(String... arg0) {
        String firstName = arg0[0];
        String lastName = arg0[1];
        String emailAddress = arg0[2];
        String passWord = arg0[3];
        String phoneNumber = arg0[4];
        BufferedReader bufferedReader;
        String result;

        try {
            data = "?firstname=" + URLEncoder.encode(firstName, "UTF-8");
            data += "?lastname=" + URLEncoder.encode(lastName, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
            data += "&phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");

            URL url = new URL(settings.getUrl()+link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream outputStream = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data = URLEncoder.encode("firstname","UTF-8")+"="+ URLEncoder.encode(firstName,"UTF-8")+"&"+
                    URLEncoder.encode("lastname","UTF-8")+"="+ URLEncoder.encode(lastName,"UTF-8")+"&"+
                    URLEncoder.encode("phonenumber","UTF-8")+"="+ URLEncoder.encode(phoneNumber,"UTF-8")+"&"+
                    URLEncoder.encode("emailaddress","UTF-8")+"="+ URLEncoder.encode(emailAddress,"UTF-8")+"&"+
                    URLEncoder.encode("password","UTF-8")+"="+ URLEncoder.encode(passWord,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    /**
     * based on JSON response display success or failure of method execution
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                Log.d(result, "JASOOORESULTT : ");
                Log.d(jsonStr, "JASOOOOOONNNN : ");
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully. Signup successfull.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {

                e.printStackTrace();
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
        }
    }
}