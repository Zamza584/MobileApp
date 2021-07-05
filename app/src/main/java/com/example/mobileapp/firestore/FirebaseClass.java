package com.example.mobileapp.firestore;



import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mobileapp.AddProduct;
import com.example.mobileapp.Checkout;
import com.example.mobileapp.Product;
import com.example.mobileapp.models.AddressModel;
import com.example.mobileapp.models.ProductModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FirebaseClass {
    private static DatabaseReference db;

    public static void registerProduct(AddProduct addProduct, ProductModel productInfo) {
        db = FirebaseDatabase.getInstance().getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                db.child("products").child(productInfo.getProduct_name()).setValue(productInfo);
                Toast.makeText(addProduct, "product added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(addProduct, "Fail to add data " + error, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public static void addAddress(Checkout checkout, AddressModel address) {
        db = FirebaseDatabase.getInstance().getReference().child("Users").child(address.getUser_id());

        Map<String, Object> addressInfo = new HashMap<>();
        addressInfo.put("address", address.getAddress());
        addressInfo.put("postalCode", address.getPostal_code());
        addressInfo.put("state", address.getState());

        db.updateChildren(addressInfo);

    }

    public void uploadProductDetails(AddProduct addProduct, Product product) {

    }

    public static String getUserID() {
        String userID = "";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        userID = user.getUid();

        return userID;

    }

}





