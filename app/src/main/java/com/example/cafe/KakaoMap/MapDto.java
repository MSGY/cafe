package com.example.cafe.KakaoMap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapDto implements Serializable {
    @SerializedName("documents")
    public List<Documents> documents = new ArrayList<>();

    public List<Documents> getDocuments() {
        return documents;
    }

    public class Documents{
        public address getAddress() {
            return address;
        }

        @SerializedName("address") address address;

        public class address{


            @SerializedName("address_name") String address_name;

            public String getAddress_name() {return address_name;}

        }
    }

}
