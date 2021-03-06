package com.example.ryan.roomrep.TenantFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ryan.roomrep.Adapters.ItemClickListener;
import com.example.ryan.roomrep.Adapters.RepairRecyclerViewAdapter;
import com.example.ryan.roomrep.Classes.Network.FragmentEventListener;
import com.example.ryan.roomrep.Classes.Network.Network;
import com.example.ryan.roomrep.Classes.Repair;
import com.example.ryan.roomrep.Classes.Router.TenantRouter;
import com.example.ryan.roomrep.Classes.Router.TenantRouterAction;
import com.example.ryan.roomrep.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TenantRepairListFragment extends Fragment implements FragmentEventListener, ItemClickListener {
    Button btnAddRepair;

    RecyclerView rcyRepairsTenant;

    TenantRouterAction routerActionListener;

    TextView txtIsThereRepairs;

    private List<Repair> repairs;

    String houseAddress;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_tenant_repair_list, container, false);

        btnAddRepair = view.findViewById(R.id.btnAddRepair);

        rcyRepairsTenant = view.findViewById(R.id.rcyRepairsTenant);

        txtIsThereRepairs = view.findViewById(R.id.txtIsThereRepairs);

        rcyRepairsTenant.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Getting all Repairs...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if(!(repairs == null)) {
            Collections.reverse(repairs);
            RepairRecyclerViewAdapter adapter = new RepairRecyclerViewAdapter(getActivity(), repairs);
            txtIsThereRepairs.setText("Repairs");
            rcyRepairsTenant.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
        btnAddRepair.setOnClickListener(onAddRepair);



        return view;
    }

    private View.OnClickListener onAddRepair = new View.OnClickListener(){

        @Override
        public void onClick(View view) {

            routerActionListener.onNavigateToRepairPicture();

        }
    };

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public void setActionListener(TenantRouterAction routerActionListener){
        this.routerActionListener = routerActionListener;
    }

    public void getRepairsFromServer(){

        Network network = new Network();
        network.registerObserver(this);
        network.getRepairs(houseAddress);
    }

    @Override
    public void update(String response) {
        progressDialog.dismiss();
        repairs = new ArrayList<>();
        JSONArray jsonArray;
        if (!response.equals("{'error':'Not such repairs for this house.'}")){
            try {
                jsonArray = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Repair repair = new Repair(
                            jsonObject.getString("Description"),
                            jsonObject.getString("Name"),
                            jsonObject.getString("Date"),
                            jsonObject.getString("Status"),
                            jsonObject.getString("PhotoRef"),
                            jsonObject.getString("DateUpdated"),
                            houseAddress);
                    repairs.add(repair);
                    setRepairs(repairs);
                    Collections.reverse(repairs);
                    routerActionListener.onSetRepairs(repairs);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else{

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RepairRecyclerViewAdapter adapter = new RepairRecyclerViewAdapter(getActivity(), repairs);
                rcyRepairsTenant.setAdapter(adapter);
                adapter.setOnItemClickListener(TenantRepairListFragment.this);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        //i will se the repair in the other view with the routercall.
        //Repair.
        Repair repair = repairs.get(position);
        //routerActionListener.onSetRepairs(repairs);
        routerActionListener.onNavigateToTenantRepairUpdate(repair, position);
        //here i will pass to the router the repair and router will set the view on the router call!!!
    }

    public void setRepairs(List<Repair> repairs){
        this.repairs = repairs;
    }
}
