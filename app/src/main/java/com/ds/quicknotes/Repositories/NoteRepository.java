package com.ds.quicknotes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ds.quicknotes.Databases.NoteDatabase;
import com.ds.quicknotes.Entities.Note;
import com.ds.quicknotes.Utils.NoteDAO;

import java.util.List;

public class NoteRepository {

    // Private Attributes
    private NoteDAO noteDAO;
    private LiveData<List<Note>> allNotes;

    // Public Constructor
    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDAO = database.noteDAO();
        allNotes = noteDAO.getAllNotes();
    }

    // Public Methods

    /**
     * Gets a list of all note objects from the database through the entities DAO
     * @return LiveData list of note objects
     */
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /**
     * Inserts a note into the database on a separate thread through a privately defined AsyncTask using the NoteDAO
     * @param note Note object to be inserted into the database
     */
    public void insert(Note note){
        //create a background asynchronous task
        new InsertNoteAsyncTask(this.noteDAO)
                .execute(note);
    }

    /**
     * Updates a note in the database on a separate thread through a privately defined AsyncTask using the NoteDAO
     * @param note Note object to be updated in the database
     */
    public void update(Note note){
        new UpdateNoteAsyncTask(this.noteDAO)
                .execute(note);
    }

    /**
     * Deletes a note in the database on a separate thread through a privately defined AsyncTask using the NoteDAO
     * @param note Note object to be deleted from the database
     */
    public void delete(Note note){
        new DeleteNoteAsyncTask(this.noteDAO)
                .execute(note);
    }

    /**
     * Deletes all notes in the database on a separate thread through a privately defined AsyncTask using the NoteDAO
     */
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(this.noteDAO)
                .execute();
    }


    // Private Classes
    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDAO noteDAO;

        //assign private noteDAO variable through private method
        private InsertNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            //In background insert a note using the noteDAO.insert function
            noteDAO.insert(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDAO noteDAO;

        //assign private noteDAO variable through private method
        private DeleteNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            //In background insert a note using the noteDAO.insert function
            noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDAO noteDAO;

        //assign private noteDAO variable through private method
        private UpdateNoteAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            //In background insert a note using the noteDAO.insert function
            noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDAO noteDAO;

        private DeleteAllNotesAsyncTask(NoteDAO noteDAO){
            this.noteDAO = noteDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.deleteAll();
            return null;
        }
    }

}
