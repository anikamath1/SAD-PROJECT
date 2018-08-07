package com.example.user.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonLogout;
    private TextView textViewUserEmail;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private EditText editTextName,editTextAddress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));

        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextAddress=(EditText)findViewById(R.id.editTextAddress);
        editTextName=(EditText)findViewById(R.id.editTextName);
        buttonSave = (Button)findViewById(R.id.buttonSave);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        textViewUserEmail.setText("Welcome  " + user.getEmail());
        buttonLogout.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }


    private void saveUserInformation()
    {
        String name=editTextName.getText().toString().trim();
        String address=editTextAddress.getText().toString().trim();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        UserInformation userInformation=new UserInformation(name,address);
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(getApplicationContext(),"INformation saved....",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        }

            if(view == buttonSave)
            {
                saveUserInformation();
            }
    }
}
