package com.beesham.shopifymerchantstore.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beesham.shopifymerchantstore.BuildConfig;
import com.beesham.shopifymerchantstore.R;
import com.beesham.shopifymerchantstore.model.Product;
import com.beesham.shopifymerchantstore.model.ProductsList;
import com.beesham.shopifymerchantstore.model.SingleProduct;
import com.beesham.shopifymerchantstore.service.ProductServiceGenerator;
import com.beesham.shopifymerchantstore.service.ShopifyApiEndpoints;
import com.beesham.shopifymerchantstore.utils.ProductUtils;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    private void doServiceCall(){

        ShopifyApiEndpoints shopifyApiEndpoints =
                ProductServiceGenerator.createService(ShopifyApiEndpoints.class);
        Call<SingleProduct> call = shopifyApiEndpoints.getSingleProduct(mProductId, BuildConfig.SHOPIFY_ACCESS_TOKEN);

        call.enqueue(new Callback<SingleProduct>() {
            @Override
            public void onResponse(Call<SingleProduct> call, Response<SingleProduct> response) {
                mProduct = response.body().getProduct();
            }

            @Override
            public void onFailure(Call<SingleProduct> call, Throwable t) {
                Log.d(LOG_TAG,"Call failed" + t.getMessage());
            }
        });
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
