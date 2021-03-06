package com.whitemediapro.jelp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageException;

import java.util.Arrays;

public class PhoneLogin extends AppCompatActivity {
    private final int REQUEST_LOGIN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_login);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            }else{
                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().
                        setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).
                                build())).build(),REQUEST_LOGIN);
            }
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_LOGIN){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            //Success

            if(resultCode == RESULT_OK){
                if(!FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().isEmpty()){
                    startActivity(new Intent(this,MainActivity.class));
                    finish();
                    return;
                }else{
                    if(response == null) {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                        Toast.makeText(this, "NO NETWORK", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                        Toast.makeText(this, "UNKNOWN ERROR", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(this, "UNKNOWN SIGNIN ERROR!!!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
