package com.savun.user.savunorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by user on 2017/1/19.
 */

public class longin extends AppCompatActivity {
    DatabaseReference firebase_client = FirebaseDatabase.getInstance().getReference("AllClient");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.longin);
        final EditText edId = (EditText)findViewById(R.id.edId);
        final EditText edpassword = (EditText)findViewById(R.id.edPw);
        final Button btlongin = (Button)findViewById(R.id.btlongin);


        btlongin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebase_client.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            AllClient allClient = ds.getValue(AllClient.class);
                            if (allClient.clientID.equals(edId.getText().toString()) && allClient.clientPassword.equals(edpassword.getText().toString())){
                                Intent intent = new Intent();
                                intent.setClass(longin.this, MainActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("clientID",edId.getText().toString());
                                intent.putExtras(bundle);
                                startActivity(intent);
                                System.exit(0);
                            }
                        }
                        Toast.makeText(getApplicationContext(), "帳號或密碼錯誤!", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
