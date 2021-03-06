package com.beesham.shopifymerchantstore.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.data.Columns;
import com.squareup.picasso.Picasso;

/**
 * Created by Beesham on 12/30/2017.
 */

public class ProductsRecyclerViewAdapter extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductViewHolder> {

    private static final String LOG_TAG = ProductsRecyclerViewAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    private OnItemClickListener mItemClickedListener;

    public interface OnItemClickListener {
        void OnItemClick(String productId);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitleView;
        public ImageView mImageView;
        public TextView mDescriptionView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleView = itemView.findViewById(R.id.product_title_text_view);
            mImageView = itemView.findViewById(R.id.product_image_image_view);
            mDescriptionView = itemView.findViewById(R.id.product_description_text_view);
        }

        @Override
        public void onClick(View view) {
            mCursor.moveToPosition(getPosition());
            mItemClickedListener.OnItemClick(mCursor.getString(mCursor.getColumnIndex(Columns.ProductColumns.PRODUCT_ID)));
        }
    }

    public ProductsRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        mItemClickedListener = (OnItemClickListener) mContext;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_list_item, parent, false);
            view.setFocusable(true);

            return new ProductViewHolder(view);
        }else{
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String imageSrc = mCursor.getString(mCursor.getColumnIndex(Columns.ProductColumns.IMAGE_URL));
        Picasso.with(mContext)
                .load(imageSrc)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImageView);

        holder.mTitleView.setText(mCursor.getString(mCursor.getColumnIndex(Columns.ProductColumns.TITLE)));
        holder.mDescriptionView.setText(mCursor.getString(mCursor.getColumnIndex(Columns.ProductColumns.DESCRIPTION)));
    }

    @Override
    public int getItemCount() {
        if(mCursor == null) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }
}
