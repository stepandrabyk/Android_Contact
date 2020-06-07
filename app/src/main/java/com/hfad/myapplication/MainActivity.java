package com.hfad.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ContactListFragment();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();


    }


    private ArrayList<Contact> initData(){
        ArrayList<Contact> cont = new ArrayList<Contact>();
        cont.add(new Contact("1","2","a@a.a"));
        cont.add(new Contact("3","2","a@a.a"));
        cont.add(new Contact("1","4","a@a.a"));
        cont.add(new Contact("5","2","a@a.a"));
        cont.add(new Contact("1","6","a@a.a"));
        return cont;
    }

}
