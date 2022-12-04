package com.example.mathattack

/**
 * A class representing the enemy the payer will be fighting against.
 */
open class Enemy {
    private var health = 5

    /**
     * Gets the health of the enemy.
     */
    fun getHealth(): Int {
        return health
    }

    /**
     * Reduce the health of the enemy by one point.
     */
    fun takeDamage() {
        health -= 1
        if (this.health < 0) {
            this.health = 0
        }
    }
}