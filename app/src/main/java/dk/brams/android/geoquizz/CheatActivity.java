package dk.brams.android.geoquizz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE = "dk.brams.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "dk.brams.android.geoquiz.answer_shown";
    private static final String KEY_CHEATER = "cheat";
    private boolean mAnswerIsTrue;

    private Button mShowAnswer;
    private TextView mAnswerTextView, mVersionField;
    private boolean mAnswerShown;


    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
        mVersionField = (TextView)findViewById(R.id.versionField);

        mVersionField.setText(getString(R.string.build_version) + " " + Build.VERSION.SDK);

        // check if the device has just been rotated or otherwise changed - if so restore cheater status
        if (savedInstanceState != null) {
            setAnswerShown(savedInstanceState.getBoolean(KEY_CHEATER, false));
        }


        mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAnswerShown(true);
            }
        });
    }

    private void updateResultDisplay() {
        if (mAnswerIsTrue)
            mAnswerTextView.setText(R.string.true_button);
        else
            mAnswerTextView.setText(R.string.false_button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx=mShowAnswer.getWidth()/2;
            int cy=mShowAnswer.getHeight()/2;
            float radius = mShowAnswer.getWidth();

            Animator anim= ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius,0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mAnswerTextView.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        } else {
            mAnswerTextView.setVisibility(View.VISIBLE);
            mShowAnswer.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d(TAG, "onSaveInstanceState() called ");

        // in case the device is rotated during a session save cheater status
        // as an extra in the bundle
        savedInstanceState.putBoolean(KEY_CHEATER, mAnswerShown);
    }


    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }


    private void setAnswerShown(boolean isAnswerShown) {

        mAnswerShown = isAnswerShown;
        if (isAnswerShown) {

            updateResultDisplay();

            Intent data = new Intent();
            data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
            setResult(RESULT_OK, data);
        }
    }

}
