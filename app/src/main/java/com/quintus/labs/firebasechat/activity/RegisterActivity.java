package com.quintus.labs.firebasechat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.quintus.labs.firebasechat.R;
import com.quintus.labs.firebasechat.model.User;

public class RegisterActivity extends AppCompatActivity {

    String _name, _email, _phone, _password;
    TextView cont;
    FirebaseAuth mAuth;
    private EditText editTextName, editTextEmail, editTextPassword, editTextPhone;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Hanken-Book.ttf");

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Registering....");

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.pass);
        editTextPhone = findViewById(R.id.mob);
        mAuth = FirebaseAuth.getInstance();

        editTextName.setTypeface(custom_font);
        editTextEmail.setTypeface(custom_font);
        editTextPhone.setTypeface(custom_font);
        editTextPassword.setTypeface(custom_font);

        cont = findViewById(R.id.cont);
        cont.setTypeface(custom_font);
    }

    public void onContinueClicked(View view) {

        _name = editTextName.getText().toString().trim();
        _email = editTextEmail.getText().toString().trim();
        _password = editTextPassword.getText().toString().trim();
        _phone = editTextPhone.getText().toString().trim();

        if (_name.isEmpty()) {
            editTextName.setError(getString(R.string.input_error_name));
            editTextName.requestFocus();
            return;
        } else if (_email.isEmpty()) {
            editTextEmail.setError(getString(R.string.input_error_email));
            editTextEmail.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            editTextEmail.setError(getString(R.string.input_error_email_invalid));
            editTextEmail.requestFocus();
            return;
        } else if (_password.isEmpty()) {
            editTextPassword.setError(getString(R.string.input_error_password));
            editTextPassword.requestFocus();
            return;
        }

        if (_password.length() < 6) {
            editTextPassword.setError(getString(R.string.input_error_password_length));
            editTextPassword.requestFocus();
            return;
        } else if (_phone.isEmpty()) {
            editTextPhone.setError(getString(R.string.input_error_phone));
            editTextPhone.requestFocus();
            return;
        } else if (_phone.length() != 10) {
            editTextPhone.setError(getString(R.string.input_error_phone_invalid));
            editTextPhone.requestFocus();
            return;
        } else {
            registerUser();
        }


    }

    private void registerUser() {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User user = new User(
                                    _name,
                                    _email,
                                    _phone,
                                    "http://www.uccs.edu/recwellness/sites/recwellness/files/2018-09/download.jpeg",
                                    "user"
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                        finish();
                                        Toast.makeText(RegisterActivity.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                    } else {
                                        //display a failure message
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void onSignInClicked(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}
