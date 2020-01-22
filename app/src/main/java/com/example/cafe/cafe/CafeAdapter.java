package com.example.cafe.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafe.Data;
import com.example.cafe.R;
import com.example.cafe.cafe.holder.GyHolder;
import com.example.cafe.cafe.holder.ItemViewHolder;

import java.util.ArrayList;


public class CafeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private ArrayList<Data> menuList;
    private String TAG = "포지션 테스트";

    public interface OnItemClickListener {void onItemClick(View view, int position);}
    private OnItemClickListener mCafeListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){this.mCafeListener = listener;}


    public CafeAdapter(){
        menuList = new ArrayList<>();
    }

    public void setData(ArrayList<Data> MenuData){
        this.menuList = MenuData;
    }

    public ArrayList<Data> getList() {
        return menuList;
    }

    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;
//        if (viewType == TYPE_HEADER) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
//            holder = new HeaderViewHolder(view);
//        } else {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cafe_item, parent, false);
            holder = new ItemViewHolder(view)
                    .setListener(mCafeListener);

//        }

        return holder;
    }
    @Override
    public int getItemViewType(int position){
        if(position == 0){ return TYPE_HEADER;}

        else {return TYPE_ITEM;}
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GyHolder) {
            ((GyHolder)holder).onBind(holder, menuList.get(position));
        }
    }
    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return menuList.size();}

    public void filterList(ArrayList<Data> filteredList ){
        menuList = filteredList;
        notifyDataSetChanged();
    }
}