package com.example.ryan.roomrep.Classes.Router;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;

import com.example.ryan.roomrep.Classes.House.House;
import com.example.ryan.roomrep.Classes.LanguageTranslation;
import com.example.ryan.roomrep.Classes.Repair;
import com.example.ryan.roomrep.Classes.Tenant.Tenant;
import com.example.ryan.roomrep.R;
import com.example.ryan.roomrep.TenantFragments.CompleteRentFragment;
import com.example.ryan.roomrep.TenantFragments.ConfirmRentFragment;
import com.example.ryan.roomrep.TenantFragments.ExpertSystemFragment;
import com.example.ryan.roomrep.TenantFragments.ListTargetChatUserFragment;
import com.example.ryan.roomrep.TenantFragments.ListingsFragment;
import com.example.ryan.roomrep.TenantFragments.MessagRFragment;
import com.example.ryan.roomrep.TenantFragments.PayRentFragment;
import com.example.ryan.roomrep.TenantFragments.RatingLandlordFragment;
import com.example.ryan.roomrep.TenantFragments.RepairPictureFragment;
import com.example.ryan.roomrep.TenantFragments.SearchFragment;
import com.example.ryan.roomrep.TenantFragments.SendRepairFragment;
import com.example.ryan.roomrep.TenantFragments.SlottingTenant;
import com.example.ryan.roomrep.TenantFragments.TenantLandingFragment;
import com.example.ryan.roomrep.TenantFragments.TenantRepairListFragment;
import com.example.ryan.roomrep.TenantFragments.TenantRepairUpdateFragment;


import java.util.ArrayList;
import java.util.List;

public class TenantRouter implements TenantRouterAction {

    FragmentManager fragmentManager;
    List<Repair> repairs;
    Tenant tenant;
    House house;
    Repair repair;

    public TenantRouter(FragmentManager fragmentManager, List<Repair> repairs) {
        this.repairs = repairs;
        this.fragmentManager = fragmentManager;


    }

    public void manageBackstack(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (fragmentManager.findFragmentById(R.id.tenant_container) == null){
            fragmentTransaction.add(R.id.tenant_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else if (fragmentManager.findFragmentById(R.id.tenant_container) != null){
            fragmentTransaction.replace(R.id.tenant_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onSetRepairs(List<Repair> repairs){
        this.repairs = repairs;
    }

    @Override
    public void onNavigateToRatingLandlord(String landlordName) {
        RatingLandlordFragment ratingLandlordFragment = new RatingLandlordFragment();
        ratingLandlordFragment.setLandlordName(landlordName);
        ratingLandlordFragment.setRouterAction(this);
        manageBackstack(ratingLandlordFragment);
    }

    @Override
    public void onNavigateToSlottingLandlord(String landlordEmail) {
        SlottingTenant slottingTenant = new SlottingTenant();
        slottingTenant.setTenant(this.tenant);
        slottingTenant.setLandlordEmail(landlordEmail);
        slottingTenant.setActionListener(this);
        manageBackstack(slottingTenant);

    }



    @Override
    public void onNavigateToTenantLanding(House house, Tenant tenant) {
        TenantLandingFragment tenantLandingFragment = new TenantLandingFragment();
        this.house = house;
        this.tenant = tenant;
        tenantLandingFragment.setHouse(house);
        tenantLandingFragment.setTenant(tenant);
        manageBackstack(tenantLandingFragment);
    }

    @Override
    public void onNavigateToSearch() {
        SearchFragment searchFragment = new SearchFragment();
        //searchFragment.setActionListener(this);
        manageBackstack(searchFragment);
    }


    @Override
    public void onNavigateToListings() {
        ListingsFragment listingsFragment = new ListingsFragment();
        listingsFragment.setHouses(new ArrayList<House>());
        //listingsFragment.setActionListener(this);
        manageBackstack(listingsFragment);
    }

    @Override
    public void onNavigateToViewListings() {
        MessagRFragment messagRFragment = new MessagRFragment();
        messagRFragment.setActionListener(this);
        manageBackstack(messagRFragment);

    }

    @Override
    public void onNavigateToPayRent(Tenant tenant) {
        PayRentFragment payRentFragment = new PayRentFragment();
        payRentFragment.setActionListener(this);
        payRentFragment.setTenant(tenant);
        payRentFragment.setHouse(this.house);
        manageBackstack(payRentFragment);
    }

    @Override
    public void onNavigateToConfirmRent() {
        ConfirmRentFragment confirmRentFragment = new ConfirmRentFragment();
        manageBackstack(confirmRentFragment);
    }

    @Override
    public void onNavigateToCompleteRent() {
        CompleteRentFragment completeRentFragment = new CompleteRentFragment();
        //completeRentFragment.setActionListener(this);
        manageBackstack(completeRentFragment);
    }



    @Override
    public void onNavigateToRepairPicture() {
        RepairPictureFragment repairPictureFragment = new RepairPictureFragment();
        repairPictureFragment.setActionListener(this);
        manageBackstack(repairPictureFragment);
    }

    @Override
    public void onNavigateToExpertSystem() {
        ExpertSystemFragment expertSystemFragment = new ExpertSystemFragment();
        expertSystemFragment.setActionListener(this);
        manageBackstack(expertSystemFragment);
    }

    @Override
    public void onNavigateToTenantRepair() {
        TenantRepairListFragment tenantRepairListFragment = new TenantRepairListFragment();
        //tenantRepairFragment.setActionListener(this);
        manageBackstack(tenantRepairListFragment);
    }

    @Override
    public void onNavigateToMessages() {
        MessagRFragment messagRFragment = new MessagRFragment();
        messagRFragment.setActionListener(this);
        manageBackstack(messagRFragment);

    }

    @Override
    public void onNavigateToMessagesPeopleList(Tenant tenant, House house) {

        ListTargetChatUserFragment listTargetChatUserFragment = new ListTargetChatUserFragment();
        listTargetChatUserFragment.setActionListener(this);
        listTargetChatUserFragment.setHouse(house);
        listTargetChatUserFragment.setTenant(tenant);
        manageBackstack(listTargetChatUserFragment);

    }

    @Override
    public void onNavigateToSendRepair(LanguageTranslation languageTranslation, String languageSelected) {
        SendRepairFragment sendRepairFragment = new SendRepairFragment();
        sendRepairFragment.setLanguageSelected(languageSelected);
        sendRepairFragment.setTenant(tenant);
        sendRepairFragment.setHouseAddress(house.getAddress());
        sendRepairFragment.setLanguageTranslation(languageTranslation);
        sendRepairFragment.setActionListener(this);
        manageBackstack(sendRepairFragment);
    }

    @Override
    public void onAddRepair(Repair repair, Tenant tenant){
        TenantRepairListFragment tenantRepairFragment = new TenantRepairListFragment();
        this.repairs.add(repair);
        tenantRepairFragment.setHouseAddress(house.getAddress());
        tenantRepairFragment.setActionListener(this);
        tenantRepairFragment.setRepairs(repairs);
        manageBackstack(tenantRepairFragment);
    }

    @Override
    public void onNavigateToTenantRepairsList(){
        TenantRepairListFragment tenantRepairListFragment = new TenantRepairListFragment();
        tenantRepairListFragment.setHouseAddress(house.getAddress());
        tenantRepairListFragment.setActionListener(this);
        tenantRepairListFragment.setRepairs(repairs);
        tenantRepairListFragment.getRepairsFromServer();
        manageBackstack(tenantRepairListFragment);
    }

    @Override
    public void onNavigateToTenantRepairsListWithNoRepair(){
        TenantRepairListFragment tenantRepairListFragment = new TenantRepairListFragment();
        tenantRepairListFragment.setActionListener(this);
        tenantRepairListFragment.setRepairs(repairs);
        manageBackstack(tenantRepairListFragment);
    }

    @Override
    public void onNavigateToTenantRepairListWithUpdatedRepair(Repair repair, int position){
        repairs.set(position, repair);
        TenantRepairListFragment tenantRepairListFragment = new TenantRepairListFragment();
        tenantRepairListFragment.setActionListener(this);
        tenantRepairListFragment.setRepairs(repairs);
        manageBackstack(tenantRepairListFragment);
    }

    @Override
    public void onNavigateToTenantRepairUpdate(Repair repair, int position){
        TenantRepairUpdateFragment tenantRepairUpdateFragment = new TenantRepairUpdateFragment();
        tenantRepairUpdateFragment.setActionListener(this);
        tenantRepairUpdateFragment.setRepair(repair, position);
        manageBackstack(tenantRepairUpdateFragment);
    }

    @Override
    public void popBackStack() {
        fragmentManager.popBackStack(fragmentManager.getBackStackEntryCount() - 1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void setTenantAndHouse(Tenant tenant, House house) {
        this.tenant = tenant;
        this.house = house;
    }

}
