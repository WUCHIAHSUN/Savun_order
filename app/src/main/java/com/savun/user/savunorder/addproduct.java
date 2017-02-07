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
 * Created by user on 2017/1/25.
 */

public class addproduct extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);
        final DatabaseReference firebase_client = FirebaseDatabase.getInstance().getReference("AllProduct");
        final EditText productName = (EditText)findViewById(R.id.edproductName);
        final EditText productID = (EditText)findViewById(R.id.edproductID);
        final EditText productretailPrice = (EditText)findViewById(R.id.edproductretailPrice);
        final EditText productsalonPrice = (EditText)findViewById(R.id.edproductsalonPrice);

        Button btsent = (Button)findViewById(R.id.btsent);

        btsent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(productID.getText().toString()) || "".equals(productName.getText().toString()) || "".equals(productretailPrice.getText().toString())
                        || "".equals(productsalonPrice.getText())){
                    Toast.makeText(getApplicationContext(), "資料不可為空", Toast.LENGTH_SHORT).show();
                }
                else{
                    AllProduct allproduct = new AllProduct(productID.getText().toString(), productName.getText().toString(),
                           productretailPrice.getText().toString(), productsalonPrice.getText().toString(), "0");
                    firebase_client.push().setValue(allproduct);
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
