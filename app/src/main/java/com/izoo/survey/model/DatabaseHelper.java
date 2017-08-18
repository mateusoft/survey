package com.izoo.survey.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateusz on 08.08.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private CreateDataBase cdb = new CreateDataBase();
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SurveyDatabase";

    // Table Names
    private static final String TABLE_Users = "User";
    private static final String TABLE_Type_Users = "Type_User";
    private static final String TABLE_History = "History";
    private static final String TABLE_Survey_Users = "Survey_User";
    private static final String TABLE_Survey = "Survey";
    private static final String TABLE_Survey_Anonymous = "Survey_Anonymous";
    private static final String TABLE_Section_In_Survey = "Sections_In_Survey";
    private static final String TABLE_Section = "Section";
    private static final String TABLE_Question_In_Section = "Questions_In_Section";
    private static final String TABLE_Question = "Question";
    private static final String TABLE_Answers_To_Question = "Answers_To_Question";
    private static final String TABLE_Answers = "Answer";
    private static final String TABLE_Type_Answers = "Type_Answer";
    private static final String TABLE_Answers_To_Open_Question = "Answers_To_Open_Question";
    private static final String TABLE_Answers_In_Question = "Answers_In_Question";

    // Common column names
    private static final String KEY_Name = "Name";
    private static final String KEY_Sequence = "Sequence";
    private static final String KEY_Date_Of_The_Survey = "Date_Of_The_Survey";

    // Users Table - column names
    private static final String KEY_ID_User = "ID_User";
    private static final String KEY_Login = "Login";
    private static final String KEY_Password = "Password";

    // Type_Users Table - column names
    private static final String KEY_ID_Type_Users = "ID_Type_Users";
    private static final String KEY_Name_Type_User = "Name_Type_User";

    // Survey Table - column names
    private static final String KEY_ID_Survey = "ID_Survey";
    private static final String KEY_Date_Create = "Date_Create";
    private static final String KEY_Date_Update = "Date_Update";
    private static final String KEY_Anonymous = "Anonymous";

    // Section Table - column names
    private static final String KEY_ID_Section = "ID_Section";

    // Question Table - column names
    private static final String KEY_ID_Question = "ID_Question";
    private static final String KEY_Text_Question = "Text_Question";
    private static final String KEY_Required = "Required";
    private static final String KEY_Tips = "Tips";

    // Answers _To_Open_Question Table - column names
    private static final String KEY_Text_Answers = "Text_Answer";

    // Type_Answers Table - column names
    private static final String KEY_ID_Type_Answers = "ID_Type_Answers";

    // Answers Table - column names
    private static final String KEY_ID_Answers = "ID_Answers";

    // History Table - column names
    private static final String KEY_ID_History = "ID_History";
    private static final String KEY_Date_Hour = "Date_Hour";

    // Table Create Statements
    // Type_Users table create statement
    private static final String CREATE_TABLE_Type_Users = "CREATE TABLE "
            + TABLE_Type_Users + "(" + KEY_ID_Type_Users + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name_Type_User
            + " VARCHAR(15) NOT NULL)";

    // Users table create statement
    private static final String CREATE_TABLE_Users = "CREATE TABLE "
            + TABLE_Users + "(" + KEY_ID_User + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Login
            + " VARCHAR(45) NOT NULL," + KEY_Password + " VARCHAR(45) NOT NULL," + KEY_ID_Type_Users
            + " INTEGER, " +
            " FOREIGN KEY "+ "("+ KEY_ID_Type_Users+")"+ " REFERENCES "+
            TABLE_Type_Users + "(" + KEY_ID_Type_Users+")" +
            ")";

    // Survey table create statement
    private static final String CREATE_TABLE_Survey = "CREATE TABLE "
            + TABLE_Survey + "(" + KEY_ID_Survey + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(45) NOT NULL," + KEY_Date_Create + " DATETIME NOT NULL," + KEY_Date_Update + " DATETIME NOT NULL, " +
            KEY_Anonymous + " BOOLEAN NOT NULL )";

    // Section table create statement
    private static final String CREATE_TABLE_Section = "CREATE TABLE "
            + TABLE_Section + "(" + KEY_ID_Section + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(30) NOT NULL)";

    // Type_Answers table create statement
    private static final String CREATE_TABLE_Type_Answers = "CREATE TABLE "
            + TABLE_Type_Answers + "(" + KEY_ID_Type_Answers + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(20) NOT NULL)";

    // Question table create statement
    private static final String CREATE_TABLE_Question = "CREATE TABLE "
            + TABLE_Question + "(" + KEY_ID_Question + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_Text_Question
            + " VARCHAR(45) NOT NULL, " + KEY_Required + " BOOLEAN NOT NULL, " + KEY_Tips
            + " VARCHAR(45), " + KEY_ID_Type_Answers + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Type_Answers + ")" + " REFERENCES " + TABLE_Type_Answers +
            "(" + KEY_ID_Type_Answers + ")" + ")";

    // Answers_To_Question table create statement
    private static final String CREATE_TABLE_Answers_To_Question = "CREATE TABLE "
            + TABLE_Answers_To_Question + "(" + KEY_ID_Question + " INTEGER, "
            + KEY_ID_Answers + " INTEGER, " + KEY_ID_History + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Question + ")" +
            " REFERENCES " + TABLE_Question + "(" + KEY_ID_Question + ")" +
            " FOREIGN KEY " + "(" + KEY_ID_Answers + ")" +
            " REFERENCES " + TABLE_Answers + "(" + KEY_ID_Answers + ")" +
            " FOREIGN KEY " + "(" + KEY_ID_History + ")" +
            " REFERENCES " + TABLE_History + "(" + KEY_ID_History + ")," +
            "PRIMARY KEY (" + KEY_ID_History +","+KEY_ID_Answers+","+KEY_ID_Question+")"+
            ")";

    // Answers table create statement
    private static final String CREATE_TABLE_Answers = "CREATE TABLE "
            + TABLE_Answers + "(" + KEY_ID_Answers + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Text_Answers
            + " VARCHAR(45) NOT NULL)";

    // Survey_Users table create statement
    private static final String CREATE_TABLE_Survey_Users = "CREATE TABLE " +TABLE_Survey_Users +
            "(" + KEY_ID_User + " INTEGER, " + KEY_ID_Survey + " INTEGER," +
            " FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey + "(" + KEY_ID_Survey + "),"+
            "FOREIGN KEY " + "(" + KEY_ID_User+ ")" + " REFERENCES " + TABLE_Users+ "(" + KEY_ID_User+ ")," +
            "PRIMARY KEY (" + KEY_ID_User + "," + KEY_ID_Survey + ")" + ")";

    // Survey_Anonymous table create statement
    private static final String CREATE_TABLE_Survey_Anonymous = "CREATE TABLE " + TABLE_Survey_Anonymous+
            "(" + KEY_ID_Survey + " INTEGER," + KEY_ID_User + " INTEGER," + KEY_Date_Of_The_Survey + " DATETIME, " +
            " FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey+ "(" + KEY_ID_Survey+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_User+ ")" + " REFERENCES " + TABLE_Users+ "(" + KEY_ID_User+ ")," +
            "PRIMARY KEY (" + KEY_ID_User + "," + KEY_ID_Survey + ")" + ")";

    // Section_In_Survey table create statement
    private static final String CREATE_TABLE_Section_In_Survey = "CREATE TABLE "+ TABLE_Section_In_Survey +
            "(" + KEY_ID_Section + " INTEGER," + KEY_ID_Survey + " INTEGER, " + KEY_Sequence +" INTEGER NOT NULL," +
            "FOREIGN KEY " + "(" + KEY_ID_Section+ ")" + " REFERENCES " + TABLE_Section+ "(" + KEY_ID_Section+ "),"+
            "FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey+ "(" + KEY_ID_Survey+ ")," +
            "PRIMARY KEY (" + KEY_ID_Section + "," + KEY_ID_Survey + ")" + ")";

    // Question_In_Section table create statement
    private static final String CREATE_TABLE_Question_In_Section = "CREATE TABLE " + TABLE_Question_In_Section +
            "(" + KEY_ID_Question + " INTEGER," + KEY_ID_Section + " INTEGER, " + KEY_Sequence +" INTEGER NOT NULL," +
            " FOREIGN KEY " + "(" + KEY_ID_Section+ ")" + " REFERENCES " + TABLE_Section+ "(" + KEY_ID_Section+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_Question+ ")" + " REFERENCES " + TABLE_Question+ "(" + KEY_ID_Question+ ")," +
            "PRIMARY KEY (" + KEY_ID_Section + "," + KEY_ID_Question + ")" + ")";

    // History table create statement
    private static final String CREATE_TABLE_History = "CREATE TABLE " + TABLE_History +
            "(" + KEY_ID_History + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Date_Hour +
            " DATETIME," + KEY_ID_User + " INTEGER," + KEY_ID_Survey + " INTEGER," +
            " FOREIGN KEY " + "(" + KEY_ID_Survey + ")" + " REFERENCES " + TABLE_Survey + "(" + KEY_ID_Survey+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_User + ")" + " REFERENCES " + TABLE_Users + "(" + KEY_ID_User + ")" + ")";

    // Answers _To_Open_Question table create statement
    private static final String CREATE_TABLE_Answers_To_Open_Question = "CREATE TABLE " + TABLE_Answers_To_Open_Question +
            "(" + KEY_ID_Question + " INTEGER, " + KEY_Text_Answers + " VARCHAR(45) NOT NULL," + KEY_ID_History + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Question + ")" + " REFERENCES " + TABLE_Question + "(" + KEY_ID_Question + ")," +
            " FOREIGN KEY " + "(" + KEY_ID_History + ")" + " REFERENCES " + TABLE_History + "(" + KEY_ID_History + ")," +
            "PRIMARY KEY (" + KEY_ID_Question +", " + KEY_ID_History +" ) )";

    // Answers_In_Question table create statement
    private static final String CREATE_TABLE_Answers_In_Question = "CREATE TABLE " + TABLE_Answers_In_Question +
            "(" + KEY_ID_Question + " INTEGER," + KEY_ID_Answers + " INTEGER, " + KEY_Sequence +" INTEGER NOT NULL," +
            " FOREIGN KEY " + "(" + KEY_ID_Answers+ ")" + " REFERENCES " + TABLE_Answers+ "(" + KEY_ID_Answers + "),"+
            " FOREIGN KEY " + "(" + KEY_ID_Question+ ")" + " REFERENCES " + TABLE_Question+ "(" + KEY_ID_Question+ ")," +
            "PRIMARY KEY (" + KEY_ID_Answers + "," + KEY_ID_Question + ")" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_Type_Users);
        db.execSQL(CREATE_TABLE_Users);
        db.execSQL(CREATE_TABLE_Survey);
        db.execSQL(CREATE_TABLE_Section);
        db.execSQL(CREATE_TABLE_Type_Answers);
        db.execSQL(CREATE_TABLE_Question);
        db.execSQL(CREATE_TABLE_Answers_To_Question);
        db.execSQL(CREATE_TABLE_Answers);
        db.execSQL(CREATE_TABLE_Survey_Users);
        db.execSQL(CREATE_TABLE_Survey_Anonymous);
        db.execSQL(CREATE_TABLE_Section_In_Survey);
        db.execSQL(CREATE_TABLE_Question_In_Section);
        db.execSQL(CREATE_TABLE_History);
        db.execSQL(CREATE_TABLE_Answers_To_Open_Question);
        db.execSQL(CREATE_TABLE_Answers_In_Question);
        db.execSQL(cdb.addTypeUser);
        db.execSQL(cdb.addUser);
        db.execSQL(cdb.addSurvey);
        db.execSQL(cdb.addSection);
        db.execSQL(cdb.addTypeAnswer);
        db.execSQL(cdb.addQuestion);
        db.execSQL(cdb.addAnswers);
        db.execSQL(cdb.addQuestionInSection);
        db.execSQL(cdb.addSurveyInSection);
        db.execSQL(cdb.addSurveyUsers);
        db.execSQL(cdb.addAnswerInQuestion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Type_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Survey);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Section);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Type_Answers);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Question);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers_To_Question);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Survey_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Survey_Anonymous);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Section_In_Survey);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Question_In_Section);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_History);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers_To_Open_Question);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers_In_Question);

        // create new tables
        onCreate(sqLiteDatabase);
    }
    // Adding new History
    public void addHistory(History h) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Date_Hour , h.getDate_Hour().toString()); //
        values.put(KEY_ID_User, h.getId_User());
        values.put(KEY_ID_Survey, h.getId_Survey());

        // Inserting Row
        db.insert(TABLE_History, null, values);
        db.close(); // Closing database connection
    }

    // Adding new Answers
    public void addAnswers(Answers answers) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Text_Answers , answers.getText_Answers()); //

        // Inserting Row
        db.insert(TABLE_Answers, null, values);
        db.close(); // Closing database connection
    }

    public List<Survey> getAllToDosByTag(int id) {
        List<Survey> survey = new ArrayList<>();

        String selectQuery = "select * from "+
                TABLE_Survey+ " where " + KEY_ID_Survey+"=="+id;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
//        if (c.moveToFirst()) {
//            do {
//                Survey s = new Survey();
//                s.setId(c.getInt((c.getColumnIndex(KEY_ID))));
//                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
//                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
//
//                // adding to todo list
//                todos.add(td);
//            } while (c.moveToNext());
//        }

        return survey;
    }
    public Cursor getAllSection() {
        String[] columns = {KEY_ID_Section + " as _id",KEY_Name,KEY_Sequence};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_Section,columns,null,null,null,null,null);
        return cursor;
    }


    public static Users checkUser(SQLiteDatabase db , String login, String password){
        Cursor cursor = db.query(TABLE_Users,
                new String[]{KEY_ID_User, KEY_Login,KEY_Password, KEY_ID_Type_Users},
                KEY_Login + " = ? AND " + KEY_Password + " = ?",
                new String[]{login,password},
                null,null,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            return new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
        }
        return null;
    }
    public static Cursor getUsers(SQLiteDatabase db){
        return db.query(TABLE_Users,new String[]{KEY_ID_User + " as _id",KEY_Login},null,null,null,null,null);
    }

}
