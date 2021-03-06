package com.whitemediapro.jelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Facebook extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    LoginButton loginButton;
    CallbackManager callbackManager;
    ProgressDialog mDialog;
    private static final String EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user==null){
            setContentView(R.layout.activity_facebook);

            FacebookSdk.sdkInitialize(getApplicationContext());
            loginButton = findViewById(R.id.login_button);
            callbackManager = CallbackManager.Factory.create();
            loginButton.setReadPermissions(Arrays.asList(EMAIL));

        }else{
            Intent intent = new Intent(Facebook.this, LoginActivity.class);
            startActivity(intent);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FacebookButtClick();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void FacebookButtClick(){

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(Facebook.this, "User Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), exception.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser myuser = auth.getCurrentUser();
                    startActivity(new Intent(Facebook.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Could not register user", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}
