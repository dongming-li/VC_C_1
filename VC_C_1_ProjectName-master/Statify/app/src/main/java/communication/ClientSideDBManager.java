package Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Class responsible for communicating with the SQLite database for client-side storage
 */
public class ClientSideDBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Statify.db";

    /**
     * Creates a local Manager that utilizes SQLite
     * @param context Passed context for constructor
     */
    public ClientSideDBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.onCreate(getWritableDatabase());
    }

    /**
     * On creation create a table if it doesn't already exist for layouts and the user
     * @param sqLiteDatabase Passed in sqlite database
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "create table if not exists Layouts " +
                        "(LayoutTitle text primary key, LayoutData text)"
        );

        sqLiteDatabase.execSQL(
                "create table if not exists User " +
                        "(UserID text primary key, Username text, Rank int)"
        );
    }


    /**
     * Adds all tables to an arraylist and if the table is equal to sqlite sequence, drop it and create a new database from the passed one
     * @param sqLiteDatabase Passed in sqlite database
     * @param i Int passed in
     * @param i1 Int passed in
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }

        for (String table : tables) {
            if(!table.equals("sqlite_sequence")){
                String dropQuery = "DROP TABLE IF EXISTS " + table;
                sqLiteDatabase.execSQL(dropQuery);
            }
        }
        onCreate(sqLiteDatabase);
    }

    /**
     * Updates  the table with the title and data if it exists already
     * Adds to the table if the title and data don't already exist
     * @param title Title passed in to update layouts
     * @param data Data passed in to update layouts
     * @return True if update layout was successful
     */
    public boolean updateLayout(String title, String data) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("LayoutTitle", title);
        contentValues.put("LayoutData", data);

        // Which row to update, based on the title

        db.insertWithOnConflict("Layouts", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }

    /**
     * This method looks at the number at the front of the string passed and updates the database with
     * Multiple titles and data
     * @param data Passed in data to update layouts
     * @return true if update Layouts was successful
     */
    public boolean updateLayouts(String data){
        Scanner scan = new Scanner(data);
        scan.useDelimiter(";");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        int numberOfLayouts = scan.nextInt();

        for(int i = 0; i < numberOfLayouts; i++){
            contentValues.put("LayoutTitle", scan.next());
            contentValues.put("LayoutData", scan.next());
            db.insertWithOnConflict("Layouts", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        }

        return true;
    }


    /**
     * This method pulls a layout from the table based off of the title passed in
     * @param title Title passed in to update layouts
     * @return The String pulled based off the title passed
     */
    public String pullLayout(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select LayoutData from Layouts where LayoutTitle=\""+title+"\"", null );
        String out = "";

        if(res.moveToFirst()){
            out = res.getString(res.getColumnIndex("LayoutData"));}
    return out;
    }

    /**
     * Pulls all titles from the layouts table and packs them into a string to be parsed later on down the chain
     * @return True if pull titles was successful
     */
    public String pullTitles() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select LayoutTitle from Layouts", null );
        String out = "";
        if(res.moveToFirst()) {
            out += res.getString(res.getColumnIndex("LayoutTitle"));
        }
        while(res.moveToNext()){
            out += "," + res.getString(res.getColumnIndex("LayoutTitle"));

        }
        return out;
    }

    /**
     * Create the calculated Data Table to store varibles and expressions
     * @param activityName String passed in for new DataTable
     * @return True if the creating the data table was true
     */
    public boolean createCalculateDataTable(String activityName){
        SQLiteDatabase db = this.getWritableDatabase();
//        activityName = activityName.replace(" ", "");
        db.execSQL(
                "create table if not exists " + activityName + "_CalculateData " +
                        "(id integer primary key autoincrement, DataPointName text, DataExpression text)"
        );
        return true;
    }

    /**
     * Updates a calculated data point with new data and expressions
     * @param activityName Passed in activity name for new data point
     * @param dataName Passed in dataName for new data Point
     * @param dataExpression Passed in dataExpression for data Point
     * @return true if the data point was added
     */
    public boolean updateCalculatedDataPoint(String activityName, String dataName, String dataExpression){
        SQLiteDatabase db = this.getWritableDatabase();
//        activityName = activityName.replace(" ", "");

        ContentValues contentValues = new ContentValues();
        contentValues.put("DataPointName", dataName);
        contentValues.put("DataExpression", dataExpression);

        // Which row to update, based on the title

        db.replace(activityName + "_CalculateData", null, contentValues);
        return true;
    }

    /**
     * Pulls all calculated data point titles and spits them out in an arrayList
     * @param activityName  Passed in activity name that is used to pull calculated data points
     * @return Arraylist of data point titles
     */
    public ArrayList<String> pullCalculateDataPointTitles(String activityName){
        ArrayList<String> dataPointTitles = new ArrayList<String>();
//        activityName = activityName.replace(" ", "");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DataPointName from " + activityName + "_CalculateData ORDER BY id ASC", null );
        if(res.moveToFirst()) {
            dataPointTitles.add(res.getString(res.getColumnIndex("DataPointName")));
        }
        while(res.moveToNext()){
            dataPointTitles.add(res.getString(res.getColumnIndex("DataPointName")));

        }

        return dataPointTitles;
    }

    /**
     * Pulls the calculated data point expressions from the table and returns them in an arraylist
     * @param activityName  Passed activity name to pull data point expression
     * @return ArrayList of data point expressions
     */
    public ArrayList<String> pullCalculateDataPointExpressions(String activityName){
        ArrayList<String> dataPointExpressions = new ArrayList<String>();
//        activityName = activityName.replace(" ", "");

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select DataExpression from " + activityName + "_CalculateData ORDER BY id ASC", null );
        if(res.moveToFirst()) {
            String toAdd = res.getString(res.getColumnIndex("DataExpression"));
            dataPointExpressions.add(toAdd);
        }
        while(res.moveToNext()){
            String toAdd = res.getString(res.getColumnIndex("DataExpression"));
            dataPointExpressions.add(toAdd);
        }

        return dataPointExpressions;
    }

    /**
     * Unused method for deleting if bowling calculate data or golf calculate data exists
     */
    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("drop table if exists Bowling_CalculateData");
        db.execSQL("drop table if exists Golf_CalculateData");
//        db.execSQL("drop table if exists Layouts");

    }

    /**
     * Clears user data that was previously stored, and replaces it with the
     * input user data
     *
     * @param userID Passed in userID to update userTable
     * @param username Passed in username to update userTable
     * @param rank Passed in rank to update userTable
     */
    public void updateUserData(String userID, String username, int rank){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from User");
        ContentValues contentValues = new ContentValues();


        contentValues.put("UserID", userID);
        contentValues.put("Username", username);
        contentValues.put("Rank", rank);
        db.insertWithOnConflict("User", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    /**
     * gets the user data in the form of a string array list
     * @return Arraylist of userdata
     */
    public ArrayList<String> getUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> userData = new ArrayList<String>();


        Cursor res =  db.rawQuery( "select * from User", null );
        if(res.moveToFirst()) {
            userData.add(res.getString(res.getColumnIndex("UserID")));
            userData.add(res.getString(res.getColumnIndex("Username")));
            userData.add(Integer.toString(res.getInt(res.getColumnIndex("Rank"))));
        }

        return userData;
    }

    /**
     * Updates a calculated data point with new data that is passed in
     * @param data  Passed in string to update Calculated data point
     */
    public void updateCalculatedData(String data){
        Scanner scan = new Scanner(data);
        scan.useDelimiter("#");

        Scanner activityScanner = new Scanner(pullTitles());
        activityScanner.useDelimiter(",");
        while(scan.hasNext()){
            Scanner in = new Scanner(scan.next());
            in.useDelimiter(";");

            String currentActivity = activityScanner.next();
            createCalculateDataTable(currentActivity);
            while(in.hasNext()){
                updateCalculatedDataPoint(currentActivity, in.next(), in.next());
            }
        }
    }

    /**
     * Delete method to delete title from the passed name
     * @param name passed in name to identify table to delete
     * @return true if table delete was successful
     */
    public boolean deleteTitle(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        {
            return db.delete("Layouts", "LayoutTitle" + "=" + name, null) > 0;
        }
    }
}