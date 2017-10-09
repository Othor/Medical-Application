package com.example.jamie.autosearch_test1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.jamie.autosearch_test1.settings.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Jamie on 9/15/2017.
 */
public class PatientHistoryActivity extends AsyncTask<String, Void, String> {

    private String data;
    Settings settings = new Settings();
    public static String link ;
    private Context context;
    private String response = "";
    private String line = "";
    private BufferedReader bufferedReader;

    public PatientHistoryActivity(Context context) {
        this.context = context;
    }
    protected void onPreExecute() {

    }

    /**
     * invoked by PatientHistory.java, appenedHistory
     * @param arg0
     * @return return the http response
     */
    @Override
    protected String doInBackground(String... arg0) {

        String _diagnosis = arg0[0];
        String _temp = arg0[1];
        String _remark = arg0[2];
        String _allergy = arg0[3];

        try {

            data = "&diagnosis=" + URLEncoder.encode(_diagnosis, "UTF-8");
            data += "&temprature=" + URLEncoder.encode(_temp, "UTF-8");
            data += "&remark=" + URLEncoder.encode(_remark, "UTF-8");
            data += "&allergy=" + URLEncoder.encode(_allergy, "UTF-8");

            link = settings.getUrl() + "/list_patients" + data;

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setDoInput(true);

            OutputStream outputStream = con.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data =
                    URLEncoder.encode("diagnosis","UTF-8")+"="+ URLEncoder.encode(_diagnosis,"UTF-8")+"&"+
                    URLEncoder.encode("temprature","UTF-8")+"="+ URLEncoder.encode(_temp,"UTF-8")+"&"+
                    URLEncoder.encode("remark","UTF-8")+"="+ URLEncoder.encode(_remark,"UTF-8")+"&"+
                    URLEncoder.encode("allergy","UTF-8")+"="+ URLEncoder.encode(_allergy,"UTF-8");
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = con.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

            while ((line = bufferedReader.readLine())!=null)
            {
                response+= line;
            }
            bufferedReader.close();
            inputStream.close();
            con.disconnect();
            return response;

        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }


    /**
     * display success on successful insertion JSON response
      * @param result
     */
    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                String query_result = jsonObj.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Data inserted successfully.", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Data could not be inserted.", Toast.LENGTH_SHORT).show();
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
