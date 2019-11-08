package cs2b01.utec.chat_mobile;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public Activity getActivity(){
        return this;
    }


    public void Go_login(View view){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


}
