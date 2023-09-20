package com.kupesan.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kupesan.data.model.Event

@Dao
interface EventDao {

    @Query("SELECT * FROM event WHERE countryCode == :countryCode")
    fun loadAllByCountryCode(countryCode: String): List<Event>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(events: List<Event>)
}