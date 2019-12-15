package com.example.cafe.cafe.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cafe.Data;
import com.example.cafe.R;
import com.example.cafe.cafe.CafeAdapter;

public class ItemViewHolder extends GyHolder{
    public TextView mTitle;
    public TextView mContent;
    public TextView mPrice;
    public ImageView mPic;

    private Context mContext;

    private CafeAdapter.OnItemClickListener mListener;

    public ItemViewHolder setListener(CafeAdapter.OnItemClickListener listener){
        mListener = listener;
        return this;
    }

    // 여기서 subView를 setting 해줍니다.
    public ItemViewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();

        mTitle = itemView.findViewById(R.id.title);
        mContent = itemView.findViewById(R.id.content);
        mPrice = itemView.findViewById(R.id.price);
        mPic = itemView.findViewById(R.id.pic);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    if (mListener != null) {
                        mListener.onItemClick(view, pos);
                    }
                }
            }
        });
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, Data menuData) {
        ItemViewHolder itemVIewHolder = (ItemViewHolder) holder;
        itemVIewHolder.mTitle.setText(menuData.getTitle());
        itemVIewHolder.mContent.setText(menuData.getContent());
        itemVIewHolder.mPrice.setText(mContext.getString(R.string.price, menuData.getPrice()));
        //  itemViewholder.mPic.setImageResource(abc.getResId());
    }

}