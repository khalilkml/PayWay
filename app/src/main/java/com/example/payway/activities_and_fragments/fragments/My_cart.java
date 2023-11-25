package com.example.payway.activities_and_fragments.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.payway.Data_Managers.Product;
import com.example.payway.Data_Managers.ProductCardManager;
import com.example.payway.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class My_cart extends Fragment  {

    private ProductViewModel productViewModel;
    private ProductCardManager productCardManager;

    private List<Product> productList;

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
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        productList = new ArrayList<>(); // Initialize an empty product list
        productCardManager = new ProductCardManager(getContext(), productList);
        recyclerView.setAdapter(productCardManager);
        Log.d("ProductListContents", productList.toString());


        back.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}