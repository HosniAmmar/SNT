package com.img.snt;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.img.snt.DAO.AppDatabase;
import com.img.snt.Model.Item;

import java.util.List;

class DatabaseInitializer {
    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db,Item item) {
        PopulateDbAsync task = new PopulateDbAsync(db,item);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db,Item item) {
        populateWithTestData(db,item);
    }

    private static Item addUser(final AppDatabase db, Item user) {
        db.itemDAO().insertAll(user);
        return user;
    }

    private static void populateWithTestData(AppDatabase db,Item item) {

        addUser(db, item);

        List<Item> userList = db.itemDAO().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        Item item;
        PopulateDbAsync(AppDatabase db,Item ite) {
            mDb = db;
            item=ite;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb,item);
            return null;
        }

    }
}
