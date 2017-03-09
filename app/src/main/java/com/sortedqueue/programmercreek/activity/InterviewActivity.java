package com.sortedqueue.programmercreek.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.fragments.ChaptersFragment;
import com.sortedqueue.programmercreek.fragments.InterviewQuestionsFragment;
import com.sortedqueue.programmercreek.util.AnimationUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewActivity extends AppCompatActivity {

    @Bind(R.id.container)
    FrameLayout container;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview);
        ButterKnife.bind(this);
        loadInterviewQuestionsFragment();
    }

    private void loadInterviewQuestionsFragment() {

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        InterviewQuestionsFragment interviewQuestionsFragment = (InterviewQuestionsFragment) getSupportFragmentManager().findFragmentByTag(InterviewQuestionsFragment.class.getSimpleName());
        if (interviewQuestionsFragment == null) {
            interviewQuestionsFragment = new InterviewQuestionsFragment();
        }

        mFragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right, R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
        mFragmentTransaction.replace(R.id.container, interviewQuestionsFragment, InterviewQuestionsFragment.class.getSimpleName());
        mFragmentTransaction.commit();
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}