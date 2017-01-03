package com.sortedqueue.programmercreek.activity;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.sortedqueue.programmercreek.R;
import com.sortedqueue.programmercreek.adapter.CustomProgramLineListAdapter;
import com.sortedqueue.programmercreek.asynctask.ProgramFetcherTask;
import com.sortedqueue.programmercreek.constants.ProgrammingBuddyConstants;
import com.sortedqueue.programmercreek.database.Program_Index;
import com.sortedqueue.programmercreek.database.Program_Table;
import com.sortedqueue.programmercreek.database.handler.DatabaseHandler;
import com.sortedqueue.programmercreek.database.operations.DataBaseInsertAsyncTask;
import com.sortedqueue.programmercreek.interfaces.UIProgramFetcherListener;
import com.sortedqueue.programmercreek.interfaces.UIUpdateListener;
import com.sortedqueue.programmercreek.util.AuxilaryUtils;
import com.sortedqueue.programmercreek.util.CreekPreferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProgramActivity extends AppCompatActivity implements UIUpdateListener {

	ArrayList<String> mProgramList = new ArrayList<String>();
	ArrayList<String> mProgramExplanationList = new ArrayList<String>();

	//ArrayAdapter<String> mAdapterProgramList;
	CustomProgramLineListAdapter mAdapterProgramList;
	CustomProgramLineListAdapter mAdapterProgramExplanationList;

	List<Program_Table> mProgram_TableList;

	ListView mProgramListView;
	ListView mProgramExplanationListView;

	int mProgramIndex;
	Button mProgDescriptionBtn;
	ImageButton mNextProgramBtn;
	ImageButton mPrevProgramBtn;
	Program_Index program_index;

	int mListPostion = 0;
	DatabaseHandler mDatabaseHandler;
	boolean mWizard = false;
	String mProgram_Title = null;
	private int mTotalPrograms;
	//gesture detector
	/**
	 * http://code.tutsplus.com/tutorials/android-sdk-detecting-gestures--mobile-21161
	 * */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mDatabaseHandler = new DatabaseHandler(this);
		Bundle newProgramActivityBundle = getIntent().getExtras();
		program_index = (Program_Index) newProgramActivityBundle.get(ProgrammingBuddyConstants.KEY_PROG_ID);
		mProgramIndex = program_index.getIndex();
		mTotalPrograms = newProgramActivityBundle.getInt(ProgrammingBuddyConstants.KEY_TOTAL_PROGRAMS, 0);
		this.mWizard = newProgramActivityBundle.getBoolean(ProgramListActivity.KEY_WIZARD);
		//boolean modules = newProgramActivityBundle.getBoolean(DashboardActivity.KEY_MODULE_LIST);

		mProgram_Title = program_index.getProgram_Description();
		if(  mProgram_Title == null ) {
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this);
		}
		else {
			setTitle("Revise : "+ mProgram_Title.toUpperCase());
			Log.d("ProgramActivity", " :: Program_Index : " + mProgramIndex +"");
			getProgramTableFromDB(mProgramIndex);
		}
		this.overridePendingTransition(R.anim.anim_slide_in_left,
				R.anim.anim_slide_out_left);
	}

	private void getProgramTableFromDB(int program_Index) {

		new ProgramFetcherTask(this, new UIProgramFetcherListener() {

			@Override
			public void updateUI(List<Program_Table> program_TableList) {
				mProgram_TableList = program_TableList;
				if( mProgram_TableList == null || mProgram_TableList.size() == 0 ) {
					new DataBaseInsertAsyncTask(ProgramActivity.this, mProgramIndex, ProgramActivity.this ).execute();
					mProgram_TableList = mDatabaseHandler.getAllProgram_Tables(mProgramIndex, new CreekPreferences(ProgramActivity.this).getProgramLanguage());
				}
				else {
					initUI( mProgram_TableList );
				}
			}
		}, mDatabaseHandler, program_Index).execute();


	}

	private void initUI(List<Program_Table> program_TableList) {

		mProgramList = new ArrayList<String>();
		mProgramExplanationList = new ArrayList<String>();

		Iterator<Program_Table> iteraor = program_TableList.iterator();
		while(iteraor.hasNext()) { 

			Program_Table newProgram_Table = iteraor.next();
			mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
			mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());

		}

		setContentView(R.layout.activity_program);

		//FrameLayout container = (LinearLayout) findViewById(R.id.container);
		mProgramListView = (ListView) findViewById(R.id.list_program);
		mProgramExplanationListView = (ListView) findViewById(R.id.list_explanation);



		// Prepare the ListView
		/*mAdapterProgramList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mProgramList);*/
		mAdapterProgramList = new CustomProgramLineListAdapter(this, android.R.layout.simple_list_item_1,
				R.id.progamLineTxtView, mProgramList, false );

		// Prepare the ListView
		mAdapterProgramExplanationList = new CustomProgramLineListAdapter(this, android.R.layout.simple_list_item_1,
				R.id.progamLineTxtView, mProgramExplanationList, true);

		mProgramListView.setAdapter(mAdapterProgramList);
		mProgramExplanationListView.setAdapter(mAdapterProgramExplanationList);
		mProgramExplanationListView.setRotationY(-90f);
		mListPostion = mProgramListView.getFirstVisiblePosition();

		initButtons();

	}

	private void initButtons() {

		mProgDescriptionBtn = (Button) findViewById(R.id.descriptionBtn);
		mProgDescriptionBtn.setOnClickListener(mButtonClickListener);

		mNextProgramBtn = (ImageButton) findViewById(R.id.nextProgramBtn);
		mNextProgramBtn.setOnClickListener(mButtonClickListener);

		mPrevProgramBtn = (ImageButton) findViewById(R.id.prevProgramBtn);
		mPrevProgramBtn.setOnClickListener(mButtonClickListener);
		enableDisablePrevButton();
		if( mWizard == true ) {
			mPrevProgramBtn.setVisibility(View.GONE);
		}


	}

	OnClickListener mButtonClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch( v.getId() ) {
			case R.id.descriptionBtn :
				programDescriptionAction();
				break;

			case R.id.prevProgramBtn :
				prevProgramBtnAction();
				break;

			case R.id.nextProgramBtn :
				if( mWizard == true ) {
					showSelectBox();
				}
				else {
					nextProgramBtnAction();	
				}
				break;

			}

		}




	};


	private void programDescriptionAction() {

		if(mProgDescriptionBtn.getText() == "Flip") { 
			mProgDescriptionBtn.setText("Flip");	
		}
		else { 
			mProgDescriptionBtn.setText("Flip");
		}
		flipit();

	}

	protected void nextProgramBtnAction() {
		mProgramIndex = mProgramIndex + 1;
		enableDisablePrevButton();
		NextProgram(mProgramIndex);
	}

	protected void prevProgramBtnAction() {
		mProgramIndex = mProgramIndex - 1;
		enableDisablePrevButton();
		NextProgram(mProgramIndex);
	}

	public String getProgramTitle(int program_Index) {
		if( mDatabaseHandler == null ) {
			mDatabaseHandler = new DatabaseHandler(ProgramActivity.this);
		}
		return AuxilaryUtils.getProgramTitle(program_Index, ProgramActivity.this, mDatabaseHandler );
	}

	public void enableDisablePrevButton() { 
		if( mProgramIndex == 1 || mProgramIndex < 1) {
			mPrevProgramBtn.setEnabled(false);

		}
		else {
			mPrevProgramBtn.setEnabled(true);
		}

	}

	protected void NextProgram(int program_Index) {
		/**
		 * Reset the list and reset adapter to change view
		 * */
		if( program_Index > 0 && program_Index <= mTotalPrograms ) {

			List<Program_Table> program_TableList = mDatabaseHandler.getAllProgram_Tables(program_Index, new CreekPreferences(this).getProgramLanguage());
			if( program_TableList == null || program_TableList.size() == 0 && mProgramIndex <= mTotalPrograms ) {

				new DataBaseInsertAsyncTask( this, mProgramIndex, this ).execute();
				program_TableList = mDatabaseHandler.getAllProgram_Tables(mProgramIndex, new CreekPreferences(this).getProgramLanguage());

			}
			else {
				/*if( mProgDescriptionBtn.getText().equals("Flip")) {
					flipit();
					mProgDescriptionBtn.setText("Flip");
				}*/
				mAdapterProgramExplanationList.clear();
				mAdapterProgramList.clear();
				mListPostion = 1;
			}
			mProgramList = new ArrayList<String>();
			mProgramExplanationList = new ArrayList<String>();
			mProgram_Title = getProgramTitle(mProgramIndex);

			if( mProgram_Title == null ) {
				AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", ProgramActivity.this);
			}
			else {
				setTitle("Revise : " + mProgram_Title.toUpperCase() );
				Iterator<Program_Table> iteraor = program_TableList.iterator();

				while(iteraor.hasNext()) { 
					Program_Table newProgram_Table = iteraor.next();
					mProgramList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line());
					mProgramExplanationList.add(newProgram_Table.getLine_No()+". "+newProgram_Table.getProgram_Line_Description());

				}


			}

			// Prepare the ListView
			mAdapterProgramList.addAll(mProgramList);
			// Prepare the ListView
			mAdapterProgramExplanationList.addAll(mProgramExplanationList);

			mAdapterProgramList.setNotifyOnChange(true);
			mAdapterProgramExplanationList.setNotifyOnChange(true);

		}
		if( program_Index > mTotalPrograms) {
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", ProgramActivity.this);
			mProgramIndex--;
		}

	}

	private Interpolator accelerator = new AccelerateInterpolator();
	private Interpolator decelerator = new DecelerateInterpolator();

	private void flipit() {

		final ListView visibleList;
		final ListView invisibleList;
		if (mProgramListView.getVisibility() == View.GONE) {
			mListPostion = mProgramExplanationListView.getFirstVisiblePosition();
			visibleList = mProgramExplanationListView;
			invisibleList = mProgramListView;
		} else {
			mListPostion = mProgramListView.getFirstVisiblePosition();
			invisibleList = mProgramExplanationListView;
			visibleList = mProgramListView;
		}
		ObjectAnimator visToInvis = ObjectAnimator.ofFloat(visibleList, "rotationY", 0f, 90f);
		visToInvis.setDuration(500);
		visToInvis.setInterpolator(accelerator);
		final ObjectAnimator invisToVis = ObjectAnimator.ofFloat(invisibleList, "rotationY",
				-90f, 0f);
		invisToVis.setDuration(500);
		invisToVis.setInterpolator(decelerator);
		visToInvis.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator anim) {
				visibleList.setVisibility(View.GONE);
				invisToVis.start();
				invisibleList.setVisibility(View.VISIBLE);
			}
		});
		visToInvis.start();
		//TODO TO retain postion
		visibleList.setSelection(mListPostion);
		invisibleList.setSelection(mListPostion);
	}

	protected void showSelectBox() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Quiz Mode - Question Type"); 
		String[] boxTypes = {"Description","Program Code"};
		builder.setItems(boxTypes, quizTypeListener);
		builder.setNegativeButton("Cancel", null);
		AlertDialog alertDialog  = builder.create();
		alertDialog.show();

	}

	public static String KEY_QUIZ_TYPE = "Quiz_Type";

	public static final int KEY_QUIZ_DESCRIPTION_QUESTION = 1;
	public static final int KEY_QUIZ_PROGRAM_CODE_QUESTION = 0;
	public static final String KEY_WIZARD = "wizard_mode";

	/**
	 * Quiz Type Listener 
	 * */
	DialogInterface.OnClickListener quizTypeListener = 
			new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {

			Bundle newIntentBundle = new Bundle();
			Intent newIntent = new Intent(ProgramActivity.this, WizardActivity.class);
			newIntentBundle.putInt(ProgrammingBuddyConstants.KEY_INVOKE_TEST, ProgrammingBuddyConstants.KEY_QUIZ);
			newIntentBundle.putBoolean(ProgramListActivity.KEY_WIZARD, true);

			switch ( which ) {

			case KEY_QUIZ_DESCRIPTION_QUESTION :
				newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_index);
				newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_DESCRIPTION_QUESTION);

				break;

			case KEY_QUIZ_PROGRAM_CODE_QUESTION :
				newIntentBundle.putParcelable(ProgrammingBuddyConstants.KEY_PROG_ID, program_index);
				newIntentBundle.putInt(KEY_QUIZ_TYPE, KEY_QUIZ_PROGRAM_CODE_QUESTION);
				break;

			}

			newIntent.putExtras(newIntentBundle);
			startActivity(newIntent);
			ProgramActivity.this.finish();

		}
	};


	@Override
	public void updateUI() {

		mProgram_TableList = mDatabaseHandler.getAllProgram_Tables(mProgramIndex, new CreekPreferences(this).getProgramLanguage());
		if( mProgram_TableList == null || mProgram_TableList.size() == 0 ) { 
			AuxilaryUtils.displayAlert(getString(R.string.app_name), "You are viewing the last program", this);
			mProgramIndex--;
		}
		else {
			initUI( mProgram_TableList );	
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.program, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.action_refresh_database:
			new DataBaseInsertAsyncTask(this, mProgramIndex, this ).execute();
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.anim_slide_in_right,
				R.anim.anim_slide_out_right);
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
