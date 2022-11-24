package com.example.mathattack

import android.util.Log
import com.example.mathattack.database.HighScore
import com.example.mathattack.database.HighScoreRepository

// Class to represent player status
class Player {
    private var health = 5
    private var score = 0
    private var name = "Test Player"
    private val TAG = "Player"

    fun getHealth(): Int {
        return this.health
    }

    fun getScore(): Int {
        return this.score
    }

    fun takeDamage() {
        this.health -= 1
        if (this.health < 0) {
            this.health = 0
        }
        Log.d(TAG, "takeDamage ${this.health}")
    }

    fun saveHighScore(highScore: HighScoreRepository) {
        val newHighScore = HighScore(name = this.name, score = this.score)
        Log.d(this.TAG, "$newHighScore")
        highScore.insertHighScore(newHighScore)
    }

    fun increaseScore() {
        this.score += 1
        Log.d(TAG, "increaseScore ${this.score}")
    }
}