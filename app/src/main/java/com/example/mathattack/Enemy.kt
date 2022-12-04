package com.example.mathattack

import kotlin.random.Random

/**
 * A class representing the enemy the payer will be fighting against.
 */
abstract class Enemy {
    // Number of questions left until enemy dies
    private var health = 5
    protected abstract var question: Question

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

    fun GetQuestion(): Question {
        return question
    }

    // Generates new question
    abstract fun newQuestion(difficulty: Int): Question
}

// Enemy that only creates addition questions
class AdditionEnemy(difficulty: Int) : Enemy() {
    override var question: Question = newQuestion(difficulty)
    override fun newQuestion(difficulty: Int): Question {
        question = GenerateAdditionQuestion(difficulty)
        return question
    }
}

// Enemy that only creates subtraction questions
class SubtractionEnemy(difficulty: Int) : Enemy() {
    override var question: Question = newQuestion(difficulty)
    override fun newQuestion(difficulty: Int): Question {
        question = GenerateSubtractionQuestion(difficulty)
        return question
    }
}

// Enemy that can create any type of question at random.
class SuperEnemy(difficulty: Int) : Enemy() {
    override var question: Question = newQuestion(difficulty)
    override fun newQuestion(difficulty: Int): Question {
        if (Random.nextBoolean()) {
            question = GenerateAdditionQuestion(difficulty)
            return question
        } else {
            question = GenerateSubtractionQuestion(difficulty)
            return question
        }
    }
}