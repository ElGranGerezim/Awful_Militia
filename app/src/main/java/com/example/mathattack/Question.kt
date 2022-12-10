package com.example.mathattack

import kotlin.random.Random
import kotlin.random.nextInt

/*
* Base class to contain a question
* Stores all numbers that are a part of it
* and generates answers/stores correct answer
* */
abstract class Question(val numbs: Array<Int>) {
    // For use in display. Overridden in child classes.
    protected var symbol: Char = '?';

    // How far +/- wrong answers can be from right answer
    private var answerSwing: Int = 5;

    // Returns question in string form
    fun getText(): String {
        // Empty string to start with
        var question: String = ""

        for (n in numbs) {
            // Put the number in the string
            question += n;
            question += " ";

            // Add the symbol
            question += symbol;
            question += " ";
        }

        // Drop the last space and symbol since we don't need them, add =
        return question.dropLast(2) + '='
    }

    // Returns a set including 1 correct answer and 2 wrong ones, in a random order.
    fun getAnswers(): Array<Int> {
        // Empty array to start
        var answers = arrayOf<Int>()

        // Add the right answer
        answers += getCorrectAnswer()

        // Add one random wrong answer
        answers += answers[0] + Random.nextInt(-answerSwing..answerSwing)

        // Keep generating wrong answers until we get one that's not already in the set.
        while (answers.size < 3) {
            val possible3rd = answers[0] + Random.nextInt(-answerSwing..answerSwing)
            if (!answers.contains(possible3rd)) {
                answers += possible3rd
            }
        }

        // Scramble em
        answers.shuffle()
        return answers
    }

    // Returns the correct answer. Needs to be overridden in child classes.
    abstract fun getCorrectAnswer(): Int;
}

// Contains an addition question, x + y + ... + z
class AdditionQuestion(numbs: Array<Int>) : Question(numbs) {
    // Set symbol to addittion
    init {
        symbol = '+';
    }

    // Add all the numbers together to get the right one.
    override fun getCorrectAnswer(): Int {
        return numbs.sum();
    }
}

// Contains a subtraction question, x - y - ... - z
class SubtractionQuestion(numbs: Array<Int>) : Question(numbs) {
    init {
        symbol = '-'
    }

    // Subtraction moment
    override fun getCorrectAnswer(): Int {
        // Start with the first number in question
        var answer = numbs[0]

        // Subtract each number from the first
        for (num in 1 until numbs.size) {
            answer -= numbs[num];
        }

        // Give it to em
        return answer;
    }
}

// Returns new addition question limited by difficulty
fun generateAdditionQuestion(difficulty: Int): AdditionQuestion {
    var numbs = arrayOf<Int>()
    numbs += Random.nextInt(difficulty + 1);
    numbs += Random.nextInt(difficulty + 1)
    return AdditionQuestion(numbs)
}

// Returns a new subtraction question limited by difficulty
fun generateSubtractionQuestion(difficulty: Int): SubtractionQuestion {
    var numbs = arrayOf<Int>()
    numbs += Random.nextInt(difficulty + 1)
    numbs += Random.nextInt(numbs[0] + 1)
    return SubtractionQuestion(numbs)
}