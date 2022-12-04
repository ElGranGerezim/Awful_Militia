package com.example.mathattack

import android.util.Log
import com.example.mathattack.database.HighScore
import com.example.mathattack.database.HighScoreRepository

/**
 * A class representing the Player.
 */
class Player {
    private var health = 5
    private var score = 0
    private var name = ""
    private val TAG = "Player"

    /**
     * Get the health of the player.
     */
    fun getHealth(): Int {
        return this.health
    }

    /**
     * Get the score of the player.
     */
    fun getScore(): Int {
        return this.score
    }

    /**
     * Get the name of the player.
     */
    fun getName(): String {
        Log.d(TAG, "getName")
        return this.name
    }

    /**
     * Decrease the player's health by one.
     */
    fun takeDamage() {
        this.health -= 1
        if (this.health < 0) {
            this.health = 0
        }
        Log.d(TAG, "takeDamage ${this.health}")
    }

    /**
     * Save the high score of the player to the database.
     */
    fun saveHighScore(highScore: HighScoreRepository) {
        val newHighScore = HighScore(name = this.name, score = this.score)
        Log.d(this.TAG, "$newHighScore")
        highScore.insertHighScore(newHighScore)
    }

    /**
     * Increase the score of the player by one.
     */
    fun increaseScore() {
        this.score += 1
        Log.d(TAG, "increaseScore ${this.score}")
    }

    /**
     * Set the name of the player.
     */
    fun setName(name: String) {
        this.name = name
    }
}