package com.example.food_fantasy.ui.dishes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.food_fantasy.ApiHit;
import com.example.food_fantasy.R;
import com.example.food_fantasy.home_baker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import static com.example.food_fantasy.splash.pref;

public class Add_dishes extends AppCompatActivity {

    ImageView dishImg;
    Button UpldBtnn,dishAdd;
    EditText title, des, price;
    private ApiHit process = new ApiHit();
    private ApiHit process_for_getalldishes = new ApiHit();
    ProgressBar loader;

    private static final int image_pic_code=1000;
    private static final int permission_code=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);

        loader = findViewById(R.id.loader_add_dish);

        title = findViewById(R.id.title_of_dish);
        des = findViewById(R.id.dish_description_text);
        price = findViewById(R.id.dish_price_text);

        dishImg =findViewById(R.id.img_dish);
        UpldBtnn=findViewById(R.id.upload_dishe_pic);
        dishAdd=findViewById(R.id.dish_add);

        UpldBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,permission_code);
                    }
                    else {

                        pickImageFromGallery();

                    }
                }
                else {

                    pickImageFromGallery();
                }

            }
        });

        dishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader.setVisibility(View.VISIBLE);

                try{

                    JSONObject new_dish = new JSONObject();
                    try {
                        new_dish.put("baker_id", pref.getString("baker_id", null));
                        new_dish.put("title", title.getText().toString());
                        new_dish.put("description", des.getText().toString());
                        new_dish.put("price", price.getText().toString());

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    process.execute("http://testbysindhu.mobifinplus.com/add_dish_baker.php","api_response",new_dish.toString());
                    getAllDishes();

//                    Intent i = new Intent(getApplicationContext(), Verification_Signup.class);
//                    i.putExtra("code", code);
//                    startActivity(i);
                }
                catch(Exception ex)
                {
                    Toast.makeText(getApplicationContext(),"Something went wrong. please restart app",Toast.LENGTH_SHORT).show();
                }

                String response;
                do{
                    response = pref.getString("api_response", null);
                }while (response == null);


                try{
                    JSONObject json = new JSONObject(response);

                    response = json.getString("status");
                    if(response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Dish Added Successfully!", Toast.LENGTH_SHORT).show();
                        loader.setVisibility(View.GONE);
                        Intent intent;
                        intent = new Intent(getApplicationContext(), home_baker.class);
                        startActivity(intent);
                    }
                    else{
                        loader.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Something went wrong from server!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                Toast.makeText(getBaseContext(),"Dish Added",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImageFromGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,image_pic_code);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case permission_code:
            {
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode== RESULT_OK)
        {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                dishImg.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

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
