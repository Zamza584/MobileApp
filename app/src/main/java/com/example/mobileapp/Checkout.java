package com.example.mobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.mobileapp.firestore.FirebaseClass;
import com.example.mobileapp.models.AddressModel;
import com.example.mobileapp.utils.TCButton;

public class Checkout extends BaseActivity {
    //Checkout class extends Base Activity to get functions to make snackbar work

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        TCButton completeOrderBtn = findViewById(R.id.complete_order);
        View backButton = findViewById(R.id.back_button);
        ImageView shoppingCartImage = findViewById(R.id.shopping_cart_image);

        shoppingCartImage.setOnClickListener(v -> {
            startActivity(new Intent(Checkout.this, Cart.class));
            finish();
        });

        completeOrderBtn.setOnClickListener(new View.OnClickListener() {
            //created function to have button validate Register Details
            @Override
            public void onClick(View v) {

                if (validateRegisterDetails()) {
                    saveAddressToFirebase();

                    Intent intent = new Intent(Checkout.this, Overview.class);
                    startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            //created function to have the imageView go back to desired activity
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    private Boolean validateRegisterDetails() {
        //function to validate register details with the use of the showErrorSnackBar function
        //return false if input from our activity is empty
        //return true if inputs are filled out
        //Resource string.xml is used to grab text values

        EditText address = findViewById(R.id.inputAddress);
        EditText state = findViewById(R.id.inputState);
        EditText postalCode = findViewById(R.id.inputPostalCode);
        EditText creditCard = findViewById(R.id.inputCardNumber);
        EditText expiryDate = findViewById(R.id.inputEpiryDate);
        EditText ccv = findViewById(R.id.inputVerificationValue);

        if (TextUtils.isEmpty(address.getText().toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_address),true);
            return false;
        } else if (TextUtils.isEmpty(state.getText().toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_state),true);
            return false;
        } else if (TextUtils.isEmpty(postalCode.getText().toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_postal_code),true);
            return false;
        } else if (TextUtils.isEmpty(creditCard.getText().toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_credid_card_number),true);
            return false;
        } else if (TextUtils.isEmpty(expiryDate.getText().toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_expiry_date),true);
            return false;
        } else if (TextUtils.isEmpty(ccv.getText().toString().trim())) {
            showErrorSnackBar(getResources().getString(R.string.err_msg_enter_ccv),true);
            return false;
        } else {
            showErrorSnackBar("your details are valid", false);
            return true;
        }

    }

    private void saveAddressToFirebase() {
        EditText address = findViewById(R.id.inputAddress);
        EditText state = findViewById(R.id.inputState);
        EditText postalCode = findViewById(R.id.inputPostalCode);

        AddressModel addressClass = new AddressModel(FirebaseClass.getUserID(),
                address.getText().toString(),
                state.getText().toString(),
                postalCode.getText().toString());

        FirebaseClass.addAddress(Checkout.this, addressClass);

    }

    public void btnNotification(View view) {
        Intent intent = new Intent(Checkout.this, Notifications.class);
        startActivity(intent);
        finish();
    }

    public void btnUser(View view) {
        Intent intent = new Intent(Checkout.this, Profile.class);
        startActivity(intent);
        finish();
    }

    public void btnCart(View view) {
        Intent intent = new Intent(Checkout.this, Cart.class);
        startActivity(intent);
        finish();
    }


}

