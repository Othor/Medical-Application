package com.example.jamie.autosearch_test1.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.jamie.autosearch_test1.MainActivity;
import com.example.jamie.autosearch_test1.R;
import com.example.jamie.autosearch_test1.app.AppController;
import com.example.jamie.autosearch_test1.model.DataModel;
import com.example.jamie.autosearch_test1.settings.Settings;
import com.example.jamie.autosearch_test1.util.Connectivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InsertHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsertHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_AGE = "age";
    public static final String TAG_EMAIL = "email";

    private static final String TAG = InsertHistoryFragment.class.getSimpleName();

    public static final String TAG_RESULTS = "results";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    //10.5.55.237
    public static final String url_data = "/list_patients/";
    public static final String url_user = "/search/";

    String tag_json_obj = "json_obj_req";

    public InsertHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsertHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsertHistoryFragment newInstance(String param1, String param2) {
        InsertHistoryFragment fragment = new InsertHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    ProgressDialog pDialog;
    List<DataModel> listData = new ArrayList<DataModel>();
    com.example.jamie.autosearch_test1.adapter.Adapter adapter;

    @InjectView(R.id.swipe_refresh)
    SwipeRefreshLayout swipe;
    @InjectView(R.id.list_view)
    ListView list_view;
    Settings settings = new Settings();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_insert_history, container, false);
        getActivity().setTitle("Insert History");
        ButterKnife.inject(this, view);

        adapter = new com.example.jamie.autosearch_test1.adapter.Adapter(getActivity(), listData);
        list_view.setAdapter(adapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           callData();
                       }
                   }
        );

        return view;
    }

    private void callData() {
        listData.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        settings.setUrl(Connectivity.BASE_URL);

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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.type_name));
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void cariData(final String keyword) {
        settings.setUrl(Connectivity.BASE_URL);
        pDialog = new ProgressDialog(getContext());
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
                        Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

}
