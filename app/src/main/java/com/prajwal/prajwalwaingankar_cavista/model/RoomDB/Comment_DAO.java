package com.prajwal.prajwalwaingankar_cavista.model.RoomDB;

import androidx.room.Dao;
import androidx.room.Insert;

/**
 * Created by Prajwal Waingankar
 * on 25-Aug-20.
 * Github: prajwalmw
 */

@Dao
public interface Comment_DAO {

    @Insert
    public void InsertComment(CommentModel commentModel);
}
