package com.conceptcore.newlifemedicines.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.HistoryAdapter;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.Address;
import com.conceptcore.newlifemedicines.Models.OrderBean;
import com.conceptcore.newlifemedicines.Models.OrderDetails;
import com.conceptcore.newlifemedicines.Models.Prescription;
import com.conceptcore.newlifemedicines.Models.Product;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
//import retrofit2.Callback;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsFragment extends Fragment {

    private TextView txtName,txtAdd1,txtAdd2,txtCityStatePin,txtTotal,txtPresTitle,txtPresComments;
    private HistoryAdapter historyAdapter;
    private RecyclerView rvHistory;
    private LinearLayout llPres,llPresComments;
    private ImageView imgPres;
    private List<Product> productList = new ArrayList<>();

    HelperMethods helperMethods = null;
    SharedPrefBean sharedPrefBean;

    String orderId;

    private Call<OrderDetails> call;


    public OrderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        txtName = view.findViewById(R.id.txtName);
        txtAdd1 = view.findViewById(R.id.txtAdd1);
        txtAdd2 = view.findViewById(R.id.txtAdd2);
        txtCityStatePin = view.findViewById(R.id.txtCityStatePin);
        rvHistory = view.findViewById(R.id.rvHistory);
        txtTotal = view.findViewById(R.id.txtTotal);

        llPres = view.findViewById(R.id.llPres);
        imgPres = view.findViewById(R.id.imgPres);
        txtPresTitle = view.findViewById(R.id.txtPresTitle);
        txtPresComments = view.findViewById(R.id.txtPresComments);
        llPresComments = view.findViewById(R.id.llPresComments);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            orderId = bundle.getString("orderId");
        }

        getOrderDetails();

        rvHistory.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvHistory.setLayoutManager(llm);

        historyAdapter = new HistoryAdapter(getActivity(),productList);
        rvHistory.setAdapter(historyAdapter);

        return view;
    }

    private void getOrderDetails(){
        helperMethods.showProgress();
        OrderBean orderBean = new OrderBean();
        orderBean.setUserId(sharedPrefBean.getUserId());
        orderBean.setOrderId(orderId);
        call = new NewLifeApiService().getNewLifeApi().getOrderDetails(orderBean);
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    OrderDetails orderDetails = response.body();

                    setOrderData(orderDetails);

                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOrderData(OrderDetails orderDetails){
        Address address = orderDetails.getAddress();
        txtName.setText(address.getName());
        txtAdd1.setText(address.getAdd1());
        txtAdd2.setText(address.getAdd2());
        txtCityStatePin.setText(address.getCity() + " " + address.getState() + " - "+ address.getPin());

//        Log.e("size products",""+ orderDetails.getProduct().size());
        productList.addAll(orderDetails.getProduct());
        historyAdapter.notifyDataSetChanged();

        Prescription prescription = orderDetails.getPrescription();
        if(prescription != null){
            llPres.setVisibility(View.VISIBLE);
            Picasso.with(getActivity())
                    .load(Constants.HOST_NAME + prescription.getPresImg())
//                .resize(260, 240)
//                .centerCrop()
                    .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.medicines))
//                .error(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .fit()
//                .resize(R.dimen.product_width, R.dimen.product_height)
//                .centerCrop()
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(imgPres);

            txtPresTitle.setText(prescription.getPresTitle());

            try{
                String comment = prescription.getPresComments();
                if(comment != null && comment != ""){
                    llPresComments.setVisibility(View.VISIBLE);
                    txtPresComments.setText(prescription.getPresComments());
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        }

        txtTotal.setText(orderDetails.getAmount());
    }


}
