package dk.brams.android.geoquizz;
public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mDone;

    public Question(int textResId, boolean answerTrue) {
        this.mTextResId = textResId;
        this.mAnswerTrue = answerTrue;
        this.mDone = false;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public boolean isDone() {
        return mDone;
    }

    public void setDone(boolean status) {mDone = status;}

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
