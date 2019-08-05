package com.oscar.Travelmantics;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.oscar.Travelmantics.model.AdminModel;
import com.oscar.Travelmantics.model.DealModel;
import com.oscar.Travelmantics.model.DealViewHolder;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
private int RC_SIGN_IN=123;
RecyclerView rcvDeal;
    protected static final Query sDealQuery =
            FirebaseDatabase.getInstance().getReference().child("traveldeals");

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        rcvDeal=view.findViewById(R.id.rcv_deal);
        rcvDeal.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseUtil.openFbReference("traveldeals",this);
        setHasOptionsMenu(true);
        //TRYING TO CHECK IF ITS ADMIN
        /*try{
           String UUID= FirebaseAuth.getInstance().getUid();
           FirebaseUtil.checkAdmin(UUID);
           if (FirebaseUtil.isAdmin) {
               setHasOptionsMenu(true);
               Log.e("isadmin","confirmed is admin");
           }
           else {
               Log.e("isadmin"," not confirmed is admin");
           }
        }
        catch (Exception e){

        }*/
        populateRecyclerView();


        return  view;
    }
public void setMenu(){
        setHasOptionsMenu(true);
}
    private void populateRecyclerView() {
        FirebaseRecyclerOptions<DealModel> options =
                new FirebaseRecyclerOptions.Builder<DealModel>()
                        .setQuery(sDealQuery, DealModel.class)
                        .setLifecycleOwner(this)
                        .build();
        FirebaseRecyclerAdapter firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<DealModel, DealViewHolder>(options) {
            @NonNull
            @Override
            public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new DealViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.deal_layout,parent,false));
            }

            @Override
            protected void onBindViewHolder(@NonNull DealViewHolder dealViewHolder, int i, @NonNull DealModel dealModel) {
                dealViewHolder.txtPrice.setText(dealModel.getPrice());
                dealViewHolder.txtDescription.setText(dealModel.getDealDescription());
                dealViewHolder.txtTitle.setText(dealModel.getDealTitle());
                Glide.with(getContext()).load(dealModel.getImageUrl()).into(dealViewHolder.imgDeal);
               // Log.e("data",dealModel.getDealDescription()+"if null");
            }

        };

           rcvDeal.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        //MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_admin) {
            ((MainActivity)getActivity()).replaceFragment(new AdminFragment());
             return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response1 = IdpResponse.fromResultIntent(data);
        /*Log.e("fstatus","firestatus"+response1.getEmail());
        Log.e("fstatus","firestatus");*/
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            Log.e("fstatus","firestatus"+response.getEmail());
           String UID= FirebaseAuth.getInstance().getUid();
           //User users=response.getUser();


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.e("fstatus","firestatus"+user);
               /* String UniqueId= FirebaseAuth.getInstance().getUid();
                AdminModel adminModel = new AdminModel(user.getEmail(),user.getPhoneNumber(),user.getDisplayName());
                FirebaseUtil.addAdmin(UniqueId,adminModel);
             */
               FirebaseUtil.checkAdmin(FirebaseAuth.getInstance().getUid());
               if (FirebaseUtil.isAdmin){
                   ((MainActivity)getActivity()).replaceFragment(new AdminFragment());
               }
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                Log.e("fstatus","firestatus error");
            }
        }
    }
    public void showMenu() {
        getActivity().invalidateOptionsMenu();
    }

}
