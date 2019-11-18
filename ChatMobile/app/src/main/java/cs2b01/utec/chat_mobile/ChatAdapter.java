package cs2b01.utec.chat_mobile;

import android.content.Context;
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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject element = elements.getJSONObject(position);
            String name = element.getString("name")+" "+element.getString("fullname");
            String username = element.getString("username");
            holder.first_line.setText(name);
            holder.second_line.setText(username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return elements.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

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
