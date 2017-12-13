package com.adam.adam.adam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {

    TextView mMeassage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMeassage = (TextView) findViewById(R.id.msg);


    }

    public void onSendButton(View view) {
        mMeassage.setText("");
    }

}
