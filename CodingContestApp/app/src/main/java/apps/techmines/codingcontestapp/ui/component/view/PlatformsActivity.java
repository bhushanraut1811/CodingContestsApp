package apps.techmines.codingcontestapp.ui.component.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.apiservice.model.Platform;
import apps.techmines.codingcontestapp.ui.adapter.PlatformListAdapter;
import apps.techmines.codingcontestapp.ui.component.presenter.IPlatformsView;
import apps.techmines.codingcontestapp.ui.component.presenter.PlatformsPresenter;

/**
 * Shows all th coding platforms
 */
public class PlatformsActivity extends BaseActivity implements IPlatformsView {

    private PlatformListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Platform> mPlatformList = new ArrayList<>();
    private PlatformsPresenter mPresenter;
    private RelativeLayout mParentLayout;
    private ProgressDialog mProgressDialog;
    private Button mBtnReload;
    private ImageView mIvReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platforms);
        //initialise views
        initViews();
        mPresenter = new PlatformsPresenter(this);
        //check network connection
        if (checkNetworkStatus()) {
            showProgressDialog(mProgressDialog, getResources().getString(R.string.loading));
            mPresenter.fetchPlatforms(mPlatformList);
        } else {
            setReloadAreaVisibility(true);
            showSnackBar(mParentLayout, getResources().getString(R.string.network_connection));
        }

        //button listener for reload
        mBtnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReloadAreaVisibility(false);
                if (checkNetworkStatus()) {
                    showProgressDialog(mProgressDialog, getResources().getString(R.string.loading));
                    mPresenter.fetchPlatforms(mPlatformList);
                } else {
                    setReloadAreaVisibility(true);
                    showSnackBar(mParentLayout, getResources().getString(R.string.network_connection));
                }
            }
        });

    }

    @Override
    public void populateListView() {
        //hide progress
        hideProgressDialog(mProgressDialog);

        if (mPlatformList.size() == 0) {
            //make retry button layout visible visible
            mPlatformList.clear();
            setReloadAreaVisibility(true);
        } else {
            mAdapter = new PlatformListAdapter(this, mPlatformList, new PlatformListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.parse(mPlatformList.get(position).getPlatformUrl());
                    viewIntent.setData(uri);
                    startActivity(viewIntent);
                }
            });

            RecyclerView.LayoutManager layoutManager =
                    new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(mAdapter);
        }
    }


    protected void initViews() {
        setUpHomeEnabled();
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_platform_list);
        mParentLayout = (RelativeLayout) findViewById(R.id.rl_platforms);
        mBtnReload = (Button) findViewById(R.id.btn_reload);
        mIvReload = (ImageView) findViewById(R.id.iv_reload);
        mProgressDialog = new ProgressDialog(this);
    }

    private void setReloadAreaVisibility(boolean visible) {
        if (visible) {
            mBtnReload.setVisibility(View.VISIBLE);
            mIvReload.setVisibility(View.VISIBLE);
        } else {
            mBtnReload.setVisibility(View.GONE);
            mIvReload.setVisibility(View.GONE);
        }
    }
}
