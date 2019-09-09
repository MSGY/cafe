package com.example.cafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class CafeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    private ArrayList<Data> listData;
    @Nullable
    // 리스너 객체 참조를 저장하는 변수입니다.
    private OnItemClickListener CafeListener = null;
    //커스텀 온클릭 리스너입니다.
    public interface OnItemClickListener {void onItemClick(View view, int pos);}
    //OnItemClcikListener 리스너 참조 객체를 어댑터에 전달하는 메소드입니다.
    public void setOnItemClickListener(OnItemClickListener listener){this.CafeListener = listener;}

    // adapter에 들어갈 list 입니다.//
    protected CafeAdapter(ArrayList<Data> ListData){
        listData = ListData;
    }
    public long getItemId(int position){
        return position;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        RecyclerView.ViewHolder holder;
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            holder = new FooterViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer, parent, false);
            holder = new FooterViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cafe_item, parent, false);
            holder = new ItemViewHolder(view);
        }
        return holder;
    }
    @Override
    public int getItemViewType(int position){
       if(position == 0){
           return TYPE_HEADER;
       }
        else if(position == listData.size() + 1){
            return TYPE_FOOTER;
        }else
            return TYPE_ITEM;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

        }else if(holder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

        }else{
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            bodyBind(itemViewHolder, position);
        }
    }
    private void bodyBind(final ItemViewHolder holder, final int position){

        Data abc = listData.get(position - 1);
        holder.textView1.setText(abc.getTitle());
        holder.textView2.setText(abc.getContent());
        holder.textView3.setText(abc.getPrice());
        holder.imageView.setImageResource(abc.getResId());
    }
    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size()+2;}

    // RecyclerView의 핵심인 ViewHolder 입니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;
        public ImageView imageView;

        // 여기서 subView를 setting 해줍니다.
        ItemViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        if(CafeListener != null){
                            CafeListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }
    public void filterList(ArrayList<Data> filteredList ){
        listData = filteredList;
        notifyDataSetChanged();
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{
        FooterViewHolder(View footerView){
            super(footerView);
        }
    }
    class HeaderViewHolder extends RecyclerView.ViewHolder{
        HeaderViewHolder(View headerView){
            super(headerView);
        }
    }
}