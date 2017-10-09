package com.example.jamie.autosearch_test1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
 * Created by Jamie on 9/2/2017.
 */
public class PatientHistory extends Activity {

    public EditText etName, etDiagnosis, etAllergy, etTemp, etRemark;
    //private String name;
    String link;

    Settings settings = new Settings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_patient_history);

        link = settings.getUrl() + "/search";

        //etName = (EditText) findViewById(R.id.etName);
        etDiagnosis = (EditText) findViewById(R.id.etDiagnosis);
        etTemp = (EditText) findViewById(R.id.etTemp);
        etRemark = (EditText) findViewById(R.id.etRemark);
        etAllergy = (EditText) findViewById(R.id.etAllergy);


        //etName.equals("");
        etDiagnosis.equals("");
        etTemp.equals("");
        etRemark.equals("");
        etAllergy.equals("");


    }

    public void newRecord(View view){
        //etName.equals("");
        etDiagnosis.equals("");
        etTemp.equals("");
        etRemark.equals("");
        etAllergy.equals("");
    }

    /**
     * invokes PatientHistoryActivity, doInBackground to insert
     * @param v
     */
    public void appenedHistory(View v) {
        String name = etName.getText().toString();
        String diagnosis = etDiagnosis.getText().toString();
        String temp = etTemp.getText().toString();
        String allergy = etAllergy.getText().toString();
        String remark = etRemark.getText().toString();

        if( etName.equals("") ||etDiagnosis.equals("") ||  etTemp.equals("") || etAllergy.equals("") || etRemark.equals("") ){
            Toast.makeText(this, "field can not be empty!", Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(this, "Inserting History...", Toast.LENGTH_SHORT).show();
            new PatientHistoryActivity(this).execute(name,diagnosis, temp, allergy, remark);
            startActivity(new Intent(this,HomeActivity.class));
        }
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {

        String patient_name;

        public EditText etName, etDiagnosis, etAllergy, etTemp, etRemark, etSearch;
        String JSON_response="";
        Context context;
        AlertDialog alertDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("TDy's JSON");
        }
        @Override
        protected String doInBackground(String... arg) {
            patient_name = arg[0];
            try {
                URL url = new URL(link);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data = URLEncoder.encode("patient_name","UTF-8")+"="+ URLEncoder.encode(patient_name,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while ((patient_name = bufferedReader.readLine())!=null){
                    stringBuilder.append(patient_name+"\n");
                    JSON_response += patient_name;
                }
                bufferedReader.close();
                outputStream.close();
                connection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //etName = (EditText) findViewById(R.id.etName);
            etDiagnosis = (EditText) findViewById(R.id.etDiagnosis);
            etTemp = (EditText) findViewById(R.id.etTemp);
            etRemark = (EditText) findViewById(R.id.etRemark);
            etAllergy = (EditText) findViewById(R.id.etAllergy);



            if(JSON_response.contains("Success"))
            {
                Intent i = new Intent(context,HomeActivity.class);
                context.startActivity(i);
                alertDialog.setMessage(JSON_response);
                alertDialog.show();
                alertDialog.cancel();

                //Toast.makeText(LoginActivity.class,"username and password characters must be greter than 1.",Toast.LENGTH_SHORT).show();

            }
        }
    }


}
