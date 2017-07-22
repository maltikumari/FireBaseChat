package gita.firebasechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Malti on 19-Jul-17.
 */

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
// 1. declearing all views
private EditText editTextEmail,editTextPassword;
private Button buttonSignUp,buttonReSetPassword,buttonSignIn;

//6. ProgressDialog
private ProgressDialog progressDialog;

//9. declare FirebaseAuth
private FirebaseAuth firebaseAuth;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //10. intialise
        firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
        //start profile activity
        finish();
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }
        // 2 intialising all views
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        buttonSignUp= (Button) findViewById(R.id.buttonSignup);

        buttonReSetPassword= (Button) findViewById(R.id.btn_reset_password);
        buttonSignIn= (Button) findViewById(R.id.sign_in_button);


        // 7. intialise ProgressDialog
        progressDialog= new ProgressDialog(this);

        // 3 setting on click listener on buttons
        buttonSignUp.setOnClickListener(this);
        buttonReSetPassword.setOnClickListener(this);
        buttonSignIn.setOnClickListener(this);

        }

private void registerUser() {
        String email= editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
        // email is empty
        Toast.makeText(this,"Please enter email", Toast.LENGTH_LONG).show();
        //stop the function exceuting further
        return;
        }
        if (TextUtils.isEmpty(password)){
        // password is empty
        Toast.makeText(this,"Please enter password", Toast.LENGTH_LONG).show();
        //stop the function exceuting further
        return;
        }
        if (password.length() < 6) {
        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
        return;
        }
        progressDialog.setMessage("Registering User..");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
          @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
             finish();
               Toast.makeText(SigninActivity.this," Registered .........", Toast.LENGTH_LONG).show();
             startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }else {
        Toast.makeText(SigninActivity.this,"Could not Registered .. Try again", Toast.LENGTH_LONG).show();
        }
        progressDialog.dismiss();

        }
        });
        }


//4.
@Override
public void onClick(View v) {
        if(v == buttonSignUp){
        registerUser();
        }
        if(v == buttonReSetPassword){
        finish();
        }
        if (v==buttonSignIn){
        startActivity(new Intent(this,MainActivity.class));
        }
        }
}
