package com.beesham.shopifymerchantstore.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.data.Columns;
import com.beesham.shopifymerchantstore.ui.TagsFragment.OnListFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class TagsRecyclerViewAdapter extends RecyclerView.Adapter<TagsRecyclerViewAdapter.ViewHolder> {

    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private List<String> dataList;

    private OnTagItemClickListener mItemClickedListener;

    public interface OnTagItemClickListener {
        void OnTagItemClick(String tag);
    }

    public TagsRecyclerViewAdapter(OnListFragmentInteractionListener listener, Context mContext) {
        mListener = listener;
        this.mContext = mContext;
        mItemClickedListener = (OnTagItemClickListener) mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTitleView.setText(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        if(dataList == null) return 0;
        return dataList.size();
    }

    public void swapCursor(Set list) {
        dataList = new ArrayList<String>(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mTitleView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setOnClickListener(this);
            mTitleView = (TextView) view.findViewById(R.id.tag_name_view);
        }

        @Override
        public void onClick(View view) {
            mItemClickedListener.OnTagItemClick(dataList.get(getPosition()));
        }
    }
}
