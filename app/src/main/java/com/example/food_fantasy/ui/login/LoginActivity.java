package com.example.food_fantasy.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.example.food_fantasy.ui.signup.SignUpActivity;
import com.example.food_fantasy.ApiHit;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.food_fantasy.splash.pref;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Handler mHandler = new Handler();
    private String email, pass;
    private EditText usernameEditText,passwordEditText;
    private ApiHit process = new ApiHit();
    private ApiHit process_for_getalldishes = new ApiHit();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);
        final TextView signUppage = findViewById(R.id.move_to_signup);
         usernameEditText = findViewById(R.id.username);
         passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });


        signUppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent=new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                register();
            }
        });


    }

    private void startMainActivity(){
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);


        String response;
        do{
            response = pref.getString("api_response", null);
        }while (response == null);

        try{
            JSONObject json = new JSONObject(response);

            response = json.getString("status");
            if(response.equals("success")) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("baker_name", json.getString("name"));
                editor.putString("baker_email", json.getString("email"));
                editor.putString("baker_id", json.getString("baker_id"));
                editor.apply();

                getAllDishes();

                loadingProgressBar.setVisibility(View.GONE);
                Intent intent;
                intent = new Intent(getApplicationContext(), home_baker.class);
                startActivity(intent);
            }
            else{
                loadingProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Invalid email or password entered!", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void register()
    {
        initialize();
        if(!validation())
        {
            Toast.makeText(this,"Login has failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            onLoginSuccess();
        }

    }
    public void initialize()
    {
        email=usernameEditText.getText().toString();
        pass= passwordEditText.getText().toString();
    }

    public boolean validation()
    {
        boolean valid = true;

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            usernameEditText.setError("Please Enter Valid email");
            valid=false;
        }
        if(pass.isEmpty())
        {
            passwordEditText.setError("Please Enter Password");
            valid=false;
        }

        return valid;
    }

    public void onLoginSuccess()
    {
        try{

            JSONObject new_baker = new JSONObject();
            try {
                new_baker.put("password", pass);
                new_baker.put("email", email);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            process.execute("http://testbysindhu.mobifinplus.com/login_baker.php","api_response",new_baker.toString());


//                    Intent i = new Intent(getApplicationContext(), Verification_Signup.class);
//                    i.putExtra("code", code);
//                    startActivity(i);
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Login has failed",Toast.LENGTH_SHORT).show();
        }

//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());

        mHandler.postDelayed(new Runnable() {
            public void run() {
                startMainActivity();
            }
        }, 3000);

    }

    public  void getAllDishes(){
        try{

            JSONObject new_dish = new JSONObject();
            try {
                new_dish.put("baker_id", pref.getString("baker_id", null));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            process_for_getalldishes.execute("http://testbysindhu.mobifinplus.com/get_dishes_baker.php","all_dishes",new_dish.toString());
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong. please restart app",Toast.LENGTH_SHORT).show();
        }
    }

}
