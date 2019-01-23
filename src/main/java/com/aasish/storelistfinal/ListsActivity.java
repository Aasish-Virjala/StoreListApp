package com.aasish.storelistfinal;


import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class ListsActivity extends AppCompatActivity {
    private String[] keys;
    private TextView mStoreList;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Button mAddItem,mBack,mDeleteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);
        //set db to the instance of Firebase Firestore
        db = FirebaseFirestore.getInstance();
        //set mAuth to the instance of Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        //set the button variables to their correspoding buttons
        mBack = (Button) findViewById(R.id.Back);
        mAddItem = (Button) findViewById(R.id.addItem);
        NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.ScrollView);
        mStoreList = (TextView) findViewById(R.id.StoreList);
        mDeleteList = (Button)findViewById(R.id.DeleteList);
        db.collection(mAuth.getCurrentUser().getEmail()).document(AccountActivity.ListName.getText().toString())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
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
                        }
                        String data = "";
                        for(int i = 0; i<keys.length-1;i++) {
                            if(keys[i].length()>17)
                                data+=keys[i].substring(0,17)+"\n" + keys[i].substring(17)+"\n"+"\n";
                            else
                                data += keys[i] + "\n" + "\n";
                        }
                        mStoreList.setText(data);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListsActivity.this, "Error in finding List", Toast.LENGTH_SHORT).show();
                    }
                });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListsActivity.this, AccountActivity.class));
            }
        });
        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListsActivity.this,AddItemActivity.class));
            }
        });
        mDeleteList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection(mAuth.getCurrentUser().getEmail()).document(AccountActivity.ListName.getText().toString()).delete();
                Toast.makeText(ListsActivity.this, "List deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ListsActivity.this,AccountActivity.class));
            }
        });
    }
}
