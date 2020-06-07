package com.hfad.myapplication;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

public class Contact  implements Serializable {
    public String name;
    public String lastName;
    public String email;
    public String mUri;

    public String getBitmap() {
        return mUri;
    }

    public void setBitmap(String bitmap) {
        mUri = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email=email;
    }

    public Contact(String name, String lastName, String email, String bitmap) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        mUri = bitmap;
    }

    public Contact() {
    }
}
