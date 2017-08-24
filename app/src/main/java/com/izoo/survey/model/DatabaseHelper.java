package com.izoo.survey.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.izoo.survey.MainActivity;

import java.sql.Date;
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
    private static final String TABLE_Answers = "Answers";
    private static final String TABLE_Type_Question = "Type_Question";

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

    // Answers _To_Question Table - column names
    private static final String KEY_Text_Answers = "Text_Answer";
    private static final String KEY_Has_Text = "Has_text";

    // Type_Answers Table - column names
    private static final String KEY_ID_Type_Question = "ID_Type_Question";

    // Answers Table - column names
    private static final String KEY_ID_Answers_to_Question = "ID_Answers_to_Question";

    // History Table - column names
    private static final String KEY_ID_History = "ID_History";
    private static final String KEY_Date_Hour = "Date_Hour";

    // Table Create Statements
    // Type_Users table create statement
    private static final String CREATE_TABLE_Type_Users = "CREATE TABLE "
            + TABLE_Type_Users + "(" + KEY_ID_Type_Users + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name_Type_User
            + " VARCHAR(30) NOT NULL)";

    // Users table create statement
    private static final String CREATE_TABLE_Users = "CREATE TABLE "
            + TABLE_Users + "(" + KEY_ID_User + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Login
            + " VARCHAR(45) NOT NULL," + KEY_Password + " VARCHAR(45)," + KEY_ID_Type_Users
            + " INTEGER, " +
            " FOREIGN KEY "+ "("+ KEY_ID_Type_Users+")"+ " REFERENCES "+
            TABLE_Type_Users + "(" + KEY_ID_Type_Users+")" +
            ")";

    // Survey table create statement
    private static final String CREATE_TABLE_Survey = "CREATE TABLE "
            + TABLE_Survey + "(" + KEY_ID_Survey + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(100) NOT NULL," + KEY_Date_Create + " DATETIME," + KEY_Date_Update + " DATETIME, " +
            KEY_Anonymous + " BOOLEAN NOT NULL )";

    // Section table create statement
    private static final String CREATE_TABLE_Section = "CREATE TABLE "
            + TABLE_Section + "(" + KEY_ID_Section + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(50) NOT NULL)";

    // Type_Answers table create statement
    private static final String CREATE_TABLE_Type_Question = "CREATE TABLE "
            + TABLE_Type_Question + "(" + KEY_ID_Type_Question + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_Name
            + " VARCHAR(50) NOT NULL)";

    // Question table create statement
    private static final String CREATE_TABLE_Question = "CREATE TABLE "
            + TABLE_Question + "(" + KEY_ID_Question + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_Text_Question
            + " VARCHAR(250) NOT NULL, " + KEY_Required + " BOOLEAN NOT NULL, " + KEY_Tips
            + " VARCHAR(250), " + KEY_ID_Type_Question + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Type_Question + ")" + " REFERENCES " + TABLE_Type_Question +
            "(" + KEY_ID_Type_Question + ")" + ")";

    // Answers_To_Question table create statement
    private static final String CREATE_TABLE_Answers_To_Question = "CREATE TABLE "
            + TABLE_Answers_To_Question + "(" + KEY_ID_Answers_to_Question + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_Text_Answers + " VARCHAR(250), " + KEY_Sequence +" INTEGER NOT NULL," + KEY_Has_Text + " BOOLEAN NOT NULL, " +
            KEY_ID_Question + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_Question + ")" +
            " REFERENCES " + TABLE_Question + "(" + KEY_ID_Question + ")" + ")";

    // Answers table create statement
    private static final String CREATE_TABLE_Answers = "CREATE TABLE "
            + TABLE_Answers + "(" + KEY_ID_Answers_to_Question + " INTEGER," + KEY_Text_Answers
            + " VARCHAR(250), " + KEY_ID_History + " INTEGER, " +
            " FOREIGN KEY " + "(" + KEY_ID_History + ")" +
            " REFERENCES " + TABLE_History + "(" + KEY_ID_History + ")" + ")";

    // Survey_Users table create statement
    private static final String CREATE_TABLE_Survey_Users = "CREATE TABLE " +TABLE_Survey_Users +
            "(" + KEY_ID_User + " INTEGER, " + KEY_ID_Survey + " INTEGER," +
            " FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey + "(" + KEY_ID_Survey + "),"+
            "FOREIGN KEY " + "(" + KEY_ID_User+ ")" + " REFERENCES " + TABLE_Users+ "(" + KEY_ID_User+ ")," +
            "PRIMARY KEY (" + KEY_ID_User + "," + KEY_ID_Survey + ")" + ")";

    // Survey_Anonymous table create statement
    private static final String CREATE_TABLE_Survey_Anonymous = "CREATE TABLE " + TABLE_Survey_Anonymous+
            "(" + KEY_ID_Survey + " INTEGER," + KEY_ID_User + " INTEGER," + KEY_Date_Of_The_Survey + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            " FOREIGN KEY " + "(" + KEY_ID_Survey+ ")" + " REFERENCES " + TABLE_Survey+ "(" + KEY_ID_Survey+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_User+ ")" + " REFERENCES " + TABLE_Users+ "(" + KEY_ID_User+ ")" + ")";

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
            " DATETIME DEFAULT CURRENT_TIMESTAMP," + KEY_ID_User + " INTEGER," + KEY_ID_Survey + " INTEGER," +
            " FOREIGN KEY " + "(" + KEY_ID_Survey + ")" + " REFERENCES " + TABLE_Survey + "(" + KEY_ID_Survey+ "),"+
            " FOREIGN KEY " + "(" + KEY_ID_User + ")" + " REFERENCES " + TABLE_Users + "(" + KEY_ID_User + ")" + ")";

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
        db.execSQL(CREATE_TABLE_Type_Question);
        db.execSQL(CREATE_TABLE_Question);
        db.execSQL(CREATE_TABLE_Answers_To_Question);
        db.execSQL(CREATE_TABLE_Answers);
        db.execSQL(CREATE_TABLE_Survey_Users);
        db.execSQL(CREATE_TABLE_Survey_Anonymous);
        db.execSQL(CREATE_TABLE_Section_In_Survey);
        db.execSQL(CREATE_TABLE_Question_In_Section);
        db.execSQL(CREATE_TABLE_History);
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Type_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Survey);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Section);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Type_Question);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Question);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers_To_Question);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Answers);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Survey_Users);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Survey_Anonymous);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Section_In_Survey);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Question_In_Section);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_History);

        // create new tables
        onCreate(sqLiteDatabase);
    }

    public Cursor getAllSection() {
        String[] columns = {KEY_ID_Section + " as _id",KEY_Name,KEY_Sequence};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_Section,columns,null,null,null,null,null);
        db.close();
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
            Users user = new Users(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }
    public static Cursor getUsers(SQLiteDatabase db){
        return db.query(TABLE_Users,new String[]{KEY_ID_User + " as _id",KEY_Login},null,null,null,null,null);
    }

    public static List<SurveyList> getSurveyList(SQLiteDatabase db, int Id_User){
        Cursor cursor = db.query(TABLE_Survey_Users,
                new String[]{KEY_ID_Survey},
                KEY_ID_User + " = ?",
                new String[]{Integer.toString(Id_User)},null,null,null);
        if(cursor.getCount() > 0){
            StringBuilder id = new StringBuilder();
            while(cursor.moveToNext()){
                id.append(Integer.toString(cursor.getInt(0))+",");
            }
            id.deleteCharAt(id.length()-1);
            cursor = db.query(TABLE_Survey,
                    new String[]{KEY_ID_Survey,KEY_Name,KEY_Date_Create,KEY_Anonymous},
                    KEY_ID_Survey + " IN ("+id+")",null,null,null,null);
            List <SurveyList> surveyList = new ArrayList<>();
            Cursor cursor1;
            while(cursor.moveToNext()){
                if(cursor.getInt(3) == 0){
                    cursor1 = db.query(TABLE_History,
                            new String[]{"COUNT(*) as total"},
                            KEY_ID_Survey + " = ? AND " + KEY_ID_User + " = ?",
                            new String[]{Integer.toString(cursor.getInt(0)), Integer.toString(Id_User)},
                            KEY_ID_Survey + "," + KEY_ID_User,null,null);
                }
                else{
                    cursor1 = db.query(TABLE_Survey_Anonymous,
                            new String[]{"COUNT(*) as total"},
                            KEY_ID_Survey + " = ? AND " + KEY_ID_User + " = ?",
                            new String[]{Integer.toString(cursor.getInt(0)), Integer.toString(Id_User)},
                            KEY_ID_Survey + "," + KEY_ID_User,null,null);
                }
                cursor1.moveToFirst();
                if(cursor1.getCount() > 0) surveyList.add(new SurveyList(cursor.getInt(0),cursor.getString(1), cursor.getString(2), cursor1.getInt(0)));
                else surveyList.add(new SurveyList(cursor.getInt(0),cursor.getString(1), cursor.getString(2), 0));
                cursor1.close();
            }
            cursor.close();
            return surveyList;
        }
        return null;
    }

    public static Survey getSurvey(SQLiteDatabase db, int id_Survey){
        Cursor cursor = db.query(TABLE_Survey, null,
                KEY_ID_Survey + " = ?",
                new String[]{Integer.toString(id_Survey)},null, null, null);
        cursor.moveToFirst();
        Survey survey = new Survey(cursor.getInt(0),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),cursor.getInt(4),null);
        ArrayList<Integer> list = new ArrayList<>();
        cursor = db.query(TABLE_Section_In_Survey,
                new String[]{KEY_ID_Section},
                KEY_ID_Survey + " = ?",
                new String[]{Integer.toString(id_Survey)}, null,null, KEY_Sequence);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getInt(0));
            }
            ArrayList<Section> sections = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                sections.add(getSection(db,list.get(i),cursor));
            }
            survey.setSections(sections);
            cursor.close();
            return survey;
        }
        else{
            cursor.close();
            return survey;
        }
    }

    public static Section getSection(SQLiteDatabase db, int id_Section, Cursor cursor){
        cursor = db.query(TABLE_Section,null,
                KEY_ID_Section + " = ?",
                new String[]{Integer.toString(id_Section)},null,null,null);
        cursor.moveToFirst();
        Section section = new Section(cursor.getInt(0),cursor.getString(1),null);
        ArrayList<Integer> list = new ArrayList<>();
        cursor = db.query(TABLE_Question_In_Section,
                new String[]{KEY_ID_Question},
                KEY_ID_Section + " = ?",
                new String[]{Integer.toString(id_Section)}, null,null, KEY_Sequence);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                list.add(cursor.getInt(0));
            }
            ArrayList<Question> questions = new ArrayList<>();
            for(int i = 0; i < list.size(); i++){
                questions.add(getQuestion(db, list.get(i),cursor));
            }
            section.setQuestions(questions);
            return section;
        }
        else return section;
    }

    public static Question getQuestion(SQLiteDatabase db, int id_Question, Cursor cursor){
        cursor = db.query(TABLE_Question,null,
                KEY_ID_Question + " = ?",
                new String[]{Integer.toString(id_Question)},null,null,null);
        cursor.moveToFirst();
        int id_type_question = cursor.getInt(4);
        Question question = new Question(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                cursor.getString(3), null, null);
        cursor = db.query(TABLE_Type_Question,
                new String[]{KEY_Name},
                KEY_ID_Type_Question + " = ?",
                new String[]{Integer.toString(id_type_question)},null,null,null);
        cursor.moveToFirst();
        question.setType_Question(cursor.getString(0));

        ArrayList<Answers_To_Question> answers = new ArrayList<>();
        cursor = db.query(TABLE_Answers_To_Question, null,
                KEY_ID_Question + " = ?",
                new String[]{Integer.toString(id_Question)}, null,null, KEY_Sequence);
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                answers.add(new Answers_To_Question(cursor.getInt(0),cursor.getString(1),cursor.getInt(3),cursor.getInt(4)));
            }
        }
        question.setAvailableAnswers(answers);
        return question;
    }

    public static void saveAnswers(SQLiteDatabase db, Survey survey, int ID_User){
        Cursor cursor = db.query(TABLE_History,
                new String[]{"COUNT(*) as total"},null,null,null,null,null);
        cursor.moveToFirst();
        int ID_History = cursor.getInt(0) + 1;
        ContentValues historyValues = new ContentValues();
        if(survey.isAnonymous() == 1){
            historyValues.putNull(KEY_Date_Hour);
            historyValues.putNull(KEY_ID_User);
            ContentValues surveyAnonymousValues = new ContentValues();
            surveyAnonymousValues.put(KEY_ID_Survey,survey.getID_Survey());
            surveyAnonymousValues.put(KEY_ID_User,ID_User);
            db.insert(TABLE_Survey_Anonymous,null,surveyAnonymousValues);
        }
        else{
            historyValues.put(KEY_ID_User, ID_User);
        }
        historyValues.put(KEY_ID_Survey,survey.getID_Survey());
        db.insert(TABLE_History,null,historyValues);
        List<Section> sections = survey.getSections();
        for(int i = 0; i < sections.size(); i++){
            List<Question> questions = sections.get(i).getQuestions();
            for(int j = 0; j < questions.size(); j++){
                Question question = questions.get(j);
                if(question.getGivenAnswers() != null){
                    List<Answers_To_Question> answers = question.getGivenAnswers();
                    for(int k = 0; k < answers.size(); k++){
                        Answers_To_Question answers_to_question = answers.get(k);
                        ContentValues answerValues = new ContentValues();
                        answerValues.put(KEY_ID_Answers_to_Question,answers_to_question.getId_Answers_To_Question());
                        answerValues.put(KEY_ID_History,ID_History);
                        if(answers_to_question.getHas_Text() == 1){
                            answerValues.put(KEY_Text_Answers,answers_to_question.getText());
                        }
                        else{
                            answerValues.putNull(KEY_Text_Answers);
                        }
                        db.insert(TABLE_Answers,null,answerValues);
                    }
                }
            }
        }
        cursor.close();
    }
    public Cursor getCountSurveyHistoryAll(int ID_Survey) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Count(ID_History) from History where ID_Survey="+ID_Survey+";",null);
        return c;
    }
    public Cursor getCountSurveyHistoryUser(int ID_User) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Survey.ID_Survey as _id, Survey.Name, Count(History.ID_Survey)" +
                " as count  from History inner join Survey on History.ID_Survey=Survey.ID_Survey where (History.ID_User="+ID_User+") Group by History.ID_Survey;",null);
        return c;
    }
    public Cursor getListSurveyHistory(int ID_User,int ID_Survey)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("Select Date_Hour from History where ID_User="+ID_User+" AND ID_Survey="+ID_Survey+";",null);
        return c;
    }

}
