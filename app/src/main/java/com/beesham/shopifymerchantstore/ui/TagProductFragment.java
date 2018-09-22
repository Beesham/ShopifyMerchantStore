package com.beesham.shopifymerchantstore.ui;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.adapters.TagProductRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.data.Columns;
import com.beesham.shopifymerchantstore.data.ProductProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TagProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TagProductFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = TagProductFragment.class.getSimpleName();

    private TagProductRecyclerViewAdapter mViewAdapter;
    private RecyclerView mRecyclerView;

    private static final int TAG_PRODUCT_LOADER = 3;

    private String mSelectedTag;
    private static final String TAG_KEY = "tag_key";

    public TagProductFragment() {
        // Required empty public constructor
    }

    public static TagProductFragment newInstance(String tag) {
        TagProductFragment fragment = new TagProductFragment();
        Bundle args = new Bundle();
        args.putString(TAG_KEY, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelectedTag = getArguments().getString(TAG_KEY);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() == null) {
            getActivity().getSupportLoaderManager().initLoader(TAG_PRODUCT_LOADER, null, this);
        } else {
            getActivity().getSupportLoaderManager().restartLoader(TAG_PRODUCT_LOADER, null, this);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tag_product, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mViewAdapter = new TagProductRecyclerViewAdapter(getContext());
            mRecyclerView.setAdapter(mViewAdapter);
        }
        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {
            case (TAG_PRODUCT_LOADER):
                return new CursorLoader(
                        getContext(),
                        ProductProvider.Variant.CONTENT_URI_VARIANT_JOIN,
                        new String[]{
                                "variant." + Columns.ProductColumns.PRODUCT_ID,
                                "product." + Columns.ProductColumns.TITLE,
                                "SUM(variant." + Columns.VariantColumns.INVENTORY_QUANTITY + ") as total_inventory",
                                "product." + Columns.ProductColumns.IMAGE_URL},
                        "product.tags like ?",
                        new String[]{"%" + mSelectedTag + "%"},
                        null
                );

            default: return null;
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mViewAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mViewAdapter.swapCursor(null);
    }
}
