package com.example.payway;

import android.app.Application;
import android.os.Build;

import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PayPalCheckout.setConfig(new CheckoutConfig(
                    this,
                    "AR7hD0bhGZ57ZfdV3Or44oyu-lCFLWLJmLXNf-1PmuemNmeRyCoFh0txK9aV8iZf5rL9IeiLMChJ8DGx",
                    Environment.SANDBOX,
                    CurrencyCode.USD,
                    UserAction.PAY_NOW,
                    "com.example.payway://paypalpay"
            ));
        }

    }
}
