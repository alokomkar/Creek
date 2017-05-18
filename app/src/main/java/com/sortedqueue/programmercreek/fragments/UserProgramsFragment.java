package com.sortedqueue.programmercreek.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseError;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.ProgramActivity;
import com.sortedqueue.programmercreek.activity.ProgramListActivity;
import com.sortedqueue.programmercreek.adapter.UserProgramRecyclerAdapter;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.UserProgramDetails;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.R.id.addCodeFAB;

/**
 * Created by Alok on 16/05/17.
 */

public class UserProgramsFragment extends Fragment implements View.OnClickListener, UserProgramRecyclerAdapter.UserProgramClickListener, FirebaseDatabaseHandler.GetAllUserProgramsListener {

    private static UserProgramsFragment instance;
    @Bind(R.id.userProgramsRecyclerView)
    RecyclerView userProgramsRecyclerView;
    @Bind(R.id.allProgramsRadioButton)
    RadioButton allProgramsRadioButton;
    @Bind(R.id.myProgramsRadioButton)
    RadioButton myProgramsRadioButton;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.noProgramsLayout)
    LinearLayout noProgramsLayout;
    private UserProgramRecyclerAdapter adapter;
    private String accessSpecifier;
    private DashboardNavigationListener dashboardNavigationListener;


    public static UserProgramsFragment getInstance() {
        if (instance == null) {
            instance = new UserProgramsFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_programs, container, false);
        ButterKnife.bind(this, view);
        allProgramsRadioButton.setChecked(true);
        allProgramsRadioButton.setOnCheckedChangeListener(checkChangedListener);
        myProgramsRadioButton.setOnCheckedChangeListener(checkChangedListener);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        // Setup refresh listener which triggers new data loading
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchUserPrograms(accessSpecifier);
            }
        });
        fetchUserPrograms("All programs");
        return view;
    }


    private CompoundButton.OnCheckedChangeListener checkChangedListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.allProgramsRadioButton:
                    if (allProgramsRadioButton.isChecked()) {
                        fetchUserPrograms("All Programs");
                    }
                    break;
                case R.id.myProgramsRadioButton:
                    if (myProgramsRadioButton.isChecked()) {
                        fetchUserPrograms("My Programs");
                    }
                    break;
            }
        }
    };

    private void fetchUserPrograms(String accessSpecifier) {
        this.accessSpecifier = accessSpecifier;
        swipeRefreshLayout.setRefreshing(true);
        new FirebaseDatabaseHandler(getContext()).getAllUserPrograms(accessSpecifier, this);
    }

    private void setupRecyclerView(ArrayList<UserProgramDetails> presentationModelArrayList) {
        userProgramsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserProgramRecyclerAdapter(getContext(), presentationModelArrayList, this);
        userProgramsRecyclerView.setAdapter(adapter);
        noProgramsLayout.setVisibility(View.GONE);
        userProgramsRecyclerView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        dashboardNavigationListener.importCodeFile();
    }

    @Override
    public void onSuccess(ArrayList<UserProgramDetails> userProgramDetailsArrayList) {
        setupRecyclerView(userProgramDetailsArrayList);
    }

    @Override
    public void onError(DatabaseError databaseError) {
        swipeRefreshLayout.setRefreshing(false);
        noProgramsLayout.setVisibility(View.VISIBLE);
        userProgramsRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(int position) {

        UserProgramDetails userProgramDetails = adapter.getItemAtPosition(position);
        userProgramDetails.setViews(userProgramDetails.getViews() + 1);
        adapter.notifyDataSetChanged();

        new FirebaseDatabaseHandler(getContext()).updateViewCount(userProgramDetails);
        Bundle newIntentBundle = new Bundle();
        Intent newIntent = null;
        newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);
        newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, userProgramDetails.getProgramIndex());
        newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 1);
        newIntentBundle.putString(ProgrammingBuddyConstants.KEY_PROG_TITLE, userProgramDetails.getProgramIndex().getProgram_Description());
        newIntentBundle.putParcelableArrayList(ProgrammingBuddyConstants.KEY_USER_PROGRAM, userProgramDetails.getProgramTables());
        newIntent = new Intent(getContext(), ProgramActivity.class);
        newIntent.putExtras(newIntentBundle);
        startActivity(newIntent);

    }

    @Override
    public void onLikeClicked(boolean isLiked, int position) {
        UserProgramDetails userProgramDetails = adapter.getItemAtPosition(position);
        new FirebaseDatabaseHandler(getContext()).updateLikes(isLiked, userProgramDetails);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if( context instanceof DashboardNavigationListener ) {
            dashboardNavigationListener = (DashboardNavigationListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dashboardNavigationListener = null;
    }
}