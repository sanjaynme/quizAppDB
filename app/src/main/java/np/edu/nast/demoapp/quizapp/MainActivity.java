package np.edu.nast.demoapp.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import np.edu.nast.demoapp.quizapp.contracts.AppContract;
import np.edu.nast.demoapp.quizapp.db.GKDataSource;
import np.edu.nast.demoapp.quizapp.db.QuizDataSource;
import np.edu.nast.demoapp.quizapp.db.ScienceDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;

    Button SaveButtonInSQLite, btnQuiz, btnGk, btnScience;

    ProgressDialog progressDialog;
    private GKDataSource gkDataSource;
    private ScienceDataSource scienceDataSource;
    private QuizDataSource quizDataSource;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gkDataSource = new GKDataSource(MainActivity.this);
        scienceDataSource = new ScienceDataSource(MainActivity.this);
        quizDataSource = new QuizDataSource(MainActivity.this);
        SaveButtonInSQLite = findViewById(R.id.button);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        btnQuiz = findViewById(R.id.btn_quiz);
        btnGk = findViewById(R.id.btn_gk);
        btnScience = findViewById(R.id.btn_science);

        SaveButtonInSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("LOADING");
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                ApiService.getServiceClass().getAllPost().enqueue(new Callback<List<ApiObject>>() {
                    @Override
                    public void onResponse(Call<List<ApiObject>> call, Response<List<ApiObject>> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            ApiObject question = new ApiObject();
                            setQuizData(response, question);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApiObject>> call, Throwable t) {
                        Log.d("", "Error msg is :::" + t.getMessage());
                    }
                });
                ApiService.getServiceClass().getGkPost().enqueue(new Callback<List<ApiObject>>() {
                    @Override
                    public void onResponse(Call<List<ApiObject>> call, Response<List<ApiObject>> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            ApiObject question = new ApiObject();

                            setGKData(response, question);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApiObject>> call, Throwable t) {
                        Log.d("", "Error msg is :::" + t.getMessage());
                    }
                });
                ApiService.getServiceClass().getSciencePost().enqueue(new Callback<List<ApiObject>>() {
                    @Override
                    public void onResponse(Call<List<ApiObject>> call, Response<List<ApiObject>> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            ApiObject question = new ApiObject();
                            setScienceData(response, question);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ApiObject>> call, Throwable t) {
                        Log.d("", "Error msg is :::" + t.getMessage());
                    }
                });

            }
        });

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                sharedPreferenceManager.setKeyValues(AppContract.PreferencesKeys.SUBJECT_QUIZ, AppContract.PreferencesValues.QUIZ);
                startActivity(intent);
            }
        }); btnGk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                sharedPreferenceManager.setKeyValues(AppContract.PreferencesKeys.SUBJECT_GK, AppContract.PreferencesValues.GK);
                startActivity(intent);
            }
        }); btnScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowDataActivity.class);
                sharedPreferenceManager.setKeyValues(AppContract.PreferencesKeys.SUBJECT_SCIENCE, AppContract.PreferencesValues.SCIENCE);
                startActivity(intent);
            }
        });
    }

    private void setGKData(Response<List<ApiObject>> response, ApiObject question) {
        gkDataSource.open();
        for (int i = 0; i < response.body().size(); i++) {
            long id = response.body().get(i).getId();
            question.setId(id);
            question.setAnswer(response.body().get(i).getAnswer());
            question.setOpta(response.body().get(i).getOpta());
            question.setOptb(response.body().get(i).getOptb());
            question.setOptc(response.body().get(i).getOptc());
            question.setQuestion(response.body().get(i).getQuestion());
            gkDataSource.insertOrUpdateGkQuestion(question);
        }
        gkDataSource.close();
    }

    private void setQuizData(Response<List<ApiObject>> response, ApiObject question) {
        quizDataSource.open();
        for (int i = 0; i < response.body().size(); i++) {
            long id = response.body().get(i).getId();
            question.setId(id);
            question.setAnswer(response.body().get(i).getAnswer());
            question.setOpta(response.body().get(i).getOpta());
            question.setOptb(response.body().get(i).getOptb());
            question.setOptc(response.body().get(i).getOptc());
            question.setQuestion(response.body().get(i).getQuestion());
            quizDataSource.insertOrUpdateQuizQuestion(question);
        }
        quizDataSource.close();
    }

    private void setScienceData(Response<List<ApiObject>> response, ApiObject question) {
        scienceDataSource.open();
        for (int i = 0; i < response.body().size(); i++) {
            long id = response.body().get(i).getId();
            question.setId(id);
            question.setAnswer(response.body().get(i).getAnswer());
            question.setOpta(response.body().get(i).getOpta());
            question.setOptb(response.body().get(i).getOptb());
            question.setOptc(response.body().get(i).getOptc());
            question.setQuestion(response.body().get(i).getQuestion());
            scienceDataSource.insertOrUpdateScienceQuestion(question);
        }
        scienceDataSource.close();
    }
}