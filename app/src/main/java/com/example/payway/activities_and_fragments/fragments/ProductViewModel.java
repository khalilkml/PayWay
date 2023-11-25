package com.example.payway.activities_and_fragments.fragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.payway.Data_Managers.Product;

import java.util.List;

public class ProductViewModel extends ViewModel {
    private MutableLiveData<List<Product>> productListLiveData = new MutableLiveData<>();

    public LiveData<List<Product>> getProductListLiveData() {
        return productListLiveData;
    }

    public void setProductList(List<Product> productList) {
        productListLiveData.setValue(productList);
    }
}
