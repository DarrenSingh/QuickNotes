package com.ds.quicknotes;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    /*Steps to create Singleton
    *
    * Define private static variable of class
    * Set an empty private constructor
    * Define a public method to get the instance variable of the class
    */

    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO();

    //synchronized means only 1 thread at a time can access this instance
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,"quicknotes_database")
                    .fallbackToDestructiveMigration()
                    // add callback to onCreate db seeder
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //Private Methods
    /**
     *override on the RoomDatabase.callback, initiates an async dbseed oncreate
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new SeedDatabaseAsyncTask(instance)
                    .execute();
        }
    };

    /**
     * Private ASyncTask to seed the database with values. Occurs off the main thread
     */
    private static class SeedDatabaseAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDAO noteDAO;

        private SeedDatabaseAsyncTask(NoteDatabase db){
            this.noteDAO = db.noteDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new Note("Seeded Note 1","This note was seeded into the database",1));
            noteDAO.insert(new Note("Seeded Note 2","This note was seeded into the database",3));
            noteDAO.insert(new Note("Seeded Note 3","This note was seeded into the database",2));
            noteDAO.insert(new Note("Seeded Note 4","This note was seeded into the database",1));
            return null;
        }
    }


}
