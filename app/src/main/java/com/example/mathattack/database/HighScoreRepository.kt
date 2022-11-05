package com.example.mathattack.database

import android.content.Context
import android.os.AsyncTask

// https://github.com/velmurugan-murugesan/Android-Example/tree/master/RoomAndroidExample/app/src/main/java/com/example/roomandroidexample

class HighScoreRepository(context: Context) {
    var db: HighScoreDao = AppDatabase.getInstance(context)?.HighScoreDao()!!

    fun getAllUsers(): List<HighScore> {
        return db.getAll()
    }

    fun getByIds(ids: IntArray): List<HighScore> {
        return db.loadAllByIds(ids)
    }

    fun getByUsername(username: String): HighScore {
        return db.findByUserName(username)
    }

    fun insertUser(highScore: HighScore) {
        insertAsyncTask(db).execute(highScore)
    }

    private class insertAsyncTask internal constructor(private val highScoreDao: HighScoreDao) :
        AsyncTask<HighScore, Void, Void>() {
        override fun doInBackground(vararg params: HighScore): Void? {
            highScoreDao.insertAll(params[0])
            return null
        }
    }
}