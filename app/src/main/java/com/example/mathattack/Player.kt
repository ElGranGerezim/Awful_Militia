package com.example.mathattack

// Class to represent player status
class Player {
    private var health = 5

    public fun getHealth() : Int { return health }

    public fun takeDamage(){
        health -= 1
    }
}