package com.sortedqueue.programmercreek.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.activity.InterviewActivity;
import com.sortedqueue.programmercreek.adapter.InterviewQuestionsAdapter;
import com.sortedqueue.programmercreek.asynctask.SlideContentReaderTask;
import com.sortedqueue.programmercreek.database.InterviewQuestionModel;
import com.sortedqueue.programmercreek.database.OptionModel;
import com.sortedqueue.programmercreek.util.SimpleItemTouchHelperCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_MULTIPLE_RIGHT;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_REARRANGE;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_SINGLE_RIGHT;
import static com.sortedqueue.programmercreek.constants.InterviewQuestionConstants.TYPE_TRUE_FALSE;

/**
 * Created by Alok Omkar on 2017-03-08.
 */

public class InterviewQuestionsFragment extends Fragment implements SlideContentReaderTask.OnDataReadListener {

    @BindView(R.id.questionTextView)
    TextView questionTextView;
    @BindView(R.id.questionCardView)
    CardView questionCardView;
    @BindView(R.id.optionsRecyclerView)
    RecyclerView optionsRecyclerView;
    @BindView(R.id.questionLayout)
    LinearLayout questionLayout;
    @BindView(R.id.lifeLine1ImageView)
    ImageView lifeLine1ImageView;
    @BindView(R.id.lifeLine2ImageView)
    ImageView lifeLine2ImageView;
    @BindView(R.id.lifeLine3ImageView)
    ImageView lifeLine3ImageView;
    @BindView(R.id.lifeLineLayout)
    RelativeLayout lifeLineLayout;
    @BindView(R.id.progressTextView)
    TextView progressTextView;

    private ArrayList<InterviewQuestionModel> interviewQuestionModels;
    private InterviewQuestionModel interviewQuestionModel;
    private InterviewQuestionsAdapter interviewQuestionsAdapter;
    private CountDownTimer mCountDownTimer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interview_questions, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        interviewQuestionModels = new ArrayList<>();
        getAllInterviewModels( programLanguage );

    }

    private int index = 0;
    private void getAllInterviewModels(String programLanguage) {
        /*setupMultiRightModel();
        setupRearrangeModel();
        setupSingleRightModel();
        setupTrueFalseModel();*/
        String fileId = "c_questions";
        new SlideContentReaderTask(getActivity(), fileId, this).execute();


    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if( progressTextView != null )
                    progressTextView.setText( "Remaining : " + (int) (millisUntilFinished / 1000) );
            }

            @Override
            public void onFinish() {
                if( progressTextView != null )
                    progressTextView.setText("Time up");
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }

    private void setupMultiRightModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_MULTIPLE_RIGHT);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "int a = 1;"));
        optionModels.add(new OptionModel(index++, "char b = \"ava\""));
        optionModels.add(new OptionModel(index++, "char[] str = \"abcd\""));
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Which are valid?");
        ArrayList<Integer> correctOptions = new ArrayList<>();
        correctOptions.add(1);
        correctOptions.add(3);
        interviewQuestionModel.setCorrectOptions(correctOptions);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void setupRearrangeModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_REARRANGE);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "void main() {"));
        optionModels.add(new OptionModel(index++, " puts(s);"));
        optionModels.add(new OptionModel(index++, " int s = 0;"));
        optionModels.add(new OptionModel(index++, "}"));
        ArrayList<Integer> correctSequence = new ArrayList<>();
        correctSequence.add(1);
        correctSequence.add(3);
        correctSequence.add(2);
        correctSequence.add(4);
        interviewQuestionModel.setCorrectSequence(correctSequence);
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Arrange in right sequence");
        interviewQuestionModel.setCorrectOption(1);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void setupSingleRightModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_SINGLE_RIGHT);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "True"));
        optionModels.add(new OptionModel(index++, "False"));
        optionModels.add(new OptionModel(index++, "No Idea"));
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Is it True that !true is false?");
        interviewQuestionModel.setCorrectOption(1);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void setupTrueFalseModel() {
        interviewQuestionModel = new InterviewQuestionModel();
        interviewQuestionModel.setTypeOfQuestion(TYPE_TRUE_FALSE);
        ArrayList<OptionModel> optionModels = new ArrayList<>();
        int index = 1;
        optionModels.add(new OptionModel(index++, "True"));
        optionModels.add(new OptionModel(index++, "False"));
        interviewQuestionModel.setOptionModels(optionModels);
        interviewQuestionModel.setQuestion("Is it True that !true is false?");
        interviewQuestionModel.setCorrectOption(1);
        interviewQuestionModel.setModelId("Model_1");
        interviewQuestionModel.setProgramLanguage("c");
        interviewQuestionModels.add(interviewQuestionModel);
    }

    private void hideShowLifeLine() {
        int visibility = interviewQuestionModel.getOptionModels().size() == 2
                ? View.GONE
                : View.VISIBLE;
        lifeLineLayout.setVisibility(visibility);
    }

    private void setupViews() {
        questionTextView.setText(interviewQuestionModel.getQuestion());
    }

    private void setupRecyclerView() {
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        interviewQuestionsAdapter = new InterviewQuestionsAdapter(interviewQuestionModel);
        optionsRecyclerView.setAdapter(interviewQuestionsAdapter);
        if (interviewQuestionModel.getTypeOfQuestion() == TYPE_REARRANGE) {
            ItemTouchHelper.Callback callback =
                    new SimpleItemTouchHelperCallback(interviewQuestionsAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(optionsRecyclerView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void checkAnswer() {
        interviewQuestionsAdapter.isAnswerChecked(true);
        cancelTimer();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNext();
            }
        }, 2500);
    }

    private void navigateToNext() {
        if( index < interviewQuestionModels.size() ) {
            interviewQuestionModel = interviewQuestionModels.get(index++);
            setupViews();
            setupRecyclerView();
            hideShowLifeLine();
        }
        startTimer();
    }

    private void cancelTimer() {
        if( mCountDownTimer != null ) {
            mCountDownTimer.cancel();
        }
    }

    private String programLanguage;
    public void setProgramLanguage(String programLanguage) {
        this.programLanguage = programLanguage;
    }

    @Override
    public void onDataReadComplete(ArrayList<InterviewQuestionModel> contentArrayList) {
        navigateToNext();
    }
}