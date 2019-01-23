package com.aasish.storelistfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference rData;
    private EditText mAddItem,mAddItem2,mAddItem3,mAddItem4,mAddItem5;
    private Button mAdd;
    private String[] keys = AccountActivity.keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAddItem = (EditText) findViewById(R.id.AddItem);
        mAddItem2 = (EditText)findViewById(R.id.AddItem2);
        mAddItem3 = (EditText)findViewById(R.id.AddItem3);
        mAddItem4 = (EditText)findViewById(R.id.AddItem4);
        mAddItem5 = (EditText)findViewById(R.id.AddItem5);
        mAdd = (Button) findViewById(R.id.Add);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mAddItem.getText().toString())&&TextUtils.isEmpty(mAddItem2.getText().toString())&&TextUtils.isEmpty(mAddItem3.getText().toString())&&TextUtils.isEmpty(mAddItem4.getText().toString())&&TextUtils.isEmpty(mAddItem5.getText().toString())) {

                    Toast.makeText(AddItemActivity.this, "All fields are empty. Fill in at least one field", Toast.LENGTH_LONG).show();
                }
                else {
                    db.collection(mAuth.getCurrentUser().getEmail()).document(AccountActivity.ListName.getText().toString())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    //Toast.makeText(AccountActivity.this, "Got here too", Toast.LENGTH_SHORT).show();
                                    if (documentSnapshot.exists()) {
                                        Map<String,Object> newMap = documentSnapshot.getData();
                                        if(!TextUtils.isEmpty(mAddItem.getText().toString()))
                                            newMap.put(mAddItem.getText().toString(),"");
                                        if(!TextUtils.isEmpty(mAddItem2.getText().toString()))
                                            newMap.put(mAddItem2.getText().toString(),"");
                                        if(!TextUtils.isEmpty(mAddItem3.getText().toString()))
                                            newMap.put(mAddItem3.getText().toString(),"");
                                        if(!TextUtils.isEmpty(mAddItem4.getText().toString()))
                                            newMap.put(mAddItem4.getText().toString(),"");
                                        if(!TextUtils.isEmpty(mAddItem5.getText().toString()))
                                            newMap.put(mAddItem5.getText().toString(),"");
                                        db.collection(mAuth.getCurrentUser().getEmail()).document(AccountActivity.ListName.getText().toString()).set(newMap);
                                        Toast.makeText(AddItemActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddItemActivity.this, ListsActivity.class));
                                    }
                                }
                            });
                }
            }
        });
    }
}
