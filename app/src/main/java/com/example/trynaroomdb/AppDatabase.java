package com.example.trynaroomdb;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Card.class, Payment.class, Type.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CardDao cardDao();

    public abstract PaymentDao paymentDao();

    public abstract TypeDao typeDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context){
        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mpt.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
