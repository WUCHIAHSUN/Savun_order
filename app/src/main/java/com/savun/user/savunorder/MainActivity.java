package com.savun.user.savunorder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    private int pagerWidth;
    private List<ImageView> imageViewList;
    cartDB mHelper = new cartDB(this);
    SQLiteDatabase mDB = null;
    private MyAdapter adapter;
    private ArrayList<String> tvpdName,tvCount,tvRice;
    private List<View> listviews;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager= (ViewPager) findViewById(R.id.viewPager);
        final TextView tvpdName2 = (TextView)findViewById(R.id.tvpdName2);
        imageViewList=new ArrayList<>();
        ImageView first=new ImageView(MainActivity.this);

        first.setImageResource(R.mipmap.img01);
        ImageView second=new ImageView(MainActivity.this);
        second.setImageResource(R.mipmap.img01);
        ImageView third=new ImageView(MainActivity.this);
        third.setImageResource(R.mipmap.img01);
        ImageView fourth=new ImageView(MainActivity.this);
        fourth.setImageResource(R.mipmap.img01);
        ImageView fifth=new ImageView(MainActivity.this);
        fifth.setImageResource(R.mipmap.img01);
        imageViewList.add(first);
        imageViewList.add(second);
        imageViewList.add(third);
        imageViewList.add(fourth);
        imageViewList.add(fifth);
        viewPager.setOffscreenPageLimit(3);
        pagerWidth= (int) (getResources().getDisplayMetrics().widthPixels*3.0f/5.0f);
        ViewGroup.LayoutParams lp=viewPager.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width=pagerWidth;
        }
        viewPager.setLayoutParams(lp);
        viewPager.setPageMargin(-50);
        findViewById(R.id.touch).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return viewPager.dispatchTouchEvent(motionEvent);
            }
        });
        viewPager.setPageTransformer(true,new MyTransformation());
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView=imageViewList.get(position);
                container.addView(imageView,position);
                return imageView;
            }
        });

        Bundle bundle = getIntent().getExtras();
        final String clientID = bundle.getString("clientID");

        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        final String orderID=sdf.format(new java.util.Date());
        SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd");
        final String orderDate=time.format(new java.util.Date());


        final String[] salonPrice = new String[100];
        final String[] Name = new String[100];
        final int[] custmerid = new int[1];
        final int[] bundlei = new int[1];
        final int[] Sprice = new int[1];
        final int[] pdcount = new int[1];
        tvpdName = new ArrayList<String>();
        tvCount = new ArrayList<String>();
        tvRice = new ArrayList<String>();
        String[] list = {"查看訂單","新增用戶","新增產品"};

        final TextView price = (TextView)findViewById(R.id.textView);
        TextView clientId = (TextView)findViewById(R.id.clientId);
        final Button btIncart = (Button)findViewById(R.id.btIncart);
        Button sendButton = (Button)findViewById(R.id.btSend);
        ListView leftlist = (ListView)findViewById(R.id.left_drawer);
        final EditText edcount = (EditText)findViewById(R.id.editText2);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        leftlist.setAdapter(listAdapter);



        clientId.setText(clientID+"你好!");
        final ArrayAdapter<String> product_count = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1);

//圖的ID
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                tvpdName2.setText(Name[position] + "");
                price.setText(salonPrice[position] +"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mDB = mHelper.getWritableDatabase();
        Cursor cursor = mDB.rawQuery("SELECT _ID, _PDNAME, _COUNT, _RICE  FROM MyTable", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tvpdName.add(cursor.getString(1));
            tvCount.add(cursor.getString(2));
            tvRice.add(cursor.getString(3));
            cursor.moveToNext();
        }

        startManagingCursor(cursor);

        cursor.close();
        mDB.close();

        final ListView lvCart = (ListView)findViewById(R.id.lvCart);
        adapter = new MyAdapter(this, tvpdName,tvCount,tvRice);
        lvCart.setAdapter(adapter);

        //product數量
        for (int i=1;i<101;i++){
            product_count.add(i+"");
        }

        final DatabaseReference firebase_custmer = FirebaseDatabase.getInstance().getReference("Orders");
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("AllProduct");
        reference_contacts.addValueEventListener(new ValueEventListener() {
            //存取產品的名稱和價格
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            AllProduct allProduct = ds.getValue(AllProduct.class);
                            Log.e("dddddddddddd", ds.getValue() + "");
                            Name[custmerid[0]] = allProduct.productName;
                            salonPrice[custmerid[0]] = allProduct.salonPrice;
                            custmerid[0] = custmerid[0]+1;
                    }

            }
                @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });


        //定單查詢自己的
        firebase_custmer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bundlei[0] = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Order order1 = ds.getValue(Order.class);
                    if (clientID.equals(order1.client)){
                        bundlei[0] = bundlei[0] + 1;
                    }
                    if ("1023".equals(clientID)){
                        bundlei[0] = bundlei[0] + 1;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //每天id重制
        Query today = firebase_custmer.orderByChild("orderDate").equalTo(orderDate);
        today.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                custmerid[0]=custmerid[0]+1;
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        leftlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                if(adapterView.getItemAtPosition(pos)=="查看訂單"){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, order_list.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("listlong",bundlei[0]);
                    bundle.putString("clientID",clientID);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if(adapterView.getItemAtPosition(pos)=="新增用戶"){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,addclient.class);
                    startActivity(intent);

                }
                if(adapterView.getItemAtPosition(pos)=="新增產品"){
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,addproduct.class);
                    startActivity(intent);
                }
            }
        });
        btIncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = "";
                String count = "";
                String rice = "";
                Sprice[0] = Integer.valueOf(edcount.getText().toString()) * Integer.valueOf(price.getText().toString());
                mDB = mHelper.getWritableDatabase();
                mDB.execSQL("INSERT INTO MyTable (_PDNAME, _COUNT, _RICE) VALUES('"+ tvpdName2.getText() +"','"+ edcount.getText() +"','"+ Sprice[0] +"')");
                Cursor cursor = mDB.rawQuery("SELECT _ID, _PDNAME, _COUNT, _RICE  FROM MyTable", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    name = cursor.getString(1);
                    count = cursor.getString(2);
                    rice = cursor.getString(3);
                    cursor.moveToNext();
                }
                tvpdName.add(name);
                tvCount.add(count);
                tvRice.add(rice);
                startManagingCursor(cursor);

                cursor.close();
                adapter.notifyDataSetChanged();
                mDB.close();
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long orderNumber = Long.valueOf(orderID)*1000+custmerid[0];
                DatabaseReference orderlist = firebase_custmer.child("SS"+orderNumber);
                Order order = new Order(clientID,"collectionDate","collectionState","deliveryState",orderDate,"SS"+ orderNumber,"recodedState","salesman","");
                orderlist.setValue(order);

                DatabaseReference orderProduct = firebase_custmer.child("SS"+orderNumber).child("product");
                int totalAmount = 0;
                mDB = mHelper.getWritableDatabase();
                Cursor cursor = mDB.rawQuery("SELECT _ID, _PDNAME, _COUNT, _RICE  FROM MyTable", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    order_product orderPd = new order_product(cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
                    totalAmount = totalAmount + cursor.getInt(3);
                    orderProduct.push().setValue(orderPd);
                    Map<String, Object> totalAmountMap = new HashMap<String, Object>();
                    totalAmountMap.put("totalAmount",String.valueOf(totalAmount));
                    orderlist.updateChildren(totalAmountMap);
                    cursor.moveToNext();
                }
                startManagingCursor(cursor);
                cursor.close();
                mDB.close();
            }
        });
    }

    //購物車
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        ArrayList<String> tvpdName;
        ArrayList<String> tvCount;
        ArrayList<String> tvRice;
        public MyAdapter(Context c, ArrayList<String> tvpdName, ArrayList<String> tvCount, ArrayList<String> tvRice){
            inflater = LayoutInflater.from(c);
            this.tvpdName = tvpdName;
            this.tvCount = tvCount;
            this.tvRice = tvRice;
        }
        @Override
        public int getCount() {
            return tvpdName.size();
        }

        @Override
        public Object getItem(int i) {
            return tvpdName.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null){
                view = inflater.inflate(R.layout.cart_list,viewGroup,false);
            }
            TextView tvpdName2,tvCount2,tvRice2;
            tvpdName2 = (TextView) view.findViewById(R.id.tvpdName);
            tvCount2 = (TextView) view.findViewById(R.id.tvCount);
            tvRice2 = (TextView) view.findViewById(R.id.tvRice);
            ImageButton delect = (ImageButton)view.findViewById(R.id.imageButton2);
            tvpdName2.setText(tvpdName.get(i));
            tvCount2.setText(tvCount.get(i) + "個");
            tvRice2.setText(tvRice.get(i));
            delect.setTag(i);
            delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int[] listcount = {0};
                    mDB = mHelper.getWritableDatabase();
                    mDB.execSQL("delete from MyTable where _ID=='"+ (i+1) +"'");
                    tvpdName.remove(i);
                    tvCount.remove(i);
                    tvRice.remove(i);
                    notifyDataSetChanged();
                    mDB = mHelper.getWritableDatabase();
                    //重制ID
                    Cursor cursor = mDB.rawQuery("SELECT _ID, _PDNAME, _COUNT, _RICE  FROM MyTable", null);
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        listcount[0]++;
                        mDB.execSQL("Update MyTable  set  _ID = '" + listcount[0] +  "' where _ID='"+cursor.getInt(0)+"'");
                        Log.e("SQLiteDBTestingActivity", "_ID = " + cursor.getInt(0));
                        cursor.moveToNext();
                    }
                    startManagingCursor(cursor);
                    cursor.close();
                    mDB.close();

                }
            });
            return view;
        }
    }
}
