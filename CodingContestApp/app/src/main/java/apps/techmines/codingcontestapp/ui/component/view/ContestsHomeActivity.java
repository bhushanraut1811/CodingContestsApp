package apps.techmines.codingcontestapp.ui.component.view;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import apps.techmines.codingcontestapp.R;
import apps.techmines.codingcontestapp.service.apiservice.model.Challenge;
import apps.techmines.codingcontestapp.ui.component.presenter.ContestsHomePresenter;
import apps.techmines.codingcontestapp.ui.component.presenter.IContestHomeView;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;
import hotchemi.android.rate.StoreType;


/**
 * Shows all the challenges hosted on different programing platform in View Pager
 */
public class ContestsHomeActivity extends BaseActivity implements IContestHomeView {

    private static final String TAG = ContestsHomeActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ContestsHomePresenter mPresenter;
    private ArrayList<String> mPlatformList = new ArrayList<>();
    private LinkedHashMap<String, ArrayList<Challenge>> mAllChallengesMap = new LinkedHashMap<>();
    private ProgressDialog mProgressDialog;
    private RelativeLayout mParentLayout;
    private TabLayout mTabLayout;
    private Button mBtnReload;
    private ImageView mIvReload;

    //invokes from presenter
    public void doViewPagerSetup() {
        //hide progress bar
        hideProgressDialog(mProgressDialog);
        //if failure ask for do again
        if (mPlatformList.size() == 0) {
            //make retry button layout visible visible
            mAllChallengesMap.clear();
            mPlatformList.clear();
            setReloadAreaVisibility(true);
        } else {
            //make layout invisible
            setReloadAreaVisibility(false);
            // Create the adapter that will return a fragment for each of the three
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            // Set up the ViewPager with the sections adapter.
            if (mViewPager != null) {
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }

            if (mTabLayout != null) {
                mTabLayout.setVisibility(View.VISIBLE);
                mTabLayout.setupWithViewPager(mViewPager);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contests_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initialise views
        initViews();
        //rate my app
        rateMyApp();

        mProgressDialog = new ProgressDialog(this);
        //initialize presenter
        mPresenter = new ContestsHomePresenter(this);
        //check network connection
        if (checkNetworkStatus()) {
            showProgressDialog(mProgressDialog, getResources().getString(R.string.loading));
            //fetch data
            mPresenter.fetchContestData(mAllChallengesMap, mPlatformList);
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
                    mPresenter.fetchContestData(mAllChallengesMap, mPlatformList);
                } else {
                    setReloadAreaVisibility(true);
                    showSnackBar(mParentLayout, getResources().getString(R.string.network_connection));
                }
            }
        });
    }

    protected void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.container);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mParentLayout = (RelativeLayout) findViewById(R.id.rl_home_contest);
        mBtnReload = (Button) findViewById(R.id.btn_reload);
        mIvReload = (ImageView) findViewById(R.id.iv_reload);
    }


    /**
     * Setting pagerAdapter
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(mAllChallengesMap.get(mPlatformList.get((position))));
        }

        @Override
        public int getCount() {
            //dynamic so hitting server based on number of tabs will show data
            return mPlatformList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPlatformList.get(position);
        }
    }

    /**
     * Share this app with others via ACTION_SEND Intent
     */
    private void shareThisApp() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        String shareMessage = "Hey check out this app at: https://play.google.com/store/apps/details?id=" + this.getPackageName();
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contests_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_share:
                shareThisApp();
                return true;

            case R.id.action_rate:
                rateThisApp();
                return true;

            case R.id.action_about_app:
                Intent bookmarkIntent = new Intent(this, AboutAppActivity.class);
                startActivity(bookmarkIntent);
                return true;

            case R.id.action_bookmark:
                Intent aboutAppIntent = new Intent(this, BookmarkActivity.class);
                startActivity(aboutAppIntent);
                return true;

            case R.id.action_platforms:
                Intent platformsIntent = new Intent(this, PlatformsActivity.class);
                startActivity(platformsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void rateThisApp() {

        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }

    private void rateMyApp() {

        AppRate.with(this)
                .setStoreType(StoreType.GOOGLEPLAY) //default is Google, other option is Amazon
                .setInstallDays(7) // default 10, 0 means install day.
                .setLaunchTimes(10) // default 10 times.
                .setRemindInterval(2) // default 1 day.
                .setShowNeverButton(true)
                .setShowLaterButton(true) // default true.
                .setDebug(false) // default false.
                .setCancelable(false) // default false.
                .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                    @Override
                    public void onClickButton(int which) {

                        Log.d("TAG", Integer.toString(which));
                    }
                })
                .setTitle(R.string.new_rate_dialog_title)
                .setTextLater(R.string.new_rate_dialog_later)
                .setTextNever(R.string.new_rate_dialog_never)
                .setTextRateNow(R.string.new_rate_dialog_ok)
                .setMessage("If you are finding this app useful, would you mind taking a moment to rate it? \nIt won't take more than a minute." + "\nThanks for your support.")
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);

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
