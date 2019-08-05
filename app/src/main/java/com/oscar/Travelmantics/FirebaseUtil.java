package com.oscar.Travelmantics;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oscar.Travelmantics.model.AdminModel;
import com.oscar.Travelmantics.model.DealModel;

import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {
    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
   // public static ArrayList<TravelDeal> mDeals;
    private static final int RC_SIGN_IN = 123;
    private static Fragment caller;
    private FirebaseUtil(){};
    public static boolean isAdmin;


    public static void openFbReference(String ref, final Fragment callerActivity) {
       // if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;

if (mFirebaseAuth.getCurrentUser()==null){
    signIn();
}
            /*mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null) {
                        FirebaseUtil.signIn();
                        Log.e("fstatu","user is null");
                    }
                    else {
                        Log.e("fstatu","user is not null"+firebaseAuth.getUid());
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                    Toast.makeText(callerActivity.getActivity(), "Welcome back!", Toast.LENGTH_LONG).show();
                }
            };*/

       //}

       // mDeals = new ArrayList<TravelDeal>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }

    private static void signIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }
public static void addAdmin(String uuid, AdminModel adminModel){
    DatabaseReference ref = mFirebaseDatabase.getReference().child("administrators");
    ref.child(uuid).setValue(adminModel);
}
public static void addDeal(DealModel deal){
    mFirebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = mFirebaseDatabase.getReference().child("traveldeals").push();
    ref.setValue(deal);
}
    public static void checkAdmin(String uid) {
        FirebaseUtil.isAdmin=false;
       // boolean kk=mFirebaseDatabase.getReference().child("administrators")
        DatabaseReference ref = mFirebaseDatabase.getReference().child("administrators").child(uid);
               // .equalTo(uid);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUtil.isAdmin=true;
               // UserFragment.showMenu();
                new UserFragment().setHasOptionsMenu(true);
                new UserFragment().setMenu();//.setMenuVisibility(true);

                Log.e("admin","is admin");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(listener);
    }

    public static void attachListener() {
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public static void detachListener() {
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }

}

