package com.example.mathattack

// Class to represent enemies
open class Enemy {
    private var health = 5

    fun getHealth() : Int { return health }
    fun takeDamage() {
        health -= 1
        if (this.health < 0) {
            this.health = 0
        }
    }
}