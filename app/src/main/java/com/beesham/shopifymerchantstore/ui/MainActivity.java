package com.beesham.shopifymerchantstore.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.beesham.shopifymerchantstore.BuildConfig;
import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.adapters.ProductsRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.model.ProductsList;
import com.beesham.shopifymerchantstore.service.ProductServiceGenerator;
import com.beesham.shopifymerchantstore.service.ShopifyApiEndpoints;
import com.beesham.shopifymerchantstore.utils.ProductUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ProductsRecyclerViewAdapter.OnItemClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static android.support.v4.app.FragmentManager mFragmentManager;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_white_24);

        //get data
        doServiceCall();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) return;

            ProductFragment productFragment = ProductFragment.newInstance();

            mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, productFragment)
                    .commit();
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                //gets a list of fragments on the backstack and removes them so the user can't cycle through old fragments
                List<android.support.v4.app.Fragment> fragmentList = mFragmentManager.getFragments();
                if(!fragmentList.isEmpty()) {
                    for(int i = 0; i < fragmentList.size(); i++) {
                        mFragmentManager.beginTransaction().remove(fragmentList.get(i)).commit();
                    }
                }

                switch(item.getItemId()){
                    case(R.id.menu_drawer_all_products):
                        mFragmentManager.beginTransaction().add(R.id.fragment_container, ProductFragment.newInstance()).commit();
                        return true;

                    case(R.id.menu_drawer_tags):
                        mFragmentManager.beginTransaction().add(R.id.fragment_container, TagsFragment.newInstance()).commit();
                        return true;
                }

                return true;
            }
        });

        handleIntent(getIntent());
    }

    /**
     * Gets data from server and caches it into sqlite database
     */
    private void doServiceCall(){
        ShopifyApiEndpoints shopifyApiEndpoints =
                ProductServiceGenerator.createService(ShopifyApiEndpoints.class);
        Call<ProductsList> call = shopifyApiEndpoints.getProducts("1", BuildConfig.SHOPIFY_ACCESS_TOKEN);

        call.enqueue(new Callback<ProductsList>() {
            @Override
            public void onResponse(Call<ProductsList> call, Response<ProductsList> response) {
                //Caches the data in database
                ProductUtils.logProducts(MainActivity.this, ProductUtils.prepareProductsForCaching(response.body()));
                ProductUtils.logProductVariants(MainActivity.this, ProductUtils.prepareProductVariantsForCaching(response.body()));
            }

            @Override
            public void onFailure(Call<ProductsList> call, Throwable t) {
                Log.d(LOG_TAG,"Call failed" + t.getMessage());
            }
        });
    }

    @Override
    public void OnItemClick(String productId) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProductDetailFragment.newInstance(productId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Opens the navigation drawer
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    /**
     * Handles search intent from the search bar
     * @param intent
     */
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY).trim();

            //use the query to search your data somehow
            showFilterDialog(query);
        }
    }

    /**
     * Displays an alert dialog to filter the search query by specific fields
     * @param query
     */
    private void showFilterDialog(final String query) {
        final CharSequence[] filters = {"Title", "Vendor", "Type"};
        final ArrayList selectedItems = new ArrayList<>();

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMultiChoiceItems(filters, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                })
                .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFragmentManager.beginTransaction()
                                .addToBackStack("main")
                                .replace(R.id.fragment_container, ProductFragment.newInstance(selectedItems, query))
                                .commit();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Do nothing
                    }
                })
                .create();

        dialog.show();
    }
}
