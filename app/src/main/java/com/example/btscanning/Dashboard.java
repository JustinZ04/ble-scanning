package com.example.btscanning;

import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    ClassAdapter adapter;

    List<Class> classList;

    private RequestQueue myQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        classList = new ArrayList<>();

        myQueue = SingletonAPICalls.getInstance(this).getRequestQueue();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadClasses();



    }

    private void easyToast(String string){
        final String String;

        String = string;

        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(Dashboard.this, String, Toast.LENGTH_SHORT).show();
            }
        });

        return;
    }

    private void loadClasses(){
        String loginURL = Constants.URL + Constants.Classes + SaveSharedPreference.getPrefProfNid(Dashboard.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray classes = new JSONArray(response);

                            for (int i = 0; i < classes.length(); i++) {
                                JSONObject classObject = classes.getJSONObject(i);

                                Class newClass = new Class(
                                        classObject.getString("courseID"),
                                        classObject.getString("className"),
                                        classObject.getString("startTime"),
                                        classObject.getString("endTime"));

                                classList.add(newClass);

                            }

                            adapter = new ClassAdapter(Dashboard.this, classList);
                            recyclerView.addItemDecoration(new DividerItemDecoration(Dashboard.this,LinearLayoutManager.VERTICAL));
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                easyToast("Failed to get Classes from Database!");
            }
        });

        myQueue.add(stringRequest);

    }
}
