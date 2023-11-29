package com.example.payway.activities_and_fragments.fragments;

import static com.example.payway.Data_Managers.Firebase.currentUserId;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;
import com.example.payway.activities_and_fragments.Activities.MainActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class My_cart extends Fragment  {

    private ProductViewModel productViewModel;
    private ProductCardManager productCardManager;

    private List<Product> productList;
    //    Button bynow;
    AtomicReference<Double> totalDouble;

    //paypal variables
    private static final String TAG = "MyTag";
    PaymentButtonContainer paymentButtonContainer;


    public My_cart() {
        // Required empty public constructor
    }

    public static My_cart newInstance(String param1, String param2) {
        My_cart fragment = new My_cart();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);


        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        ImageView back = view.findViewById(R.id.back);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view2);
        TextView total = view.findViewById(R.id.Totale);
        paymentButtonContainer=view.findViewById(R.id.payment_button_container);
//        paymentButtonContainer.setAlpha(0.9f);
//        bynow = view.findViewById(R.id.Bynow);

        //2 cards in row
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        // Get reference to Firestore collection containing the user's product list
        CollectionReference productListRef = FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserId())
                .collection("productList");

        totalDouble = new AtomicReference<>(0.0); // Initialize as AtomicReference
        // Retrieve data from Firestore
        productListRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Product> retrievedProductList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Convert Firestore document to Product object
                    Product product = document.toObject(Product.class);
                    retrievedProductList.add(product);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        totalDouble.updateAndGet(v -> v + Double.parseDouble(product.getProductPrice()));
                    }
                }

                // Update the RecyclerView adapter with the retrieved product list
                productCardManager = new ProductCardManager(getContext(), retrievedProductList);
                recyclerView.setAdapter(productCardManager);

                // Retrieve the final total after iteration
                Double finalTotal = totalDouble.get();
                total.setText(String.valueOf(finalTotal)); // or textView.setText(Double.toString(totalPrice));
            } else {
                // Handle unsuccessful Firestore query
                Log.e("Firestore", "Error getting documents: ", task.getException());
            }
        });




        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.cartbackpress();
                }
            }
        });

        // Handle back press of the phone
        back.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.cartbackpress();
            }
        });

        initiatePayPalPayment();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Access the MainActivity
        MainActivity mainActivity = (MainActivity) requireActivity();
        // Hide the bottom app bar
        mainActivity.hideBottomAppBar();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Access the MainActivity
        MainActivity mainActivity = (MainActivity) requireActivity();
        // Hide the bottom app bar
        mainActivity.showBottomAppBar();
    }

    private void initiatePayPalPayment(){
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
                                                        .value(String.valueOf(totalDouble))
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
                                Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            );
        }
    }

}