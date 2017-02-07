package com.savun.user.savunorder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * Created by user on 2017/1/4.
 */

public class order_list extends AppCompatActivity {
    DatabaseReference firebase_custmer = FirebaseDatabase.getInstance().getReference("Orders");
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        String[] orderID;
        String[] client;
        String[] orderDate;
        String[] totalAmount;

        public MyAdapter(Context c, String[] orderID, String[] client, String[] orderDate, String[] totalAmount) {
            inflater = LayoutInflater.from(c);
            this.orderID = orderID;
            this.client = client;
            this.orderDate = orderDate;
            this.totalAmount = totalAmount;
        }

        @Override
        public int getCount() {
            return orderID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.list,viewGroup,false);
            TextView orderID2,client2,orderDate2,totalAmount2;

            orderID2 = (TextView) view.findViewById(R.id.keyname);
            client2 = (TextView) view.findViewById(R.id.custmer_name);
            orderDate2 = (TextView) view.findViewById(R.id.date);
            totalAmount2 = (TextView) view.findViewById(R.id.money);

            orderID2.setText(orderID[i]);
            client2.setText(client[i]);
            orderDate2.setText(orderDate[i]);
            totalAmount2.setText(totalAmount[i]);
            return view;
        }
    }
    ListView ls ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
        final AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
        ls = (ListView) findViewById(R.id.orderlist);
        ls.setDividerHeight(10);


        Bundle bundle = getIntent().getExtras();
        final int guess = bundle.getInt("listlong");
        final String clientID = bundle.getString("clientID");

        final String[] orderID= new String[guess];
        final String[] client = new String[guess];
        final String[] orderDate = new String[guess];
        final String[] totalAmount = new String[guess];
        final int[] j = {guess-1};
        

        firebase_custmer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Order order1 = ds.getValue(Order.class);
                    if (clientID.equals(order1.client)) {
                        orderID[j[0]] = order1.orderID;
                        client[j[0]] = order1.client;
                        orderDate[j[0]] = order1.orderDate;
                        totalAmount[j[0]] = order1.totalAmount;
                        j[0] = j[0] - 1;
                    }
                     if ("1023".equals(clientID)){
                        orderID[j[0]] = order1.orderID;
                        client[j[0]] = order1.client;
                        orderDate[j[0]] = order1.orderDate;
                        totalAmount[j[0]] = order1.totalAmount;
                        j[0] = j[0] - 1;
                    }
                }
                //j[]倒排Listview
                ls.setAdapter(new MyAdapter(order_list.this,orderID,client,orderDate,totalAmount));
                //registerForContextMenu(ls);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
        ls.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                MyAlertDialog.setTitle("訂貨單"+orderID[pos]);
                MyAlertDialog.setMessage("我是內容");
                MyAlertDialog.show();
                return true;
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