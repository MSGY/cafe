package com.example.cafe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private CafeAdapter adapter;
    private ArrayList<Data> listData;
    RecyclerView recyclerView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        init();
        setSearch();
        getOrder();
        MenuOptionClickEvent();
    }

    private void init() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CafeAdapter(listData);
        recyclerView.setAdapter(adapter);

    }

    private void getData() {
        listData = new ArrayList<>();
        // 임의의 데이터입니다.
        listData.add(new Data("핸드드립", "핸드드립입니다.", "1원", R.drawable.a));
        listData.add(new Data("아메리카노", "아메리카노입니다.", "2원", R.drawable.b));
        listData.add(new Data("에스프레소", "에스프레소입니다", "3원", R.drawable.c));
        listData.add(new Data("카페모카", "카페모카입니다", "4원", R.drawable.d));
        listData.add(new Data("카푸치노", "카푸치노입니다", "5원", R.drawable.e));
        listData.add(new Data("카라멜 마끼아또", "카라멜 마끼아또입니다", "6원", R.drawable.f));
        listData.add(new Data("그린티라떼", "그린티라떼입니다.", "7원", R.drawable.g));
        listData.add(new Data("초코라떼", "초코라떼입니다", "8원", R.drawable.h));
        listData.add(new Data("카페라떼", "카페라떼입니다", "9원", R.drawable.i));
        listData.add(new Data("콜드브루", "콜드브루입니다", "10원", R.drawable.j));
        listData.add(new Data("밀크티", "밀크티입니다", "11원", R.drawable.k));
        listData.add(new Data("레몬티", "레몬티입니다", "12원", R.drawable.l));
        listData.add(new Data("유자차", "유자차입니다", "13원", R.drawable.m));
        listData.add(new Data("얼그레이티", "얼그레이티입니다", "14원", R.drawable.n));
        listData.add(new Data("바닐라라떼", "바닐라라떼입니다", "15원", R.drawable.o));
        listData.add(new Data("123,", "456", "16원", R.drawable.p));
    }

    private void filter(String text) {
        ArrayList<Data> filteredList = new ArrayList<>();
        for (Data SearchData : listData) {
            if (SearchData.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(SearchData);
            }
        }
        adapter.filterList(filteredList);
    }

    public void setSearch() {
        editText = (EditText) findViewById(R.id.menu_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }
    public void getOrder(){

        final GestureDetector gestureDetector = new GestureDetector(MainActivity.this,new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                Log.d("TAG","onInterceptTouchEvent");
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child!=null && gestureDetector.onTouchEvent(e)) {
                    TextView price = (TextView) rv.getChildViewHolder(child).itemView.findViewById(R.id.textView3);
                    TextView TotalPrice = (TextView)findViewById(R.id.TextView5);
                    Toast.makeText(getApplication(), price.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }
    public void MenuOptionClickEvent(){
        adapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                final ArrayList<Object> selectedItems = new ArrayList<>();
                new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("추가메뉴")
                        .setMultiChoiceItems(R.array.menu, null, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                if (isChecked) {
                                    //항목이 선택이 되면 추가시킵니다.
                                    selectedItems.add(i);
                                } else if(selectedItems.contains(i)) {
                                    //아이템이 이미 배열에 있으면, 제거합니다.
                                    selectedItems.remove(Integer.valueOf(i));
                                }
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String[] options = getResources().getStringArray(R.array.menu);
                                String string = "선택하신 추가 옵션은 ";
                                for (int i = 0; i < selectedItems.size(); i++) {
                                    string += options[i] + " ";

                                }
                                Toast.makeText(MainActivity.this, string +" 입니다.", Toast.LENGTH_SHORT).show();
                            }

                        })
                        .setNegativeButton("취소", null)
                        .show();
            }
        });
    }
    public void TotalPrice(){adapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {

            }
        });
    }
}