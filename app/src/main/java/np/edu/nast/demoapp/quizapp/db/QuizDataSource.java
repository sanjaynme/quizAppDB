package np.edu.nast.demoapp.quizapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import np.edu.nast.demoapp.quizapp.ApiObject;

public class QuizDataSource {
    private static final String LOGTAG = "GKDataSource";
    QuizDBHelper dbhelper;
    SQLiteDatabase database;

    public QuizDataSource(Context context) {
        dbhelper = new QuizDBHelper(context);
    }

    public void open() {
        Log.d(LOGTAG, "Database opened!");
        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.d(LOGTAG, "Database closed!");
        dbhelper.close();
    }

    public void insertOrUpdateQuizQuestion(ApiObject question) {
        long id = question.getId();
        Cursor c = database.rawQuery("SELECT * FROM " + QuizDBHelper.TABLE_NAME_QUIZ + " WHERE " + QuizDBHelper.COLUMN_ITEM_ID + "=" + id, null);
        if (c.getCount() == 0) {
            //if the row is not present,then insert the row
            ContentValues values = new ContentValues();
            values.put(QuizDBHelper.COLUMN_ITEM_ID, id);
            values.put(QuizDBHelper.COLUMN_QUESTION, question.getQuestion());
            values.put(QuizDBHelper.COLUMN_OPTION_A, question.getOpta());
            values.put(QuizDBHelper.COLUMN_OPTION_B, question.getOptb());
            values.put(QuizDBHelper.COLUMN_OPTION_C, question.getOptc());
            values.put(QuizDBHelper.COLUMN_OPTION_ANSWER, question.getAnswer());


            database.insert(QuizDBHelper.TABLE_NAME_QUIZ, null, values);
        } else {
            //else update the row
            ContentValues updatedValues = new ContentValues();
            updatedValues.put(QuizDBHelper.COLUMN_ITEM_ID, id);
            updatedValues.put(QuizDBHelper.COLUMN_QUESTION, question.getQuestion());
            updatedValues.put(QuizDBHelper.COLUMN_OPTION_A, question.getOpta());
            updatedValues.put(QuizDBHelper.COLUMN_OPTION_B, question.getOptb());
            updatedValues.put(QuizDBHelper.COLUMN_OPTION_C, question.getOptc());
            updatedValues.put(QuizDBHelper.COLUMN_OPTION_ANSWER, question.getAnswer());

            database.update(QuizDBHelper.TABLE_NAME_QUIZ, updatedValues, QuizDBHelper.COLUMN_ITEM_ID + "=" + id, null);
        }
    }

    public List<ApiObject> getData() {
        database = dbhelper.getReadableDatabase();
//        SQLiteDatabase db = dbhelper.getReadableDatabase();

        String[] columns = {QuizDBHelper.COLUMN_ITEM_ID,
                QuizDBHelper.COLUMN_QUESTION,
                QuizDBHelper.COLUMN_OPTION_A,
                QuizDBHelper.COLUMN_OPTION_B,
                QuizDBHelper.COLUMN_OPTION_C,
                QuizDBHelper.COLUMN_OPTION_ANSWER};
        Cursor cursor = database.query(QuizDBHelper.TABLE_NAME_QUIZ, columns, null, null, null, null, null);
        List<ApiObject> questionList = new ArrayList<>();

        while (cursor.moveToNext()) {
            ApiObject q = new ApiObject();
            q.setId(cursor.getInt(0));
            q.setQuestion(cursor.getString(1));
            q.setOpta(cursor.getString(2));
            q.setOptb(cursor.getString(3));
            q.setOptc(cursor.getString(4));
            q.setAnswer(cursor.getString(5));

            questionList.add(q);
        }
        return questionList;
    }

}
