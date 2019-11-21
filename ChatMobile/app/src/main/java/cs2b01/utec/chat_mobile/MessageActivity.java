package cs2b01.utec.chat_mobile;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        String username = getIntent().getExtras().getString("username");
        setTitle("@"+username);

        mRecyclerView = findViewById(R.id.main_recycler_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMessages();
    }

    private Activity getActivity() {
        return this;
    }

    public void getMessages(){
        final int userFromId = getIntent().getExtras().getInt("user_from_id");
        final int userToId = getIntent().getExtras().getInt("user_to_id");

        String uri = "http://10.0.2.2:8000/messages/"+userFromId+"/"+userToId;
        JsonArrayRequest  request = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //TODO process response
                        mAdapter = new MessageAdapter(response, getActivity(), userFromId));
                        mRecyclerView.setAdapter(mAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
