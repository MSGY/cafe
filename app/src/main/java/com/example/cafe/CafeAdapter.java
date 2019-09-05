package com.example.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class CafeAdapter extends RecyclerView.Adapter<CafeAdapter.ItemViewHolder>{

    private ArrayList<Data> listData;

    //커스텀 온클릭 리스너입니다.
    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }
    // 리스너 객체 참조를 저장하는 변수입니다.
    private OnItemClickListener CafeListener = null;

    //OnItemClcikListener 리스너 참조 객체를 어댑터에 전달하는 메소드입니다.
    public void setOnItemClickListener(OnItemClickListener listener){
        this.CafeListener = listener;

    }

    // adapter에 들어갈 list 입니다.//
    public CafeAdapter(ArrayList<Data> ListData){
        listData = ListData;
        setHasStableIds(true);
    }
    public long getItemId(int position){
        return position;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cafe_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Data abc = listData.get(position);
        holder.textView1.setText(abc.getTitle());
        holder.textView2.setText(abc.getContent());
        holder.textView3.setText(abc.getPrice());
        holder.imageView.setImageResource(abc.getResId());
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }
    public void filterList(ArrayList<Data> filteredList ){
        listData = filteredList;
        notifyDataSetChanged();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.

    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public ImageView imageView;
        int pos = getAdapterPosition();

        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(pos != RecyclerView.NO_POSITION){
                            if(CafeListener != null){
                                CafeListener.onItemClick(view, pos);
                            }
                    }
                }
            });
        }


    }
}


