package com.example.food_fantasy.ui.signup;


import android.app.Activity;

import com.example.food_fantasy.ApiHit;
import com.example.food_fantasy.ui.login.LoginActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_fantasy.R;
import com.example.food_fantasy.ui.login.LoginViewModel;
import com.example.food_fantasy.ui.login.LoginViewModelFactory;
import com.example.food_fantasy.home_baker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.CollationElementIterator;

public class SignUpActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Handler mHandler = new Handler();
    // private TextView content;
    public static EditText signup_frst_name,signup_last_name,signup_user_email,signup_password,signup_confirm_passwd,signup_phone;
    public static String fname,lname,email,pass,cpass,phone, code = "";
    private ApiHit process = new ApiHit();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
//                .get(LoginViewModel.class);
        final TextView signUpbutton = findViewById(R.id.signup);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final TextView login_link= findViewById(R.id.move_to_login);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading_signup);
        signup_frst_name = findViewById(R.id.signup_firstname);
        signup_last_name = findViewById(R.id.signup_last_name);
        signup_user_email = findViewById(R.id.signup_username);
        signup_password = findViewById(R.id.signup_password);
        signup_confirm_passwd = findViewById(R.id.signup_confirmpassword);
        signup_phone = findViewById(R.id.editText_phone);




        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });


//        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
//            @Override
//            public void onChanged(@Nullable LoginFormState loginFormState) {
//                if (loginFormState == null) {
//                    return;
//                }
//                loginButton.setEnabled(loginFormState.isDataValid());
//                if (loginFormState.getUsernameError() != null) {
//                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
//                }
//                if (loginFormState.getPasswordError() != null) {
//                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
//                }
//            }
//        });
//
//        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
//            @Override
//            public void onChanged(@Nullable LoginResult loginResult) {
//                if (loginResult == null) {
//                    return;
//                }
//                loadingProgressBar.setVisibility(View.GONE);
//                if (loginResult.getError() != null) {
//                    showLoginFailed(loginResult.getError());
//                }
//                if (loginResult.getSuccess() != null) {
//                    updateUiWithUser(loginResult.getSuccess());
//                }
//                setResult(Activity.RESULT_OK);
//
//                //Complete and destroy login activity once successful
//                finish();
//            }
//        });

//        TextWatcher afterTextChangedListener = new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // ignore
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                // ignore
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
//            }
//        };
//        usernameEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.addTextChangedListener(afterTextChangedListener);
//        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    loginViewModel.login(usernameEditText.getText().toString(),
//                            passwordEditText.getText().toString());
//                }
//                return false;
//            }
//        });


//        signUppage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent;
//                intent=new Intent(getApplicationContext(),SignUpActivity.class);
//                startActivity(intent);
//            }
//        });

        signUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                register();
//                Intent i = new Intent(getApplicationContext(), Verification_Signup.class);
////                i.putExtra("EXTRA_SESSION_ID", data);
//                startActivity(i);
//                if(process.getStatus() == AsyncTask.Status.FINISHED){
//                    loadingProgressBar.setVisibility(View.GONE);
//                }
//
            }
        });

    }

    private void startMainActivity(){
        Intent i = new Intent(getApplicationContext(), home_baker.class);
        startActivity(i);
    }

//    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    public void register()
    {
        initialize();
        if(!validation())
        {
            Toast.makeText(this,"SignUp has failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            onSignUpSuccess();
        }

    }
    public void onSignUpSuccess()
    {
        try{

            // CALL GetText method to make post method call
//            GetText();



            JSONObject new_baker = new JSONObject();
            try {
                new_baker.put("first_name", fname);
                new_baker.put("last_name", lname);
                new_baker.put("password", pass);
                new_baker.put("email", email);
                new_baker.put("phone_number", phone);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            process.execute("http://testbysindhu.mobifinplus.com/signup_baker.php","api_response",new_baker.toString());

//            for(int i=0; i<500000; i++);
            Intent i = new Intent(getApplicationContext(), Verification_Signup.class);
            i.putExtra("code", code);
            startActivity(i);
        }
        catch(Exception ex)
        {
            Toast.makeText(this,"SignUp has failed",Toast.LENGTH_SHORT).show();
        }

    }


    public boolean validation()
    {
        boolean valid = true;
        if(fname.isEmpty()|| fname.length()>32)
        {
            signup_frst_name.setError("Please Enter Valid Name");
            valid=false;
        }
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signup_user_email.setError("Please Enter Valid email");
            valid=false;
        }
        if(pass.isEmpty())
        {
            signup_password.setError("Please Enter Password");
            valid=false;
        }
        if(cpass.isEmpty() || !cpass.equals(pass))
        {
            signup_confirm_passwd.setError("Please Enter Correct Password");
            valid=false;
        }

        return valid;
    }
    public void initialize()
    {
        fname=signup_frst_name.getText().toString();
        lname=signup_last_name.getText().toString();
        email=signup_user_email.getText().toString();
        pass=signup_password.getText().toString();
        cpass=signup_confirm_passwd.getText().toString();
        phone=signup_phone.getText().toString();
    }



    public  void  GetText()  throws UnsupportedEncodingException
    {
        // Get user defined values
        String text = "error";
        BufferedReader reader=null;

        // Create data variable for sent values to server

        String data = URLEncoder.encode("fname", "UTF-8")
                + "=" + URLEncoder.encode(fname, "UTF-8");
        signup_frst_name.setText( "257"  );
        data += "&" + URLEncoder.encode("lname", "UTF-8") + "="
                + URLEncoder.encode(lname, "UTF-8");

        data += "&" + URLEncoder.encode("email", "UTF-8") + "="
                + URLEncoder.encode(email, "UTF-8");

        data += "&" + URLEncoder.encode("cpass", "UTF-8")
                + "=" + URLEncoder.encode(cpass, "UTF-8");

        data += "&" + URLEncoder.encode("phone", "UTF-8")
                + "=" + URLEncoder.encode(phone, "UTF-8");
        signup_frst_name.setText( "269"  );


        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("http://testbysindhu.mobifinplus.com/testjson.php");
            signup_frst_name.setText( "278"  );
            // Send POST data request

            URLConnection conn = url.openConnection();
            signup_frst_name.setText( "282"  );
            conn.setDoOutput(true);
            signup_frst_name.setText( "284"  );
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            signup_frst_name.setText( "286"  );
            wr.write( data );
            signup_frst_name.setText( "287"  );
            wr.flush();
            signup_frst_name.setText( "290"  );

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            signup_frst_name.setText( "295"  );
            StringBuilder sb = new StringBuilder();
            signup_frst_name.setText( "297"  );
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
                signup_frst_name.setText( "305"  );
            }


            text = sb.toString();
            signup_frst_name.setText( "310"  );
        }
        catch(Exception ex)
        {
            //signup_frst_name.setText( "314"  );
        }
        finally
        {
            try
            {

                reader.close();
                // signup_frst_name.setText( "322" );
            }

            catch(Exception ex) {
                //signup_frst_name.setText( "326"  );
            }
        }

        // Show response on activity
        //signup_frst_name.setText( text  );

    }

}





