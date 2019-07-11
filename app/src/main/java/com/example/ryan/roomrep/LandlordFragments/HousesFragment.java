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
import com.example.ryan.roomrep.Classes.Router.RouterActionListener;
import com.example.ryan.roomrep.Classes.Router.SetRouterAction;
import com.example.ryan.roomrep.R;

import java.util.ArrayList;
import java.util.List;

public class HousesFragment extends Fragment implements SetRouterAction {


    Button btnAddHouse;
    RouterActionListener routerActionListener;
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
                routerActionListener.onNavigateToLandlordListings();
            }
        }
    };

    @Override
    public void setRouterAction(RouterActionListener routerActionListener) {
        this.routerActionListener = routerActionListener;
    }

    public void addHouse(House house) {
        this.houses.add(house);
    }


}
