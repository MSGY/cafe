package com.example.cafe.KakaoMap;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.cafe.R;

import javax.annotation.Nullable;

public class MapSearchFrag extends Fragment {
        public static final String TAG = "ASDFASDF";
        public AddressTransferListener mAddressTransferListener;

        @Override
        public void onAttach(Context context) {

            super.onAttach(context);
            if(context instanceof AddressTransferListener){
                mAddressTransferListener = (AddressTransferListener) context;
            }
        }
        @Override
        public void onDetach() {

            super.onDetach();
            mAddressTransferListener = null;

        }

        @Nullable
        @Override
        public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstance){

            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_search,
                                                                container, false);
            final EditText addressSearch = rootView.findViewById(R.id.address_Search);
            addressSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                   if( actionId == KeyEvent.KEYCODE_ENDCALL){
                       String inputAddress = addressSearch.getText().toString();
                       Log.d(TAG, "onCreateView: "+ inputAddress);
                       mAddressTransferListener.addressTransfer(inputAddress);

                       FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                       fragmentManager.beginTransaction().remove(MapSearchFrag.this).commit();
                       fragmentManager.popBackStack();

                       ((MapActivity)getActivity()).hideKeypad(addressSearch);

                       return true;
                   } else{
                       Log.d(TAG, "키값이 다름" + actionId);

                       return false;
                   }
                }
            });



            ImageView backpress_Btn = rootView.findViewById(R.id.backpress_Btn);
            backpress_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG,"ASDFASDF");
                }
            });


            return rootView;

        }

    public interface AddressTransferListener{

            String addressTransfer(String name);

    }

}
