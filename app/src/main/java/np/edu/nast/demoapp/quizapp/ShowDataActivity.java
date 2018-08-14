package np.edu.nast.demoapp.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import np.edu.nast.demoapp.quizapp.contracts.AppContract;
import np.edu.nast.demoapp.quizapp.db.GKDataSource;
import np.edu.nast.demoapp.quizapp.db.QuizDataSource;
import np.edu.nast.demoapp.quizapp.db.ScienceDataSource;

public class ShowDataActivity extends AppCompatActivity {

    int score = 0;
    int quid = 0;


    TextView txtQuestion;
    RadioButton rda, rdb, rdc;
    Button butNext;


    private List<ApiObject> quizQuestionList, gkQuestionList, scienceQuestionList;
    private ApiObject currentQuestion;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        QuizDataSource quizDataSource = new QuizDataSource(this);
        GKDataSource gkDataSource = new GKDataSource(this);
        ScienceDataSource scienceDataSource = new ScienceDataSource(this);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        if (sharedPreferenceManager.getStringValues(AppContract.PreferencesKeys.SUBJECT_GK)
                .equalsIgnoreCase(sharedPreferenceManager.getStringValues(AppContract.PreferencesValues.GK))) {
            gkQuestionList = gkDataSource.getData();
            Collections.shuffle(gkQuestionList);
            currentQuestion = gkQuestionList.get(quid);
        } else if (sharedPreferenceManager.getStringValues(AppContract.PreferencesKeys.SUBJECT_QUIZ)
                .equalsIgnoreCase(sharedPreferenceManager.getStringValues(AppContract.PreferencesValues.QUIZ))) {
            quizQuestionList = quizDataSource.getData();
            Collections.shuffle(quizQuestionList);
            currentQuestion = quizQuestionList.get(quid);
        } else if (sharedPreferenceManager.getStringValues(AppContract.PreferencesKeys.SUBJECT_SCIENCE)
                .equalsIgnoreCase(sharedPreferenceManager.getStringValues(AppContract.PreferencesValues.SCIENCE))) {
            scienceQuestionList = scienceDataSource.getData();
            Collections.shuffle(scienceQuestionList);
            currentQuestion = scienceQuestionList.get(quid);
        }
        txtQuestion = findViewById(R.id.question);
        rda = findViewById(R.id.radio0);
        rdb = findViewById(R.id.radio1);
        rdc = findViewById(R.id.radio2);
        butNext = findViewById(R.id.button1);
        setQuestionView();
    }

    private void setQuestionView() {
        txtQuestion.setText(currentQuestion.getQuestion());
        rda.setText(currentQuestion.getOpta());
        rdb.setText(currentQuestion.getOptb());
        rdc.setText(currentQuestion.getOptc());
        quid++;
    }

    public void btClick(View view) {
        RadioGroup grp = findViewById(R.id.radioGroup1);
        RadioButton answer = findViewById(grp.getCheckedRadioButtonId());
        if (currentQuestion.getAnswer().equals(answer.getText())) {
            Toast.makeText(ShowDataActivity.this, "You Are Correct", Toast.LENGTH_SHORT).show();
            score++;
            Log.d("Score", "Your score: " + score);
        } else {
            Toast.makeText(ShowDataActivity.this, "You Are Wrong" + "The Correct Answer is:" + currentQuestion.getAnswer(), Toast.LENGTH_SHORT).show();
        }

        if (quid < 3) {
            if (sharedPreferenceManager.getStringValues(AppContract.PreferencesKeys.SUBJECT_GK)
                    .equalsIgnoreCase(sharedPreferenceManager.getStringValues(AppContract.PreferencesValues.GK))) {
                currentQuestion = gkQuestionList.get(quid);
            } else if (sharedPreferenceManager.getStringValues(AppContract.PreferencesKeys.SUBJECT_QUIZ)
                    .equalsIgnoreCase(sharedPreferenceManager.getStringValues(AppContract.PreferencesValues.QUIZ))) {
                currentQuestion = quizQuestionList.get(quid);
            } else if (sharedPreferenceManager.getStringValues(AppContract.PreferencesKeys.SUBJECT_SCIENCE)
                    .equalsIgnoreCase(sharedPreferenceManager.getStringValues(AppContract.PreferencesValues.SCIENCE))) {
                currentQuestion = scienceQuestionList.get(quid);
            }
            setQuestionView();
        } else {
            Intent intent = new Intent(ShowDataActivity.this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }
}