package com.whitemediapro.jelp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.FacebookActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    public ImageView mPhone_login, mfcbk_login,mEmail_login,mLoginBtn,mRegBtn;
    LinearLayout mlinearLayout; private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase database;
    DatabaseReference reference;
    CoordinatorLayout mCoordinator;
    ProgressBar mProgressbar;
    EditText mEmail_user, password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        mAuth = FirebaseAuth.getInstance();


        mEmail_login = findViewById(R.id.mail_login);
        mfcbk_login = findViewById(R.id.fcbk_login);
        mLoginBtn = findViewById(R.id.login_btn);
        mProgressbar = findViewById(R.id.progressbar1);
        mRegBtn = findViewById(R.id.reg_btn);
        mPhone_login = findViewById(R.id.phone_lgn);
        mEmail_user = findViewById(R.id.Edlogin_username);
        password1 = findViewById(R.id.Edlogin_pass1);
        mCoordinator = findViewById(R.id.myCoordinatorLayout);
        mlinearLayout = findViewById(R.id.login_frame);


        mEmail_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlinearLayout.setVisibility(View.VISIBLE);

            }
        });

        password1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (password1.getText().toString().length() < 6)
                {
                    password1.setError("Password should be of atleast 6 characters");
                }
                else
                {
                    password1.setError(null);
                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null)
                {

                    Intent in=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }

            }
        };




        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail_user.getText().toString().trim();
                final String password = password1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressbar.setVisibility(View.VISIBLE);
                mAuth = FirebaseAuth.getInstance();

                //create user

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Snackbar snackbar = Snackbar
                                            .make(mCoordinator, "Registration Successful!!", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent h = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(h);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail_user.getText().toString().trim();
                final String password = password1.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mProgressbar.setVisibility(View.VISIBLE);
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(email, password)

                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {

                                    Toast.makeText(LoginActivity.this, "Login Error" + task.getException(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });



            }
        });

        mPhone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(LoginActivity.this, PhoneLogin.class);
                startActivity(h);

            }
        });

        mfcbk_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent h = new Intent(LoginActivity.this, Facebook.class);
                startActivity(h);
            }
        });


    }

    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
