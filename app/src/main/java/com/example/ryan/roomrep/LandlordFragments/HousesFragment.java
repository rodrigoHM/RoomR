package com.example.ryan.roomrep.LandlordFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ryan.roomrep.Adapters.HouseRecyclerviewAdapter;
import com.example.ryan.roomrep.Classes.House.House;
import com.example.ryan.roomrep.Classes.Landlord.Landlord;
import com.example.ryan.roomrep.Classes.Network.HouseMainListener;
import com.example.ryan.roomrep.Classes.Network.Network;
import com.example.ryan.roomrep.Classes.Router.LandlordRouterAction;
import com.example.ryan.roomrep.MainActivityLandlord;
import com.example.ryan.roomrep.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HousesFragment extends Fragment implements HouseMainListener {


    Button btnAddHouse;

    LandlordRouterAction routerActionListener;

    Button btnViewListings;

    RecyclerView rcyHouses;

    private List<House> houses = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house, container, false);

        btnAddHouse = view.findViewById(R.id.btnHousesAddHouse);
        rcyHouses = view.findViewById(R.id.rcyHouses);
        btnViewListings = view.findViewById(R.id.btnHousesViewListings);

        Landlord landlord = ((MainActivityLandlord)getActivity()).getLandlord();

        Network network = new Network();
        network.registerHouseListener(this);
        network.getLandlordHouses();


        rcyHouses.setLayoutManager(new LinearLayoutManager(getActivity()));

        HouseRecyclerviewAdapter adapter = new HouseRecyclerviewAdapter(getActivity(), houses);

        rcyHouses.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        btnAddHouse.setOnClickListener(onAddHouse);

        btnViewListings.setOnClickListener(onViewListings);

        return view;
    }

    private View.OnClickListener onAddHouse = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (routerActionListener != null){
                routerActionListener.onNavigateToHousesAdd();
            }
        }
    };


    private View.OnClickListener onViewListings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (routerActionListener != null){
                routerActionListener.onNavigateToLandlordListings(houses);
            }
        }
    };

    public void setRouterAction(LandlordRouterAction routerActionListener) {
        this.routerActionListener = routerActionListener;
    }

    public void addHouse(House house) {
        this.houses.add(house);

    }



    @Override
    public void onGetHouses(JSONArray response) {
        Gson gson = new Gson();
        final ArrayList<House> houses = new ArrayList<>();
        for (int i = 0; i < response.length(); i++) {
            try {
                houses.add(gson.fromJson(response.get(i).toString(), House.class));
                houses.get(i).setUrl(response.getJSONObject(i).getString("image"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HouseRecyclerviewAdapter adapter = new HouseRecyclerviewAdapter(getActivity(), houses);
                rcyHouses.swapAdapter(adapter, false);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
