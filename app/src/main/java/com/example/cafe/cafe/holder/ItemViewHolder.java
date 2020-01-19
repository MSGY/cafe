package com.example.cafe.cafe.holder;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.cafe.Data;
import com.example.cafe.R;
import com.example.cafe.cafe.CafeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ItemViewHolder extends GyHolder{
    public TextView mTitle;
    public TextView mContent;
    public TextView mPrice;
    public ImageView mDrinkImage;

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
        mDrinkImage = itemView.findViewById(R.id.drink_image);

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
        StorageReference storage = FirebaseStorage.getInstance().getReferenceFromUrl(menuData.getUrl());
        storage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
            if(task.isSuccessful()){
                Glide.with(mContext)
                        .load(task.getResult())
                        .apply(RequestOptions.circleCropTransform())
                        .into(mDrinkImage);
            }

            }
        });
        //StorageReference getReference = storage.getReferenceFromUrl("gs://my-cafe-e8f07.appspot.com/DrinkImage/*");

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.mTitle.setText(menuData.getTitle());
        itemViewHolder.mContent.setText(menuData.getContent());
        itemViewHolder.mPrice.setText(mContext.getString(R.string.price, menuData.getPrice()));
    }

}