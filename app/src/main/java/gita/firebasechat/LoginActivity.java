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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Malti on 19-Jul-17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    // 1. declearing all views
    private EditText editTextEmail,editTextPassword;
    private Button buttonSignIn;
    private TextView textViewSignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth= FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null){
            //start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        // 2 intialising all views
        editTextEmail= (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn= (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp= (TextView) findViewById(R.id.textViewSignUp);
        progressDialog=new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }
    private void userLogin() {
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

        //5. if validation is ok
        //8. we will first show the progressbar
        progressDialog.setMessage("Login User Connecting ...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){

                            //start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
else
    Toast.makeText(getApplicationContext(),"task not execute",Toast.LENGTH_LONG).show();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        if(v == buttonSignIn){
            userLogin();
        }
        if (v==textViewSignUp){
            finish();
            startActivity(new Intent(this,SigninActivity.class));
        }
    }
}
