package com.example.jamie.autosearch_test1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.jamie.autosearch_test1.settings.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Jamie on 7/13/2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
/*    LoginActivity loginActivity;
    LocationUploader locationUploader;
    MapsActivity mapsActivity;*/
    String login_name;
    String login_pass;

    AlertDialog alertDialog;
    Context context;
    Settings settings = new Settings();
    BackgroundTask(Context ctx)
    {
        context = ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Information....");
        settings.setUrl("http://192.168.43.41");
    }

    @Override
    protected String doInBackground(String... params) {

        String login_url = settings.getUrl() + "/login/";
        //String login_url = SignupActivity.link;
        String method = params[0];

         if(method.equals("login"))
        {
            login_name = params[1];
            login_pass = params[2];
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("login_name","UTF-8")+"="+ URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+ URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine())!=null)
                {
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            if(result.equals("Registration Success..."))
            {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }

            else if(result.contains("Success"))
            {
                Intent i = new Intent(context,HomeActivity.class);
                context.startActivity(i);
                alertDialog.setMessage(result);
                alertDialog.show();
                alertDialog.cancel();

                //Toast.makeText(LoginActivity.class,"username and password characters must be greter than 1.",Toast.LENGTH_SHORT).show();

            }
            else
            {
                alertDialog.setMessage(result);
                alertDialog.show();
            }

        }catch (Exception ex){
            Toast.makeText(context,"exception"+ result, Toast.LENGTH_LONG).show();
        }

    }
}