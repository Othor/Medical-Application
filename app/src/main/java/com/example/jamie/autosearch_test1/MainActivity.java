package com.example.jamie.autosearch_test1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.jamie.autosearch_test1.app.AppController;
import com.example.jamie.autosearch_test1.model.DataModel;
import com.example.jamie.autosearch_test1.settings.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    com.example.jamie.autosearch_test1.adapter.Adapter adapter;
    SwipeRefreshLayout swipe;
    ListView list_view;
    Settings settings = new Settings();

    //10.5.55.237
    public static final String url_data = "/list_patients/";
    public static final String url_user = "/search/";

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_AGE = "age";
    public static final String TAG_EMAIL = "email";

    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";



    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        list_view = (ListView) findViewById(R.id.list_view);

        adapter = new com.example.jamie.autosearch_test1.adapter.Adapter(this, listData);
        list_view.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

       swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           callData();
                       }
                   }
        ); /**/

        System.out.println("inside Oncreate: --------------------------------------------------------");

    }

    private void callData() {
        listData.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        settings.setUrl("http://192.168.43.41");

        // Creating volley request obj
        JsonArrayRequest jArr = new JsonArrayRequest(settings.getUrl()+url_data, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, response.toString());

                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        DataModel item = new DataModel();

                        item.setId(obj.getString(TAG_ID));
                        item.setName(obj.getString(TAG_NAME));
                        item.setAge(obj.getString(TAG_AGE));
                        item.setEmail(obj.getString(TAG_EMAIL));

                        System.out.println("ID : --------------------------------------------------------" +  obj.getString(TAG_ID) );
                        System.out.println("Name : --------------------------------------------------------" + obj.getString(TAG_NAME));

                        listData.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();
                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    @Override
    public void onRefresh() {
        callData();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        cariData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.type_name));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    private void cariData(final String keyword) {
        settings.setUrl("http://192.168.43.41");
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading...");
        pDialog.show();
        System.out.println("Response sysout:--------------------------------------------- " + keyword);

        StringRequest strReq = new StringRequest(Request.Method.POST,settings.getUrl()+ url_user, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Log.e("Response: ", response.toString());
                System.out.println("---------------------------Response :" + response);

                try {
                    JSONObject jObj = new JSONObject(response);

                    //int value = jArray.getInt(TAG_VALUE);
                    int value = jObj.getInt(TAG_VALUE);

                    if (value == 1) {
                        listData.clear();
                        adapter.notifyDataSetChanged();

                        String getObject = jObj.getString(TAG_RESULTS);
                        JSONArray jsonArray = new JSONArray(getObject);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            DataModel data = new DataModel();

                            data.setId(obj.getString(TAG_ID));
                            data.setName(obj.getString(TAG_NAME));
                            data.setAge(obj.getString(TAG_AGE));
                            data.setEmail(obj.getString(TAG_EMAIL));
                            data.getAge();
                            data.getEmail();

                            listData.add(data);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("keyword", keyword);

                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    public void editHistory(View view){
        startActivity(new Intent(this,PatientHistory.class));
    }

}
