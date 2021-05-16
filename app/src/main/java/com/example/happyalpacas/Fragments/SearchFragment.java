package com.example.happyalpacas.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happyalpacas.Club_User;
import com.example.happyalpacas.Club_Users_Adapter;
import com.example.happyalpacas.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private RecyclerView rvClub_User;
    private Club_Users_Adapter adapter;
    private List<Club_User> allClubUsers;
    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvClub_User = view.findViewById(R.id.rvClub_User);
        //Steps to use the recylcer view:
        //0.create layout for one row in the list
        //1.create the adapter
        allClubUsers = new ArrayList<>();
        adapter = new Club_Users_Adapter(getContext(),allClubUsers);
        //2. create data source
        //3. set the adapter on recylcer view
        rvClub_User.setAdapter(adapter);
        //4.set the layout manager
        rvClub_User.setLayoutManager(new LinearLayoutManager(getContext()));
        queryClub_Users();

    }

    private void queryClub_Users(){
        ParseQuery<Club_User> query = ParseQuery.getQuery(Club_User.class);
        query.setLimit(20);
        query.addDescendingOrder(Club_User.KEY_CREATEDAT);
        query.findInBackground(new FindCallback<Club_User>() {
            @Override
            public void done(List<Club_User> clubusers, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting club_users", e);
                    return;
                }
                for( Club_User club_user : clubusers){
                    Log.i(TAG, "ClubUser: " + club_user.getDescription() + "username:" + club_user.getUser());
                }
                allClubUsers.addAll(clubusers);
                adapter.notifyDataSetChanged();
            }
        });

    }
}