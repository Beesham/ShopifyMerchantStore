package com.beesham.shopifymerchantstore.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Beesham on 12/30/2017.
 */

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter {

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ProductViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            //TODO: launch details view of product
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
