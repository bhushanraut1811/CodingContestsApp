
package apps.techmines.codingcontestapp.ui.component.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.apiservice.model.Challenge;
import apps.techmines.codingcontestapp.ui.adapter.ContestListAdapter;


/**
 * A placeholder fragment containing  view.
 */
public class PlaceholderFragment extends Fragment {

    /**
     * The fragment argument representing the list for this
     * fragment.
     */
    private static final String ARG_CHALLENGES_LIST = "list";

    private RecyclerView mRecyclerView;


    private ContestListAdapter mAdapter;

    /**
     * empty constructor
     */
    public PlaceholderFragment() {
    }

    /**
     * initialises view elements
     * @param view
     */
    private void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.lv_challenge_list);
    }

    /**
     * Returns a new instance of this fragment
     */
    public static PlaceholderFragment newInstance(ArrayList<Challenge> list) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHALLENGES_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contests_home, container, false);
        initViews(rootView);
        setUpList();
        return rootView;
    }

    private void setUpList() {

        final ArrayList<Challenge> list = (ArrayList<Challenge>) getArguments().getSerializable(ARG_CHALLENGES_LIST);
        mAdapter = new ContestListAdapter(getContext(), list, new ContestListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(list.get(position).getChallengeUrl());
                viewIntent.setData(uri);
                startActivity(viewIntent);
                //Toast.makeText(getContext(), "CLICKED", Toast.LENGTH_SHORT).show();
            }
        });
        //setting layout Manager and adapter to recyclerView
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
