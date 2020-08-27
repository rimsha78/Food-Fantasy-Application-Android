package com.example.food_fantasy.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_fantasy.R;
import com.example.food_fantasy.home_baker;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.food_fantasy.splash.pref;

public class Verification_Signup extends AppCompatActivity {

    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification__signup);

        final EditText input = (EditText) findViewById(R.id.verifycode);
        Button verify_button = (Button) findViewById((R.id.verify_button));
//        SharedPreferences pref = Globle_Application.getInstance().getSharedPreferences("api_hit", 0); // 0 - for private mode
//
        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do{
                    code = pref.getString("api_response", null);
                }while (code == null);

                try{
                    JSONObject json = new JSONObject(code);

                    String user_code = input.getText().toString();

                    code = json.getString("code");
                    code = code.substring(0,4);
                    if(Integer.parseInt(code) == Integer.parseInt(user_code)) {
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("baker_name", json.getString("name"));
                        editor.putString("baker_email", json.getString("email"));
                        editor.apply();

                        Intent intent;
                        intent = new Intent(getApplicationContext(), home_baker.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Invalid Code Entered. Check Your Email!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
