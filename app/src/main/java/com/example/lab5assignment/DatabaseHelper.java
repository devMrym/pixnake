package com.example.lab5assignment;

// Import statements bring in necessary parts of the Android framework and Java libraries.
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.*;

// DatabaseHelper class extends SQLiteOpenHelper, a helper class to manage database creation and version management.
public class DatabaseHelper extends SQLiteOpenHelper {
    // Constants for the database name and version. These are used to identify and manage the database.
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 1;
    // The context allows access to application-specific resources and classes.
    private final Context context;
    // A reference to the SQLiteDatabase, which represents the database itself.
    SQLiteDatabase db;

    // The path where the database is stored on the Android device.
    private static final String DATABASE_PATH = "data/data/com.example.lab5assignment/databases/";
    // Name of the table used for storing user information.
    private final String USER_TABLE = "user";

    // Constructor for DatabaseHelper. It sets up the database environment when an instance is created.
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        // Calls createDb() to ensure the database is set up when the helper is initialized.
        createDb();
    }

    // onCreate is called the first time a database is accessed and is used to create tables and populate initial data.
    @Override
    public void onCreate(SQLiteDatabase db){
        // This is empty because the database will be copied from assets, not created from scratch.
    }

    // onUpgrade is called if the database version on the device is lower than DATABASE_VERSION. Used for upgrading the database.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Implement schema changes and data migration here.
    }

    // Checks if the database exists on the device. If not, it copies the pre-made database from assets.
    public void createDb() {
        boolean dbExist = checkDbExist();

        if (!dbExist) {
            // This will create an empty database into the default system path of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            // Copy the database from assets.
            copyDatabase();
        }
    }

    // checkDbExist checks if the database already exists to avoid re-copying the file each time.
    private boolean checkDbExist(){
        SQLiteDatabase sqLiteDatabase = null;

        try{
            String path = DATABASE_PATH + DATABASE_NAME;
            // Attempt to open the database at the specified path.
            sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception ex){
            // The database does not exist yet.
        }
        if (sqLiteDatabase != null){
            sqLiteDatabase.close();
            // Return true because the database exists.
            return true;
        }

        // Return false because the database does not exist.
        return false;
    }

    // Copies your database from the local 'assets' folder to the just created empty database in the system folder, from where it can be accessed and handled.
    private void copyDatabase (){
        try {
            // Open your local db as the input stream.
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db.
            String outFileName = DATABASE_PATH + DATABASE_NAME;

            // Open the empty db as the output stream.
            OutputStream outputStream = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file.
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            // Close the streams.
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    // Open the database.
    private SQLiteDatabase openDatabase(){
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    // Close the database to free up resources and ensure data integrity.
    public void close(){
        if (db != null){
            db.close();
        }
    }

    // checkUserExist checks if a user exists in the database by username and password.
    public boolean checkUserExist(String username, String pass){
        String [] columns = {"username"};
        // Open the database for reading.
        db = openDatabase();

        // Define a selection criteria where "username" and "pass" match the parameters passed into the method.
        String selection = "username=? and pass = ?";
        String[] selectionArgs = {username, pass};

        // Query the user table to find if any row matches the selection criteria.
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        // Close the cursor and database to free up resources.
        cursor.close();
        close();

        // If count > 0, it means a match was found. Return true in that case.
        return count > 0;
    }

}

