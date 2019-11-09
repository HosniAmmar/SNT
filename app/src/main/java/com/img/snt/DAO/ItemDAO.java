package com.img.snt.DAO;

import androidx.room.*;

import com.img.snt.Model.Item;

import java.util.List;

@Dao
public interface ItemDAO {

        @Query("SELECT * FROM item")
        List<Item> getAll();

        /* @Query("SELECT * FROM user WHERE uid IN (:userIds)")
        List<Item> loadAllByIds(int[] userIds);

       @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1")
        Item findByName(String first, String last);
        */
        @Query("SELECT * FROM item WHERE id = (:itemId)")
        Item getOneById(String itemId);

        @Query("SELECT * FROM item WHERE language IN (:language) ")
        List<Item> loadAllByLanguage(String language);

        @Query("SELECT COUNT(*) FROM item WHERE id IN (:ids)")
        int countId(String ids);

        @Insert
        void insertAll(Item... items);

        @Delete
        void delete(Item user);

}
