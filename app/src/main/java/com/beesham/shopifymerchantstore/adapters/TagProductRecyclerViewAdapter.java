package com.beesham.shopifymerchantstore.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.data.Columns;
import com.squareup.picasso.Picasso;

public class TagProductRecyclerViewAdapter extends RecyclerView.Adapter<TagProductRecyclerViewAdapter.TagProductViewHolder> {

    private static final String LOG_TAG = TagProductRecyclerViewAdapter.class.getSimpleName();
    private Cursor mCursor;
    private Context mContext;

    private OnTagProductItemClickListener mItemClickedListener;

    public interface OnTagProductItemClickListener {
        void OnTagProductItemClick(String productId);
    }

    public class TagProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitleView;
        public ImageView mImageView;
        public TextView mTotalAvailableProductView;

        public TagProductViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleView = itemView.findViewById(R.id.tag_product_list_item_title_view);
            mImageView = itemView.findViewById(R.id.tag_product_list_item_image_view);
            mTotalAvailableProductView = itemView.findViewById(R.id.tag_product_list_item_variants_total_view);
        }

        @Override
        public void onClick(View view) {
            mCursor.moveToPosition(getPosition());
            //mItemClickedListener.OnTagProductItemClick();
        }
    }

    public TagProductRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        mItemClickedListener = (OnTagProductItemClickListener) mContext;
    }

    @NonNull
    @Override
    public TagProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(parent instanceof RecyclerView) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tag_product_list_item, parent, false);
            view.setFocusable(true);

            return new TagProductViewHolder(view);
        }else{
            throw new RuntimeException("Not bound to RecyclerViewSelection");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TagProductViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String imageSrc = mCursor.getString(mCursor.getColumnIndex(Columns.ProductColumns.IMAGE_URL));
        Picasso.with(mContext)
                .load(imageSrc)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.mImageView);

        holder.mTitleView.setText(mCursor.getString(mCursor.getColumnIndex(Columns.ProductColumns.TITLE)));
        holder.mTotalAvailableProductView.setText(mCursor.getString(mCursor.getColumnIndex("total_inventory")));
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
}
