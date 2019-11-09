package com.img.snt.DAO;

import android.content.Context;

import androidx.room.*;

import com.img.snt.Model.Item;

@Database(entities = {Item.class}, version = 14,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDAO itemDAO();
    private static AppDatabase INSTANCE;


    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database-snt")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}