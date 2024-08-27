package com.revnoah.pottytime.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.revnoah.pottytime.dao.CategoryDao;
import com.revnoah.pottytime.dao.EntryDao;
import com.revnoah.pottytime.model.Category;
import com.revnoah.pottytime.model.Entry;

import java.util.concurrent.Executors;

@Database(entities = {Category.class, Entry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract CategoryDao categoryDao();
    public abstract EntryDao entryDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "potty-time-db")
                            .addCallback(roomCallback)  // Add the callback for seeding
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Execute database operations asynchronously
            Executors.newSingleThreadExecutor().execute(() -> {
                // Get the database instance
                AppDatabase database = INSTANCE;
                // Insert seed data
                CategoryDao categoryDao = database.categoryDao();
                categoryDao.insert(new Category("blank", "Dry", 1));
                categoryDao.insert(new Category("+", "Urine in Toilet", 2));
                categoryDao.insert(new Category("A", "Urine Accident in Pants", 3));
                categoryDao.insert(new Category("BMP", "Bowel Movement in Pants", 4));
                categoryDao.insert(new Category("BMT", "Bowel Movement in Toilet", 5));
            });
        }
    };
}
