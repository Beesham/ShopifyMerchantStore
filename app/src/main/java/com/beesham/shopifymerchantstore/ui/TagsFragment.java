package com.beesham.shopifymerchantstore.ui;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.adapters.TagsRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.data.Columns;
import com.beesham.shopifymerchantstore.data.ProductProvider;
import com.beesham.shopifymerchantstore.utils.ProductUtils;

import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TagsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = TagsFragment.class.getSimpleName();

    private TagsRecyclerViewAdapter mTagsRecyclerViewAdapter;
    private RecyclerView mRecyclerView;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TagsFragment() {
    }

    public static TagsFragment newInstance() {
        TagsFragment fragment = new TagsFragment();
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
            getActivity().getSupportLoaderManager().initLoader(2, null, this);
        } else {
            getActivity().getSupportLoaderManager().restartLoader(2, null, this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tags_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mTagsRecyclerViewAdapter = new TagsRecyclerViewAdapter(mListener, getContext());
            mRecyclerView.setAdapter(mTagsRecyclerViewAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().restartLoader(2, null, this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projections = {
                Columns.ProductColumns.TAGS
        };

        return new CursorLoader(
                getContext(),
                ProductProvider.Product.CONTENT_URI,
                projections,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mTagsRecyclerViewAdapter.swapCursor(ProductUtils.filterTags(data));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mTagsRecyclerViewAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
    }
}
