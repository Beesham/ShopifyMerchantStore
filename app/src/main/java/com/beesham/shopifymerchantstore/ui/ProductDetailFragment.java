package com.beesham.shopifymerchantstore.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beesham.shopifymerchantstore.BuildConfig;
import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.adapters.ProductsRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.adapters.VariantsRecyclerViewAdapter;
import com.beesham.shopifymerchantstore.model.Product;
import com.beesham.shopifymerchantstore.model.ProductsList;
import com.beesham.shopifymerchantstore.model.SingleProduct;
import com.beesham.shopifymerchantstore.service.ProductServiceGenerator;
import com.beesham.shopifymerchantstore.service.ShopifyApiEndpoints;
import com.beesham.shopifymerchantstore.utils.ProductUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    private static final String LOG_TAG = ProductDetailFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PRODUCT_ID = "product_id";

    private String mProductId;

    private Product mProduct;

    private ImageView mProductImageView;
    private TextView mProductTitle;
    private TextView mProductDescription;

    private RecyclerView mRecyclerView;
    private VariantsRecyclerViewAdapter mAdapter;


    private OnFragmentInteractionListener mListener;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param productId
     * @return A new instance of fragment ProductDetailFragment.
     */
    public static ProductDetailFragment newInstance(String productId) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getString(ARG_PRODUCT_ID);
            doServiceCall();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        mProductImageView = view.findViewById(R.id.product_image_image_view);
        mProductTitle = view.findViewById(R.id.product_title_text_view);
        mProductDescription = view.findViewById(R.id.product_description_text_view);

        mRecyclerView = view.findViewById(R.id.variants_recycler_view);
        mAdapter = new VariantsRecyclerViewAdapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    private void doServiceCall(){

        ShopifyApiEndpoints shopifyApiEndpoints =
                ProductServiceGenerator.createService(ShopifyApiEndpoints.class);
        Call<SingleProduct> call = shopifyApiEndpoints.getSingleProduct(mProductId, BuildConfig.SHOPIFY_ACCESS_TOKEN);

        call.enqueue(new Callback<SingleProduct>() {
            @Override
            public void onResponse(Call<SingleProduct> call, Response<SingleProduct> response) {
                mProduct = response.body().getProduct();
                setDetails();
            }

            @Override
            public void onFailure(Call<SingleProduct> call, Throwable t) {
                Log.d(LOG_TAG,"Call failed" + t.getMessage());
            }
        });
    }

    private void setDetails() {
        Picasso.with(getContext())
                .load(mProduct.getImage().getSrc())
                .placeholder(R.mipmap.ic_launcher)
                .into(mProductImageView);

        mProductTitle.setText(mProduct.getTitle());
        mProductDescription.setText(mProduct.getBodyHtml());

        mAdapter.swapList(mProduct.getVariants());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            /*throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
