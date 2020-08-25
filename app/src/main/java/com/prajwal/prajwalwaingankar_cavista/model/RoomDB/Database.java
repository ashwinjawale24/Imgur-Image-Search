package com.prajwal.prajwalwaingankar_cavista.model.RoomDB;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Prajwal Waingankar
 * on 25-Aug-20.
 * Github: prajwalmw
 */

@androidx.room.Database(entities = CommentModel.class, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {

    public static Database database1;
    public abstract Comment_DAO comment_dao();

    /**
     *
     * @param context the context passed to the Database instance
     * @return Database
     */
    public static synchronized Database getInstance(Context context)
    {
        if(database1 == null)
        {
            database1 = Room
                    .databaseBuilder(context.getApplicationContext(), Database.class,"cavista_prajwal.db")
                    .build();
        }

        return database1;
    }
}
