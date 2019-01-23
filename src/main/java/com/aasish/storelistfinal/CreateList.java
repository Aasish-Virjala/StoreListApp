package com.aasish.storelistfinal;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import junit.framework.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateList extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button mCreateNewList;
    private FirebaseFirestore db;
    private EditText mNewList, mFirstItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        db  = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mNewList = (EditText) findViewById(R.id.NewListName);
        mCreateNewList = (Button) findViewById(R.id.CreateList);
        mFirstItem = (EditText) findViewById(R.id.FirstItem);
        mCreateNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection(mAuth.getCurrentUser().getEmail()).document(mNewList.getText().toString())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                //Toast.makeText(AccountActivity.this, "Got here too", Toast.LENGTH_SHORT).show();
                                if (documentSnapshot.exists()) {
                                    Toast.makeText(CreateList.this, "List already exists.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if(TextUtils.isEmpty(mFirstItem.getText().toString()))
                                        Toast.makeText(CreateList.this, "First Item cannot be empty", Toast.LENGTH_SHORT).show();
                                    else {
                                        Map<String, Object> New = new HashMap<>();
                                        New.put(mFirstItem.getText().toString(), "");
                                        db.collection(mAuth.getCurrentUser().getEmail()).document(mNewList.getText().toString()).set(New);
                                        AccountActivity.ListName = mNewList;
                                        startActivity(new Intent(CreateList.this, ListsActivity.class));
                                    }
                                }
                            }
                        });
            }
        });
    }
}
