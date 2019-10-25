package com.example.ryan.roomrep.LandlordFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryan.roomrep.Adapters.ItemClickListener;
import com.example.ryan.roomrep.Adapters.RepairRecyclerViewAdapter;
import com.example.ryan.roomrep.Classes.Landlord.Landlord;
import com.example.ryan.roomrep.Classes.Network.FragmentEventListener;
import com.example.ryan.roomrep.Classes.Network.Network;
import com.example.ryan.roomrep.Classes.Repair;
import com.example.ryan.roomrep.Classes.Router.LandlordRouterAction;
import com.example.ryan.roomrep.Classes.Router.TenantRouterAction;
import com.example.ryan.roomrep.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RepairHistoryLandlordFragment extends Fragment implements FragmentEventListener, ItemClickListener {

    LandlordRouterAction routerActionListener;

    RecyclerView rcyRepairsLandlord;

    TextView txtIsThereRepairs;

    private List<Repair> repairs;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_repair_history_landlord, container, false);


        rcyRepairsLandlord = view.findViewById(R.id.rcyRepairsLandlord);

        txtIsThereRepairs = view.findViewById(R.id.txtIsThereRepairsLandlord);

        rcyRepairsLandlord.setLayoutManager(new LinearLayoutManager(getActivity()));


        if(!(repairs == null)){
            txtIsThereRepairs.setText("Repairs");
            RepairRecyclerViewAdapter adapter = new RepairRecyclerViewAdapter(getActivity(), repairs);
            //progressDialog = new ProgressDialog(getActivity());
            //progressDialog.setMessage("Getting all Repairs...");
            //progressDialog.show();

            rcyRepairsLandlord.setAdapter(adapter);
            adapter.setOnItemClickListener(this);
            adapter.notifyDataSetChanged();
        }


        return view;
    }

    public void setRepairs(List<Repair> repairs){
        this.repairs = repairs;
    }


    public void setActionListener(LandlordRouterAction routerActionListener){
        this.routerActionListener = routerActionListener;
    }

    public void getRepairsFromServer() {
        Network network = Network.getInstance();
        network.registerObserver(this);
        network.getRepairs();
    }


    @Override
    public void onItemClick(View view, int position) {
        //i will se the repair in the other view with the routercall.
        //Repair.
        Repair repair = repairs.get(position);
        routerActionListener.onNavigateToLandlordRepairView(repair, position);
        //here i will pass to the router the repair and router will set the view on the router call!!!
    }

    @Override
    public void update(String response) {
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
                            jsonObject.getString("PhotoRef"));
                    repairs.add(repair);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                routerActionListener.onSetRepairs(repairs);
            }
        }
        else{

        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RepairRecyclerViewAdapter adapter = new RepairRecyclerViewAdapter(getActivity(), repairs);

                rcyRepairsLandlord.setAdapter(adapter);
                adapter.setOnItemClickListener(RepairHistoryLandlordFragment.this);
                adapter.notifyDataSetChanged();
            }
        });
        //progressDialog.dismiss();
    }
}
