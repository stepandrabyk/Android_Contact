package com.hfad.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class AddActivity extends AppCompatActivity {
    private static final String AGR_DATE="add_contact";
    private static final int PICK_IMAGE=1;
    EditText textName,textLastName,textEmail;
    ImageView image;
    Button btnAdd;
    Uri mUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        textName=findViewById(R.id.edit_name);
        textLastName=findViewById(R.id.edit_lastname);
        textEmail=findViewById(R.id.edit_email);
        image=findViewById(R.id.image_add);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contact contact = new Contact(textName.getText().toString(),textLastName.getText().toString(),textEmail.getText().toString());
                if(mUri!=null)
                    contact.setBitmap(mUri.toString());
                Bundle args = new Bundle();
                Intent  intent =new Intent();

                intent.putExtra(AGR_DATE,contact);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galery = new Intent();
                galery.setType("image/*");
                galery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galery,"Select Photo"),PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK)
        {
            mUri = data.getData();
            image.setImageURI(mUri);
        }
    }
}
