package com.savun.user.savunorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by user on 2017/1/23.
 */

public class addclient extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_client);
        final DatabaseReference firebase_client = FirebaseDatabase.getInstance().getReference("AllClient");
        final EditText address = (EditText)findViewById(R.id.edaddress);
        final EditText clientID = (EditText)findViewById(R.id.edclientID);
        final EditText clientLv = (EditText)findViewById(R.id.edLv);
        final EditText clientName = (EditText)findViewById(R.id.edclientName);
        final EditText clientPassword = (EditText)findViewById(R.id.edPassword);
        final EditText tel = (EditText)findViewById(R.id.edPhone);
        Button btsend = (Button)findViewById(R.id.btSend);

        btsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(address.getText().toString()) || "".equals(clientID.getText().toString()) || "".equals(clientLv.getText().toString())
                        || "".equals(clientName.getText().toString()) || "".equals(clientPassword.getText().toString())
                        || "".equals(tel.getText().toString())){
                    Toast.makeText(getApplicationContext(), "資料不可為空", Toast.LENGTH_SHORT).show();
                }
                else{
                    AllClient allclient = new AllClient(address.getText().toString(), clientID.getText().toString(),
                            clientLv.getText().toString(), clientName.getText().toString(), clientPassword.getText().toString(), tel.getText().toString());
                    firebase_client.push().setValue(allclient);
                    Toast.makeText(getApplicationContext(), "新增成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.exit(0);
            return true;
        }
        return false;
    }
}
