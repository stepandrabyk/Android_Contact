package com.hfad.myapplication;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SharedPreferencesHelper {
    public static final String SHARED_PREF_NAME = "SHARED_PREF_NAME";
    public static final String CONTACTS_KEY = "CONTACTS_KEY";
    public static final Type USERS_TYPE = new TypeToken<List<Contact>>(){}.getType();
    private SharedPreferences mSharedPreferences;
    private Gson mGson = new Gson();

    public SharedPreferencesHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Log.i(TAG, "SharedPreferencesHelper: ");
    }
    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> users = mGson.fromJson(mSharedPreferences.getString(CONTACTS_KEY, ""), USERS_TYPE);
        return users == null ? new ArrayList<Contact>() : users;
    }
    public boolean addContact(Contact contact) {
        ArrayList<Contact> contacts = getContacts();

        contacts.add(contact);
        mSharedPreferences.edit().putString(CONTACTS_KEY, mGson.toJson(contacts, USERS_TYPE)).apply();
        ArrayList<Contact> contacts1 = getContacts();
        return true;
    }
    public boolean editContect(Contact contact, int position){
        ArrayList<Contact> contacts = getContacts();

        contacts.set(position,contact);
        mSharedPreferences.edit().putString(CONTACTS_KEY, mGson.toJson(contacts, USERS_TYPE)).apply();
        return true;
    }
    public boolean clearContacts(){
        ArrayList<Contact> contacts = initData();
        mSharedPreferences.edit().putString(CONTACTS_KEY, mGson.toJson(contacts, USERS_TYPE)).apply();
        return true;
    }
    public boolean removeContacts(int position){
        ArrayList<Contact> contacts = getContacts();
        contacts.remove(position);
        mSharedPreferences.edit().putString(CONTACTS_KEY, mGson.toJson(contacts, USERS_TYPE)).apply();
        return true;
    }
    private ArrayList<Contact> initData(){
        ArrayList<Contact> cont = new ArrayList<>();
        cont.add(new Contact("1","2","a@a.a"));
        cont.add(new Contact("3","2","a@a.a"));
        cont.add(new Contact("1","4","a@a.a"));
        for (Contact c:cont
        ) {
            addContact(c);
        }

        return cont;
    }



}
