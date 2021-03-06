package com.example.ryan.roomrep.Classes.Router;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.ryan.roomrep.Classes.House.House;
import com.example.ryan.roomrep.Classes.Landlord.Landlord;
import com.example.ryan.roomrep.Classes.Network.Network;
import com.example.ryan.roomrep.Classes.Repair;
import com.example.ryan.roomrep.Classes.RepairContact;
import com.example.ryan.roomrep.LandlordFragments.AddHouseFragment;
import com.example.ryan.roomrep.LandlordFragments.ContactRepairmanFragment;
import com.example.ryan.roomrep.LandlordFragments.HousesFragment;
import com.example.ryan.roomrep.LandlordFragments.LandlordListingsFragment;
import com.example.ryan.roomrep.LandlordFragments.MessageLandlordFragment;
import com.example.ryan.roomrep.LandlordFragments.NotificationOptionsFragment;
import com.example.ryan.roomrep.LandlordFragments.NotifyRFragment;
import com.example.ryan.roomrep.LandlordFragments.RatingTenantFragment;
import com.example.ryan.roomrep.LandlordFragments.RepairHistoryLandlordFragment;
import com.example.ryan.roomrep.LandlordFragments.RepairNotificationLandlordFragment;
import com.example.ryan.roomrep.LandlordFragments.RepairViewLandlordFragment;
import com.example.ryan.roomrep.LandlordFragments.SearchTenantFragment;
import com.example.ryan.roomrep.LandlordFragments.ShowTenantFragment;
import com.example.ryan.roomrep.LandlordFragments.SlottingLandlord;
import com.example.ryan.roomrep.LandlordFragments.TenantProfilesFragment;
import com.example.ryan.roomrep.R;

import java.util.ArrayList;
import java.util.List;

public class LandlordRouter implements LandlordRouterAction {


    private FragmentManager fragmentManager;


    private List<House> houses;
    private Landlord landlord;
    private List<Repair> repairs = new ArrayList<>();
    private List<RepairContact> repairContacts = new ArrayList<>();


    public LandlordRouter(FragmentManager fragmentManager, List<House> houses){
        this.fragmentManager = fragmentManager;
        this.houses = houses;
    }


    public void manageBackstack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragmentManager.findFragmentById(R.id.fragment_container) == null){
            fragmentTransaction.add(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (fragmentManager.findFragmentById(R.id.fragment_container) != null){
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onSetRepairmans(List<RepairContact> repairContacts){
        this.repairContacts = repairContacts;
    }

    @Override
    public void onNavigateToRatingTenant(String tenantName) {
        RatingTenantFragment ratingTenantFragment = new RatingTenantFragment();
        ratingTenantFragment.setLandlord(this.landlord);
        ratingTenantFragment.initNetwork();
        ratingTenantFragment.setTenantName(tenantName);
        ratingTenantFragment.setRouterAction(this);
        manageBackstack(ratingTenantFragment);

    }

    @Override
    public void onNavigateToSlottingTenant( String tenantName) {
        SlottingLandlord slottingLandlord = new SlottingLandlord();
        slottingLandlord.setLandlord(this.landlord);
        slottingLandlord.setTenantName(tenantName);
        slottingLandlord.initNetwork();
        slottingLandlord.setActionListener(this);
        manageBackstack(slottingLandlord);
    }

    @Override
    public void onSetRepairs(List<Repair> repairs){
        this.repairs = repairs;
    }

    @Override
    public void onNavigateToHousesAdd(Landlord landlord) {
        AddHouseFragment addHouseFragment = new AddHouseFragment();
        addHouseFragment.setRouterAction(this);
        addHouseFragment.setLandlord(landlord);
        manageBackstack(addHouseFragment);
    }

    @Override
    public void onAddHouse(House house) {
        HousesFragment housesFragment = new HousesFragment();
        housesFragment.setRouterAction(this);
        housesFragment.setLandlord(this.landlord);
        this.houses.add(house);
        housesFragment.setHouses(this.houses);
        manageBackstack(housesFragment);
    }

    @Override
    public void onNavigateToHouses(Landlord landlord) {
        HousesFragment housesFragment = new HousesFragment();
        this.landlord = landlord;
        housesFragment.setRouterAction(this);
        housesFragment.setLandlord(landlord);
        housesFragment.setHouses(this.houses);
        manageBackstack(housesFragment);
    }


    @Override
    public void onNavigateToLandlordListings(Landlord landlord) {
        LandlordListingsFragment landlordListingsFragment = new LandlordListingsFragment();
        landlordListingsFragment.setLandlord(landlord);
        landlordListingsFragment.setHouses(this.houses);
        landlordListingsFragment.setRouterAction(this);
        manageBackstack(landlordListingsFragment);
    }

    @Override
    public void onNavigateToTenantProfile(House house) {
        TenantProfilesFragment tenantProfilesFragment = new TenantProfilesFragment();
        tenantProfilesFragment.setHouse(house);
        manageBackstack(tenantProfilesFragment);
    }

    @Override
    public void onNaviagateToAddTenant() {
        ShowTenantFragment showTenantFragment = new ShowTenantFragment();
        showTenantFragment.setRouterAction(this);
        manageBackstack(showTenantFragment);
    }

    @Override
    public void onNaviagateToSearchTenant(House house) {
        SearchTenantFragment searchTenantFragment = new SearchTenantFragment();
        searchTenantFragment.setHouse(house);
        searchTenantFragment.setRouterAction(this);
       manageBackstack(searchTenantFragment);
    }

    @Override
    public void onNavigateToMessagePage() {
        MessageLandlordFragment messageLandlordFragment = new MessageLandlordFragment();
        messageLandlordFragment.setRouterAction(this);
        manageBackstack(messageLandlordFragment);
    }

    @Override
    public void onNavigateToShowTenant(House house) {
        ShowTenantFragment showTenantFragment = new ShowTenantFragment();
        showTenantFragment.setHouse(house);
        showTenantFragment.setLandlord(this.landlord);
        showTenantFragment.setRouterAction(this);
        manageBackstack(showTenantFragment);
    }



    @Override
    public void onNavigateToLandlordRepairView(Repair repair, int position){
        RepairViewLandlordFragment repairViewLandlordFragment = new RepairViewLandlordFragment();
        repairViewLandlordFragment.setActionListener(this);
        repairViewLandlordFragment.setRepair(repair, position);
        manageBackstack(repairViewLandlordFragment);
    }

    @Override
    public void onNavigateToLandlordRepairs(){
        RepairHistoryLandlordFragment repairHistoryLandlordFragment = new RepairHistoryLandlordFragment();
        repairHistoryLandlordFragment.setActionListener(this);
        repairHistoryLandlordFragment.setHouses(houses);
        repairHistoryLandlordFragment.setRepairs(repairs);
        manageBackstack(repairHistoryLandlordFragment);
    }

    @Override
    public void onNavigateToLandlordRepairsWithNoUpdate(){
        RepairHistoryLandlordFragment repairHistoryLandlordFragment = new RepairHistoryLandlordFragment();
        repairHistoryLandlordFragment.setActionListener(this);
        repairHistoryLandlordFragment.setHouses(houses);
        repairHistoryLandlordFragment.setRepairs(repairs);
        manageBackstack(repairHistoryLandlordFragment);
    }

    @Override
    public void onNavigateToLandlordRepairsWithUpdate(Repair repair, int position){
        repairs.set(position, repair);
        RepairHistoryLandlordFragment repairHistoryLandlordFragment = new RepairHistoryLandlordFragment();
        repairHistoryLandlordFragment.setActionListener(this);
        repairHistoryLandlordFragment.setHouses(houses);
        repairHistoryLandlordFragment.setRepairs(repairs);
        manageBackstack(repairHistoryLandlordFragment);
    }

    @Override
    public void onNavigateToNotifyR() {
        NotifyRFragment notifyRFragment = new NotifyRFragment();
        notifyRFragment.setLandlord(this.landlord);
        notifyRFragment.setHouse(this.houses);
        notifyRFragment.initNetwork();
        manageBackstack(notifyRFragment);
    }

    @Override
    public void onNavigateToNotifyrOptions(){
        NotificationOptionsFragment notificationOptionsFragment = new NotificationOptionsFragment();
        notificationOptionsFragment.setActionListener(this);
        manageBackstack(notificationOptionsFragment);
    }

    @Override
    public void onNavigateToContactRepairman(String address, String category){
        ContactRepairmanFragment contactRepairmanFragment = new ContactRepairmanFragment();
        contactRepairmanFragment.setActionListener(this);
        contactRepairmanFragment.setRepairContacts(repairContacts);
        contactRepairmanFragment.getRepairmansFromServer(address, category);
        manageBackstack(contactRepairmanFragment);

    }

    @Override
    public void onNavigateToNotifyRepairs(){
        RepairNotificationLandlordFragment repairNotificationLandlordFragment = new RepairNotificationLandlordFragment();
        repairNotificationLandlordFragment.setActionListener(this);
        repairNotificationLandlordFragment.setHouses(houses);
        repairNotificationLandlordFragment.setRepairs(repairs);
        manageBackstack(repairNotificationLandlordFragment);

    }
    @Override
    public void popBackStack() {
        fragmentManager.popBackStack(fragmentManager.getBackStackEntryCount() - 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void updateHouses(List<House> houses) {
        this.houses = houses;
    }

}
