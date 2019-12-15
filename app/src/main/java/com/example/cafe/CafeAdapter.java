package com.example.cafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CafeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int TYPE_HEADER = 0;
    private final int TYPE_ITEM = 1;
    private ArrayList<Data> menuData;
    private String TAG = "포지션 테스트";

    public interface OnItemClickListener {void onItemClick(View view, int position);}
    private OnItemClickListener CafeListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){this.CafeListener = listener;}


    protected CafeAdapter(ArrayList<Data> MenuData){

        this.menuData = MenuData;
    }

    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;
        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false);
            holder = new HeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cafe_item, parent, false);
            holder = new ItemViewHolder(view);
        }

        return holder;
    }
    @Override
    public int getItemViewType(int position){
        if(position == 0){ return TYPE_HEADER;}

        else {return TYPE_ITEM;}
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        }else{
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            bodyBind(itemViewHolder, position);
        }
    }

    private void bodyBind(final ItemViewHolder holder, final int position){

        Data menuData1 = menuData.get(position - 1);
        holder.textView1.setText(menuData1.getTitle());
        holder.textView2.setText(menuData1.getContent());
        holder.textView3.setText(menuData1.getPrice()+"원");
      //  holder.imageView.setImageResource(abc.getResId());
    }
    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return menuData.size()+1;}


    // RecyclerView의 핵심인 ViewHolder 입니다.
    public class ItemViewHolder extends RecyclerView.ViewHolder {
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
        menuData = filteredList;
        notifyDataSetChanged();
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder{
        HeaderViewHolder(View headerView){
            super(headerView);
        }
    }
}