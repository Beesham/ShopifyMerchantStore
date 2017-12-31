package com.beesham.shopifymerchantstore.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.model.Variant;

import java.util.List;

/**
 * Created by Beesham on 12/31/2017.
 */

public class VariantsRecyclerViewAdapter extends RecyclerView.Adapter<VariantsRecyclerViewAdapter.VariantsViewHolder> {

    private List<Variant> mList;

    public class VariantsViewHolder extends RecyclerView.ViewHolder {

        public TextView mVariantNameView;
        public TextView mVariantPriceView;

        public VariantsViewHolder(View itemView) {
            super(itemView);
            mVariantNameView = itemView.findViewById(R.id.variant_name_text_view);
            mVariantPriceView = itemView.findViewById(R.id.variant_price_text_view);
        }
    }

    @Override
    public VariantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vairant_list_item, parent, false);
            view.setFocusable(true);

            return new VariantsViewHolder(view);
        }else{
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }

    @Override
    public void onBindViewHolder(VariantsViewHolder holder, int position) {
        holder.mVariantNameView.setText(mList.get(position).getTitle());
        holder.mVariantPriceView.setText(mList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        if(mList == null) return 0;
        return mList.size();
    }

    public void swapList(List<Variant> newList) {
        mList = newList;
        notifyDataSetChanged();
    }
}
