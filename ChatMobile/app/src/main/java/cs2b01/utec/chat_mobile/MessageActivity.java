package cs2b01.utec.chat_mobile;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    public Activity getActivity(){
        return this;
    }


    public void getMessages(){
        final int userFromId = getIntent().getExtras().getInt("user_from_id");
        final int userToId = getIntent().getExtras().getInt("user_to_id");

        String uri = "http://10.0.2.2:8000/messages/"+userFromId+"/"+userToId;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        //TODO process response
                        mAdapter = new MessageAdapter(response, getActivity(), userFromId);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }


    public void sendMessage(View view){

        EditText mensaje = (EditText)findViewById(R.id.txtMessage);
        String content = mensaje.getText().toString();
        String user_from_id = getIntent().getExtras().get("user_from_id").toString();
        String user_to_id = getIntent().getExtras().get("user_to_id").toString();

        Map<String, String> messages = new HashMap<>();
        messages.put("user_from_id",user_from_id);
        messages.put("user_to_id", user_to_id);
        messages.put("content", content);

        JSONObject jsonMessage = new JSONObject(messages);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/messages",
                jsonMessage,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

        EditText editText = (EditText)findViewById(R.id.txtMessage);
        editText.setText("");
    }


}
