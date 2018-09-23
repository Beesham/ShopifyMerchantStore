package com.beesham.shopifymerchantstore.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.adapters.ProductsRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.data.Columns;
import com.beesham.shopifymerchantstore.data.ProductProvider;
import com.beesham.shopifymerchantstore.model.Filters;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = ProductFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private ProductsRecyclerViewAdapter mAdapter;

    private static final int PRODUCT_LOADER_NO_FILTERS = 0;
    private static final int PRODUCT_LOADER_WITH_FILTERS = 1;

    //Bundle argument keys
    private static final String ARGS_KEY_FILTER = "filters";
    private static final String ARGS_KEY_QUERY = "query";

    private ArrayList mFilters;
    private String mSearchQuery;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(ArrayList filters, String query) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putIntegerArrayList(ARGS_KEY_FILTER, filters);
        args.putString(ARGS_KEY_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getArguments() == null) {
            getActivity().getSupportLoaderManager().initLoader(PRODUCT_LOADER_NO_FILTERS, null, this);
        } else {
            mFilters = getArguments().getIntegerArrayList(ARGS_KEY_FILTER);
            mSearchQuery = getArguments().getString(ARGS_KEY_QUERY);
            getActivity().getSupportLoaderManager().restartLoader(PRODUCT_LOADER_WITH_FILTERS, null, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product, container, false);

        mRecyclerView = view.findViewById(R.id.products_recycler_view);
        mAdapter = new ProductsRecyclerViewAdapter(getContext());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        getActivity().setTitle(R.string.fragment_all_products);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projections = {
                Columns.ProductColumns.PRODUCT_ID,
                Columns.ProductColumns.TITLE,
                Columns.ProductColumns.IMAGE_URL,
                Columns.ProductColumns.DESCRIPTION
        };

        switch (id) {
            case(PRODUCT_LOADER_NO_FILTERS):
                return new CursorLoader(
                        getContext(),
                        ProductProvider.Product.CONTENT_URI,
                        projections,
                        null,
                        null,
                        null
                );

            case(PRODUCT_LOADER_WITH_FILTERS):
                if(mFilters.contains(Filters.FILTER_BY_TITLE)) {
                    return new CursorLoader(
                            getContext(),
                            ProductProvider.Product.CONTENT_URI,
                            projections,
                            Columns.ProductColumns.TITLE + " like ?",
                            new String[]{"%" + mSearchQuery + "%"},
                            null
                    );
                }else if(mFilters.contains(Filters.FILTER_BY_VENDOR)) {
                    return new CursorLoader(
                            getContext(),
                            ProductProvider.Product.CONTENT_URI,
                            projections,
                            Columns.ProductColumns.VENDOR + " like ?",
                            new String[]{"%" + mSearchQuery + "%"},
                            null
                    );
                }else if(mFilters.contains(Filters.FILTER_BY_TYPE)) {
                    return new CursorLoader(
                            getContext(),
                            ProductProvider.Product.CONTENT_URI,
                            projections,
                            Columns.ProductColumns.PRODUCT_TYPE + " like ?",
                            new String[]{"%" + mSearchQuery + "%"},
                            null
                    );
                }
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        Log.i(LOG_TAG, "Size of cursor: " + mAdapter.getItemCount());
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
