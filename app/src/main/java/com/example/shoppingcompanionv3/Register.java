package com.example.shoppingcompanionv3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity
{
    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.createBtn);
        mLoginBtn = findViewById(R.id.loginText);

        fAuth = FirebaseAuth.getInstance();

        /*if(fAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }*/

        mRegisterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String username = mFullName.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return;
                }

                if(TextUtils.isEmpty(username))
                {
                    mFullName.setError("Username is required");
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

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            String UserID = fAuth.getCurrentUser().getUid();
                            Toast.makeText(Register.this, "ID: " + UserID, Toast.LENGTH_SHORT).show();

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
                        else
                        {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

<<<<<<< HEAD
                mLoginBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                    }
                });

                mBackBtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), All_Folder_Screen.class));
                    }
                });
=======
        mLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), Login.class));
>>>>>>> parent of 9b45834 (Merge branch 'Dylan's-Login' into McBrad's-Camera)
            }
        });
    }
}