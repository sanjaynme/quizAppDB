package np.edu.nast.demoapp.quizapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String LOGTAG = "WasteInfoVal";

    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_GK = "gk";
    public static final String TABLE_NAME_SCIENCE = "science";
    public static final String TABLE_NAME_QUIZ = "quiz";
    public static final String COLUMN_ITEM_ID = "id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_OPTION_A = "opta";
    public static final String COLUMN_OPTION_B = "optb";
    public static final String COLUMN_OPTION_C = "optc";
    public static final String COLUMN_OPTION_ANSWER = "answer";

    public QuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create GK Table
    private static final String CREATE_GK_TABLE = "CREATE TABLE "
            + TABLE_NAME_GK + " (" + COLUMN_ITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUESTION + " VARCHAR, "
            + COLUMN_OPTION_A + " VARCHAR, " + COLUMN_OPTION_B
            + " VARCHAR, " + COLUMN_OPTION_C + " VARCHAR, " + COLUMN_OPTION_ANSWER
            + ")";

    //create Science Table
    private static final String CREATE_SCIENCE_TABLE = "CREATE TABLE "
            + TABLE_NAME_SCIENCE + " (" + COLUMN_ITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUESTION + " VARCHAR, "
            + COLUMN_OPTION_A + " VARCHAR, " + COLUMN_OPTION_B
            + " VARCHAR, " + COLUMN_OPTION_C + " VARCHAR, " + COLUMN_OPTION_ANSWER
            + ")";
    //create Quiz Table
    private static final String CREATE_QUIZ_TABLE = "CREATE TABLE "
            + TABLE_NAME_QUIZ + " (" + COLUMN_ITEM_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_QUESTION + " VARCHAR, "
            + COLUMN_OPTION_A + " VARCHAR, " + COLUMN_OPTION_B
            + " VARCHAR, " + COLUMN_OPTION_C + " VARCHAR, " + COLUMN_OPTION_ANSWER
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GK_TABLE);
        db.execSQL(CREATE_SCIENCE_TABLE);
        db.execSQL(CREATE_QUIZ_TABLE);
        Log.i(LOGTAG, "The tables have been created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_GK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SCIENCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_QUIZ);
        // Create tables again
        onCreate(db);
    }
}
