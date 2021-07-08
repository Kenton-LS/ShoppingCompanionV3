package com.example.shoppingcompanionv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity
{
    EditText mEmail, mPassword;
    ImageView mLoginBtn;
    ImageView mBackBtn;
    TextView mCreateBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBackBtn = findViewById(R.id.img_back);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = (ImageView) findViewById(R.id.img_continueLogin);
        mCreateBtn = (TextView) findViewById(R.id.txtCreate);

        mLoginBtn.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }

                if(password.length() < 6)
                {
                    mPassword.setError("Password must be at least 6 characters long");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Login();
                        }
                        else
                        {
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mCreateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN SCREEN","onClick for view change to Register");
                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });

    }

    public void Login(){
        Toast.makeText(Login.this, "User Logged in.", Toast.LENGTH_SHORT).show();
        String UserID = fAuth.getCurrentUser().getUid();
        Toast.makeText(Login.this, "ID: " + UserID, Toast.LENGTH_SHORT).show();

        Handler handler = new Handler(); // Delay for 0.5 seconds
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                Intent i = new Intent(getApplicationContext(), All_Folder_Screen.class);
                i.putExtra("UserFirebaseID", UserID); // Send through the User's ID to the MainActivity
                startActivity(i);
            }
        }, 500); // End delay
    }

}