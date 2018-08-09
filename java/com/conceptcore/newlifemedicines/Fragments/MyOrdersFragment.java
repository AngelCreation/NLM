package com.conceptcore.newlifemedicines.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.OrderAdapter;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.Models.OrderBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment implements OrderAdapter.OrderClickListner {

    private RecyclerView rvOrders;
    private LinearLayout dataViewOrders,errorViewOrders;
    private TextView txtErrorOrders;
    private SwipeRefreshLayout swipeRefreshOrders;

    private OrderAdapter orderAdapter;
    List<OrderBean> orderList = new ArrayList<>();
    private Call<List<OrderBean>> call;

    HelperMethods helperMethods = null;
    SharedPrefBean sharedPrefBean;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        rvOrders = view.findViewById(R.id.rvOrders);
        txtErrorOrders = view.findViewById(R.id.txtErrorOrders);
        errorViewOrders = view.findViewById(R.id.errorViewOrders);
        dataViewOrders = view.findViewById(R.id.dataViewOrders);
        swipeRefreshOrders = view.findViewById(R.id.swipeRefreshOrders);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        getAllOrders();

        rvOrders.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvOrders.setLayoutManager(llm);

        orderAdapter = new OrderAdapter(getActivity(),orderList);
        orderAdapter.setOrderClickListner(this);
        rvOrders.setAdapter(orderAdapter);

        swipeRefreshOrders.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllOrders();
            }
        });


        return view;
    }

    private void getAllOrders(){
        helperMethods.showProgress();
        OrderBean orderBean = new OrderBean();
        orderBean.setUserId(sharedPrefBean.getUserId());
        call = new NewLifeApiService().getNewLifeApi().getMyOrders(orderBean);
        call.enqueue(new Callback<List<OrderBean>>() {
            @Override
            public void onResponse(Call<List<OrderBean>> call, Response<List<OrderBean>> response) {
                helperMethods.hideProgress();

                if(response.code() == 200){
                    orderList.clear();
                    if(response.body().size() > 0){
                        dataViewOrders.setVisibility(View.VISIBLE);
                        errorViewOrders.setVisibility(View.GONE);

                        orderList.addAll(response.body());

                    } else {
                        dataViewOrders.setVisibility(View.GONE);
                        errorViewOrders.setVisibility(View.VISIBLE);
                    }
                    orderAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderBean>> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnOrderClicked(View view, int position, List<OrderBean> list) {
        OrderBean orderBean = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderBean.getOrderId());

        OrderDetailsFragment orderDetailsFragment = new OrderDetailsFragment();
        orderDetailsFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.frame, orderDetailsFragment).addToBackStack(null).commit();
    }
}
