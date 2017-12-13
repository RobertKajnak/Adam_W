package com.adam.adam.adam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class PreferencesActivity extends AppCompatActivity {

    ScrollView mScrollViewMain;
    TextView mGreetingText;
    DatePicker mDatePicker;
    Button mButtonConfirm;
    DatePicker mDate;
    ArrayList<CheckBox> mCheckboxes = new ArrayList<>();
    LinearLayout mLinearLayoutCheckboxes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        //Intent intentThatStartedThisActivity = getIntent();

        mGreetingText = (TextView) findViewById(R.id.text_greeting);
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        mButtonConfirm = (Button) findViewById(R.id.button_confirm);
        mLinearLayoutCheckboxes = (LinearLayout) findViewById(R.id.listview_checkboxes);
        mScrollViewMain = (ScrollView) findViewById(R.id.scrollViewMain);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken==null || accessToken.isExpired()){
            mGreetingText.setText("Log in not functioning properly. PLease restart the app");
        }
        else{
            //mGreetingText.setText(
             //       "User ID: "+ accessToken.getUserId());
            mGreetingText.setText("Greetings, John!\nFill in your preferences and we'll find you the perfect match");
        }

        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startChatApplicationIntent = new Intent(PreferencesActivity.this,ChatActivity.class);

                startActivity(startChatApplicationIntent);
            }
        });
        mCheckboxes.add((CheckBox)findViewById(R.id.checkbox_cafe));
        mCheckboxes.add((CheckBox)findViewById(R.id.checkbox_mus));
        mCheckboxes.add((CheckBox)findViewById(R.id.checkbox_old));
        mCheckboxes.add((CheckBox)findViewById(R.id.checkbox_uniq));
        mCheckboxes.add((CheckBox)findViewById(R.id.checkbox_park));
    }

    public  void onCheckboxTicked(View view){
        boolean isAtleastOne = false;
        for (CheckBox cb: mCheckboxes ) {
            if (cb.isChecked()){
                isAtleastOne = true;
                break;
            }
        }
        if (isAtleastOne){
            mButtonConfirm.setVisibility(View.VISIBLE);
            mScrollViewMain.fullScroll(View.FOCUS_DOWN);
        }
        else{
            mButtonConfirm.setVisibility(View.GONE);
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        if (checked){
            //mButtonConfirm.setVisibility(View.VISIBLE);
            mLinearLayoutCheckboxes.setVisibility(View.VISIBLE);
        }

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioGuide:
                if (checked)
                    Toast.makeText(this, "Guide Selected. Will be implemented", Toast.LENGTH_SHORT).show();

                    mDatePicker.setVisibility(View.INVISIBLE);
                    break;
            case R.id.radioTourist:
                if (checked)
                    mDatePicker.setVisibility(View.VISIBLE);

                    break;
        }
    }
}
