package cs2b01.utec.chat_mobile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public JSONArray elements;
    private Context context;
    public int userFromId;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_view, parent, false);
        return  new ViewHolder(view);
    }

    public void goToMessageActivity(int user_id, String username){
        Intent intent = new Intent(this.context, MessageActivity.class);
        intent.putExtra("user_from_id", userFromId);
        intent.putExtra("user_to_id", user_id);
        intent.putExtra("username", username);
        this.context.startActivity(intent);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject element = elements.getJSONObject(position);
            String name = element.getString("name")+" "+element.getString("fullname");
            final String username = element.getString("username");
            final int user_id = element.getInt("id");
            holder.first_line.setText(name);
            holder.second_line.setText(username);

            //OnClick open next activity:  Message Activity
            holder.container.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goToMessageActivity(user_id, username);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return elements.length();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView first_line, second_line;
        RelativeLayout container;

        public ViewHolder(View itemView){
            super(itemView);
            first_line = itemView.findViewById(R.id.element_view_first_line);
            second_line = itemView.findViewById(R.id.element_view_second_line);
            container = itemView.findViewById(R.id.element_view_container);
        }
    }

    public ChatAdapter(JSONArray elements, Context context, int userFromId){
        this.elements = elements;
        this.context = context;
        this.userFromId = userFromId;
    }


}
