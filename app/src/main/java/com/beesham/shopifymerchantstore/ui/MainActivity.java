package com.beesham.shopifymerchantstore.ui;

import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.beesham.shopifymerchantstore.BuildConfig;
import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.adapters.ProductsRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.model.Product;
import com.beesham.shopifymerchantstore.model.ProductsList;
import com.beesham.shopifymerchantstore.service.ProductServiceGenerator;
import com.beesham.shopifymerchantstore.service.ShopifyApiEndpoints;
import com.beesham.shopifymerchantstore.utils.ProductUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductsRecyclerViewAdapter.OnItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static android.support.v4.app.FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doServiceCall();

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) return;

            ProductFragment productFragment = ProductFragment.newInstance();

            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, productFragment)
                    .commit();
        }
    }

    private void doServiceCall(){

        ShopifyApiEndpoints shopifyApiEndpoints =
                ProductServiceGenerator.createService(ShopifyApiEndpoints.class);
        Call<ProductsList> call = shopifyApiEndpoints.getProducts("1", BuildConfig.SHOPIFY_ACCESS_TOKEN);

        call.enqueue(new Callback<ProductsList>() {
            @Override
            public void onResponse(Call<ProductsList> call, Response<ProductsList> response) {
                ProductUtils.logProducts(MainActivity.this, ProductUtils.prepareForCaching(response.body()));
            }

            @Override
            public void onFailure(Call<ProductsList> call, Throwable t) {
                Log.d(LOG_TAG,"Call failed" + t.getMessage());
            }
        });
    }

    @Override
    public void OnItemClick() {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, new ProductDetailFragment())
                .addToBackStack(null)
                .commit();
    }
}
