package com.aasish.storelistfinal;

import android.content.Intent;
import android.nfc.Tag;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import junit.framework.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class AccountActivity extends AppCompatActivity  {
    private FirebaseAuth mAuth;
    private Button mLogoutButton, mSearch, mCreateNewList;
    private FirebaseFirestore db;
    static EditText ListName;
    private String mEmailField2 = MainActivity.mEmailField2;
    private EditText mEmailField = MainActivity.mEmailField;
    private String ValuesFromMain = MainActivity.Values, TestValue;
    private Map Hello;
    private String[] KeyValues;
    private Integer Values;
    static String[] keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        ListName = (EditText) findViewById(R.id.SearchList);
        mSearch = (Button) findViewById(R.id.SearchButton);
        mLogoutButton = (Button) findViewById(R.id.LogoutBtn);
        mCreateNewList = (Button) findViewById(R.id.CreateList);
        @NonNull
        String username = mAuth.getCurrentUser().getEmail();
        Toast.makeText(this, "Signed in as: " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            @NonNull
            public void onClick(View v) {
                //Toast.makeText(AccountActivity.this, "Got here", Toast.LENGTH_SHORT).show();
                db.collection(mAuth.getCurrentUser().getEmail()).document(ListName.getText().toString())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                //Toast.makeText(AccountActivity.this, "Got here too", Toast.LENGTH_SHORT).show();
                                if (documentSnapshot.exists()) {
                                    int i = 1;
                                    for(String key : documentSnapshot.getData().keySet())
                                        i++;
                                    keys = new String[i];
                                    int j = 0;
                                    for(String key : documentSnapshot.getData().keySet()) {
                                        keys[j] = key;
                                        j++;
                                    }
                                    startActivity(new Intent(AccountActivity.this, ListsActivity.class));
                                } else {
                                    Toast.makeText(AccountActivity.this, "List does not exist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AccountActivity.this, "Error in finding List", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });
        mCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, CreateList.class));
            }
        });

    }
}