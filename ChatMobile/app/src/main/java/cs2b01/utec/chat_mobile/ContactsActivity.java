package cs2b01.utec.chat_mobile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ContactsActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        int user_id = getIntent().getExtras().getInt("user_id");
        String username = getIntent().getExtras().getString("username");
        setTitle("@"+username);
        mRecyclerView = findViewById(R.id.main_recycler_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUsers();

    }

    public Activity getActivity(){
        return this;
    }

    //getting contacts from server
    public void getUsers(){
        String uri="http://10.0.2.2:8000/users";
        JSONArray jsonMessage = new JSONArray();
        final int user_id = getIntent().getExtras().getInt("user_id");

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                jsonMessage, //empty message
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Step1. Create element view for each user
                        //Step2. Create dinamically elements view and inject to Recyc View
                        mAdapter = new ChatAdapter(response, getActivity(), user_id);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //TODO process the error
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
