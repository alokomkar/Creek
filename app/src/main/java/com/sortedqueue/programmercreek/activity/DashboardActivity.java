package com.sortedqueue.programmercreek.activity;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.DashboardPagerAdapter;
import com.sortedqueue.programmercreek.asynctask.JavaProgramInserter;

import com.sortedqueue.programmercreek.database.CreekUserDB;
import com.sortedqueue.programmercreek.database.firebase.FirebaseDatabaseHandler;
import com.sortedqueue.programmercreek.fragments.DashboardFragment;
import com.sortedqueue.programmercreek.fragments.LanguageFragment;
import com.sortedqueue.programmercreek.interfaces.DashboardNavigationListener;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity implements DashboardNavigationListener {

    //@Bind(R.id.adView)
    //AdView adView;
    @Bind(R.id.dashboardViewPager)
    ViewPager dashboardViewPager;
    @Bind(R.id.dashboardTabLayout)
    TabLayout dashboardTabLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String TAG = getClass().getSimpleName();
    private CreekPreferences creekPreferences;
    private GoogleApiClient mGoogleApiClient;

    private void logDebugMessage(String message) {
        Log.d(TAG, message);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        ButterKnife.bind(this);
        setupToolbar();
        creekPreferences = new CreekPreferences(DashboardActivity.this);
        configureGoogleSignup();
        //adView.setVisibility(View.GONE);

        //initAds();

        dashboardViewPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager(), DashboardActivity.this));
        dashboardTabLayout.setupWithViewPager(dashboardViewPager);
        dashboardTabLayout.getTabAt(0).setIcon(R.drawable.ic_account_box_white_24dp);
        dashboardTabLayout.getTabAt(1).setIcon(R.drawable.ic_dns_white_24dp);
        if( creekPreferences.getProgramLanguage().equals("")) {
            dashboardViewPager.setCurrentItem(0);
        }
        else {
            dashboardViewPager.setCurrentItem(1);
            getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + creekPreferences.getProgramLanguage().toUpperCase());
        }

        dashboardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if( position == 0 ) {
                    LanguageFragment.getInstance().animateViews();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        this.overridePendingTransition(R.anim.anim_slide_in_left,
                R.anim.anim_slide_out_left);
        //initJavaIndex();

    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }



    private void initJavaIndex() {
        creekPreferences.setProgramLanguage("c");
        new JavaProgramInserter(DashboardActivity.this).insertProgramWiki();
    }

    /*private void initAds() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.mobile_banner_id));
        //For actual ads : AdRequest adRequest = new AdRequest.Builder().build();
        //For creating test ads
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("2510529ECB8B5E43FA6416A37C1A6101")
                .build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
                //AuxilaryUtils.displayResultAlert("Result", "Message", DashboardActivity.this);
            }
        });

    }*/


    /**
     * Method to initialize UI.
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.action_about:
                Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_invite:
                tellYourFriends();
                return true;

            case R.id.action_share:
                shareInfo();
                return true;

            case R.id.action_sync:
                LanguageFragment.getInstance().getFirebaseDBVerion();

                return true;
            case R.id.action_log_out :
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                // ...
                            }
                        });
                creekPreferences.clearCacheDetails();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signOut();
                Intent spashIntent = new Intent(DashboardActivity.this, SplashActivity.class);
                startActivity(spashIntent);
                finish();
                return true;


            /*case R.id.action_search:
                Intent searchIntent = new Intent(DashboardActivity.this, ProgramWikiActivity.class);
                startActivity(searchIntent);
                return true;*/

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void configureGoogleSignup() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        finish();
    }


    private void tellYourFriends() {
        //https://developers.facebook.com/docs/sharing/android/
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle("Infinite Programmer")
                .setContentDescription("Check this out : ")
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#InfiniteProgrammer")
                        .build())

                .setContentUrl(Uri.parse(getString(R.string.app_url)))
                .build();
        ShareDialog shareDialog = new ShareDialog(DashboardActivity.this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
    }

    private void shareInfo() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app : \n" + getString(R.string.app_url));
        startActivity(Intent.createChooser(shareIntent, "Share App Info"));
    }

    @Override
    public void navigateToDashboard() {
        dashboardViewPager.setCurrentItem(1);
        DashboardFragment.getInstance().animateViews();
        getSupportActionBar().setTitle(getString(R.string.app_name) + " - " + creekPreferences.getProgramLanguage().toUpperCase());
    }

    @Override
    public void navigateToLanguage() {
        LanguageFragment.getInstance().animateViews();
        dashboardViewPager.setCurrentItem(0);
    }
}
