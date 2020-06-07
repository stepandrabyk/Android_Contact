package com.hfad.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class EditActivity extends AppCompatActivity {
    private static final String AGR_DATE="date";
    EditText nameEditText,lastNameEditText,emailEditText;
    ImageView mImageView;
    Button mSaveButton,mDeleteButton;
    Contact curContact;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        nameEditText = findViewById(R.id.edit_name);
        lastNameEditText = findViewById(R.id.edit_lastname);
        emailEditText = findViewById(R.id.edit_email);
        mSaveButton=findViewById(R.id.btn_save);


        ActionBar actionBar =getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        Bundle args = intent.getExtras();
        if(args!=null){
            curContact = (Contact)args.getSerializable(AGR_DATE);
            nameEditText.setText(curContact.name);
            lastNameEditText.setText(curContact.lastName);
            emailEditText.setText(curContact.email);
            if(curContact.mUri!=null) {
                Uri uri = Uri.parse(curContact.mUri);
                mImageView = findViewById(R.id.image_edit);
                mImageView.setImageURI(uri);
                Log.i("A","+++++++");
            }
        }
        else {curContact = new Contact();}
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curContact.setName(nameEditText.getText().toString());
                curContact.setLastName(lastNameEditText.getText().toString());
                curContact.setEmail(emailEditText.getText().toString());
                Bundle args = new Bundle();
                args.putSerializable(AGR_DATE,curContact);
                Intent intent = new Intent();
                intent.putExtra(AGR_DATE,curContact);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
