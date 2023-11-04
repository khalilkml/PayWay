package com.example.payway;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.OrderRequest;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.paymentbutton.PaymentButtonContainer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MyTag";
    PaymentButtonContainer paymentButtonContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paymentButtonContainer = findViewById(R.id.payment_button_container);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            paymentButtonContainer.setup(
                    new CreateOrder() {
                        @Override
                        public void create(@NotNull CreateOrderActions createOrderActions) {
                            Log.d(TAG, "create: ");
                            ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                            purchaseUnits.add(
                                    new PurchaseUnit.Builder()
                                            .amount(
                                                    new Amount.Builder()
                                                            .currencyCode(CurrencyCode.USD)
                                                            .value("10.00")
                                                            .build()
                                            )
                                            .build()
                            );
                            OrderRequest order = new OrderRequest(
                                    OrderIntent.CAPTURE,
                                    new AppContext.Builder()
                                            .userAction(UserAction.PAY_NOW)
                                            .build(),
                                    purchaseUnits
                            );
                            createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                        }
                    },
                    new OnApprove() {
                        @Override
                        public void onApprove(@NotNull Approval approval) {
                            approval.getOrderActions().capture(new OnCaptureComplete() {
                                @Override
                                public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                    Log.d(TAG, String.format("CaptureOrderResult: %s", result));
                                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
            );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
