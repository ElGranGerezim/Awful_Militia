package com.example.mathattack.database

import androidx.room.*

// https://gabrieltanner.org/blog/android-room/

@Entity(tableName = "high_score")
data class HighScore(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "user_name") val name: String,
    @ColumnInfo(name = "score") val score: Int,
)

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_score")
    fun getAll(): List<HighScore>

    @Query("SELECT * FROM high_score WHERE uid in (:high_scoreIds)")
    fun loadAllByIds(high_scoreIds: IntArray): List<HighScore>

    @Query("SELECT * FROM high_score WHERE user_name LIKE :userName LIMIT 1")
    fun findByUserName(userName: String): HighScore

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg highScores: HighScore)

    @Delete
    fun delete(highScore: HighScore)
}