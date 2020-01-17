package com.example.cafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafe.KakaoMap.MapActivity;
import com.example.cafe.cafe.CafeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private CafeAdapter mAdapter;
    private static ArrayList<Data> menuData;
    RecyclerView recyclerView;
    EditText editText;
    Button signoutBtn;
    Button main2MapBtn;

    private String TAG = "파이어베이스 테스트";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuData = new ArrayList<>();

        final TextView totalPrice = findViewById(R.id.textView5);
        init(totalPrice);
        setSearch();

        main2MapBtn = findViewById(R.id.button2);
        main2MapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main2map = new Intent(MainActivity.this, MapActivity.class);
                main2map.putExtra("total", totalPrice.getText().toString());
                startActivity(main2map);
            }
        });

        signoutBtn = findViewById(R.id.btn_logout);
        signoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent menu2login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(menu2login);
            }
        });
      }

    public void init(final TextView tv) {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("menu")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Data data = new Data(doc.getString("Title"), doc.getString("Content"), doc.getString("Price"));
                                menuData.add(data);
                            }
                        }
                        mAdapter = new CafeAdapter();
                        mAdapter.setData(menuData);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
                               int sum = 0;
                                @Override
                                public void onItemClick(View view, int pos) {

                                    final String menu[] = {"사이즈 업 ","얼음 추가","샷 추가","휘핑크림 추가"};
                                    final boolean selectedItems[] = {false, false, false, false};
                                    //final ArrayList<Object> selectedItems = new ArrayList<>();
                                    final Data PriceValues = menuData.get(pos);
                                    new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("추가 옵션")
                                            .setMultiChoiceItems(menu, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                                                    selectedItems[i]=isChecked;

                                                }
                                            })
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    //   String[] options = getResources().getStringArray(R.array.menu);

                                                    //   StringBuilder optionMenu = new StringBuilder("선택하신 추가 옵션은  ");

                                                        String str = "선택하신 추가 옵션은 : ";
                                                        for (int i = 0; i < selectedItems.length; i++) {
                                                            if (selectedItems[i]) {
                                                                str = str + "「" + menu[i] + "」 ";
                                                                Toast.makeText(MainActivity.this, str + "입니다", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
//                                                        if(!selectedItems[which]){
//                                                            Toast.makeText(MainActivity.this, "선택하신 추가 옵션은 없습니다", Toast.LENGTH_SHORT).show();
//                                                        }
                                                    sum += Integer.parseInt(PriceValues.getPrice());
                                                    String total = Integer.toString(sum);
                                                    tv.setText("총 주문금액 : "+ total+"원");

                                                    Log.d(TAG, "합산가격2 "+ total);
                                                }
                                            })
                                            .setNegativeButton("취소", null)
                                            .show();
                                }
                            });
                        }
                        });

                    }

    private void filter(String text) {
        ArrayList<Data> filteredList = new ArrayList<>();
        for (Data SearchData : menuData) {
            if (SearchData.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(SearchData);
            }
        }
        mAdapter.filterList(filteredList);
    }
    public void setSearch() {
        editText = findViewById(R.id.menu_search);
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

}