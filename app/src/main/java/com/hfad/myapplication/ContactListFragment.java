package com.hfad.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactListFragment extends Fragment {
    private static final String AGR_EDIT="date";
    private RecyclerView mRecyclerView;
    private Contact mContact;
    private Button mButtonAdd, mButtonClear;
    private int mPosition;
    private  static final int REQUEST_CODE_EDIT = 2;
    private  static final int REQUEST_CODE_ADD = 1;
    private Adapter mAdapter;
    private static final String AGR_ADD="add_contact";
    private SharedPreferencesHelper mSharedPreferencesHelper;
    //после додавания
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){
            return;
        }else {
            switch (requestCode) {
                case REQUEST_CODE_ADD: {
                    Bundle args = data.getExtras();
                    Contact contact = (Contact) args.getSerializable(AGR_ADD);
                    mAdapter.addContact(contact);
                    break;
                }
                case REQUEST_CODE_EDIT: {
                    Bundle args = data.getExtras();
                    Contact c = (Contact) args.getSerializable(AGR_EDIT);
                    mAdapter.updateContact(c);
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mButtonAdd = view.findViewById(R.id.btn_add);
        mButtonClear = view.findViewById(R.id.btn_clear);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddActivity.class);
                startActivityForResult(intent,1);
            }
        });
        mSharedPreferencesHelper = new SharedPreferencesHelper(getActivity());
        mButtonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSharedPreferencesHelper.clearContacts();
                mAdapter.clearContacts();
                updateUI();
            }
        });
        updateUI();
        return view;
    }
    private void updateUI() {
        List<Contact> contacts=mSharedPreferencesHelper.getContacts();
        if(mAdapter == null){
            mAdapter = new Adapter(contacts);
            mRecyclerView.setAdapter(mAdapter);
        }
        else {mAdapter.notifyDataSetChanged();}
    }

    //вособновение
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }





    private class  ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTextView;
        private TextView mLastNameTextView;
        private ImageView mImageView;
        private Button mButtonDelete;
        public ContactHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.activity_list_item,parent,false));
            mNameTextView = (TextView) itemView.findViewById(R.id.textName);
            mLastNameTextView = (TextView) itemView.findViewById(R.id.textLastName);
            mImageView = (ImageView) itemView.findViewById(R.id.image_list_item);
            itemView.setOnClickListener(this);
            mButtonDelete = itemView.findViewById(R.id.btn_delete);
            mButtonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mAdapter.removeContact(getAbsoluteAdapterPosition());
                    mSharedPreferencesHelper.removeContacts(getAbsoluteAdapterPosition());
                    updateUI();
                }
            });
        }
        public void bind(Contact contact){
            mContact=contact;
            mNameTextView.setText(mContact.name);
            mLastNameTextView.setText(mContact.lastName);
            mImageView.setImageResource(R.mipmap.ic_launcher);
            if(mContact.mUri!=null
            ){
                Uri uri = Uri.parse(mContact.mUri);
                mImageView.setImageURI(uri);


            }
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            mPosition=position;
            Contact contact = mAdapter.getContact(position);
            Bundle args = new Bundle();
            args.putSerializable(AGR_EDIT,contact);
            Intent intent = new Intent(getContext(), EditActivity.class);
            intent.putExtra(AGR_EDIT,contact);
            Toast.makeText(getActivity(),"clocked"+ Integer.toString( position),Toast.LENGTH_LONG).show();
            startActivityForResult(intent,2);
        }
    }





    public class Adapter extends RecyclerView.Adapter<ContactHolder>{
        private List<Contact> mContacts;
        public Adapter (List<Contact> contacts)
        {
            mContacts=contacts;
        }
        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater =LayoutInflater.from(getActivity());
            return new ContactHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bind(contact);
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }
        public Contact getContact(int positon){ return mContacts.get(positon);}

        public void addContact(Contact v){
            mContacts.add(v);
            mSharedPreferencesHelper.addContact(v);
        }
        public void updateContact(Contact v){
            mContacts.set(mPosition,v);
            mSharedPreferencesHelper.editContect(v,mPosition);
        }
        public void removeContact(int position){
            mContacts.remove(position);
        }
        public void clearContacts(){
            mContacts = mSharedPreferencesHelper.getContacts();
        };
    }
}
