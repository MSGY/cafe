package com.example.cafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private CafeAdapter adapter;
    private static ArrayList<Data> menuData;
    RecyclerView recyclerView;
    EditText editText;
    Button signout_btn;
    Button Main2Map;

    private String TAG = "파이어베이스 테스트";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuData = new ArrayList<>();

        init();
        setSearch();
        Main2Map = findViewById(R.id.button2);
        Main2Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main2map = new Intent(MainActivity.this, MapActivity.class);
                startActivity(main2map);
            }
        });
        signout_btn = findViewById(R.id.btn_logout);
        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent menu2login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(menu2login);
            }
        });
      }

    private void init() {
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
                        adapter = new CafeAdapter(menuData);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        adapter.setOnItemClickListener(new CafeAdapter.OnItemClickListener() {
                                int sum = 0;
                                @Override
                                public void onItemClick(View view, int pos) {
                                    final Data PriceValues = menuData.get(pos);
                                    final ArrayList<Object> selectedItems = new ArrayList<>();
                                    new AlertDialog.Builder(MainActivity.this)
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
                                                    if (selectedItems.size() != 0) {
                                                        for (int i = 0; i < selectedItems.size(); i++) {
                                                            string += options[i] + " ";
                                                        }
                                                        Toast.makeText(MainActivity.this, string + "입니다.", Toast.LENGTH_SHORT).show();
                                                    }
                                                    else{
                                                        Toast.makeText(MainActivity.this, "선택하신 추가메뉴는 없습니다.", Toast.LENGTH_SHORT).show();
                                                    }

                                                    sum += Integer.parseInt(PriceValues.getPrice())-1;
                                                    String total = Integer.toString(sum);
                                                    TextView TotalPrice = findViewById(R.id.textView5);
                                                    TotalPrice.setText("총 주문금액은 : "+ total+"원");
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
}