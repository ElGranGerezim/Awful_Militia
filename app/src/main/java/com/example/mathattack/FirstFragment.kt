package com.example.mathattack

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.mathattack.database.HighScoreRepository
import com.example.mathattack.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var difficulty = 9
    private var qMode = -1

    // Get an array of addition questions
    private var question: Question = AdditionQuestion(arrayOf(0, 0));

    // Initiate the player class
    private val player = Player()

    // Initiate the enemy class
    private lateinit var enemy: Enemy

    // Get the MainActivity context for movement of data between fragments and more.
    private var mainContext: Context? = null

    // Prepare to load in High Score Repository for queries and inserts into the database.
    private var scoreDb: HighScoreRepository? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get database session from MainActivity
        scoreDb = (activity as MainActivity).highScore
        // Set Main Activity Context
        mainContext = this.activity?.applicationContext

        // Set listener for the first fragment to receive any stored data from the start fragment.
        setFragmentResultListener("startFragment") { key, bundle ->
            val name = bundle.getString("player_name")
            val mode = bundle.getInt("question_mode")
            player.setName(name!!)
            Log.d("FIRST_FRAG", mode.toString())
            qMode = mode
            Log.d("FIRST_FRAG", qMode.toString())

            // Genereate Enemy and Question
            refreshEnemy()
            refreshQuestion()

            // Bind values to titles at top of string for player name, score and enemy health.
            binding.playerLabel.text = player.getName()
            binding.playerDisplay.text = player.getHealth().toString()
            binding.enemyDisplay.text = enemy.getHealth().toString()
            binding.scoreDisplay.text = player.getScore().toString()
            binding.playerHealthBar.max = player.getHealth()
            binding.playerHealthBar.progress = player.getHealth()
        }

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add a button to screen that will display a possible answer to the question.
        binding.rightButton.setOnClickListener {
            val answer = binding.rightButton.text.toString().toInt()
            selectAnswer(answer)
        }

        // Add a button to screen left of previous button containing a possible answer.
        binding.leftButton.setOnClickListener {
            val answer = binding.leftButton.text.toString().toInt()
            selectAnswer(answer)
        }

        // Add a button between previous buttons containing a possible answer.
        binding.midButton.setOnClickListener {
            val answer = binding.midButton.text.toString().toInt()
            selectAnswer(answer)
        }

        // Add button to refresh screen and show a new question.
        binding.refreshButton.setOnClickListener {
            refreshQuestion()
        }
    }

    /**
     * When an answer is selected check if it is the correct answer and perform an action based on
     * whether the choice was correct or not.
     *
     * @param answer: Int => The chosen answer by the user.
     */
    private fun selectAnswer(answer: Int) {

        // If the answer is correct, damage the enemy.
        if (answer == enemy.GetQuestion().getCorrectAnswer()) {
            enemy.takeDamage()
            binding.enemyHealthBar.progress = enemy.getHealth()
            if (enemy.getHealth() < 1) {
                player.increaseScore()
                refreshEnemy()
            }

        } else {
            // If the answer is incorrect damage the player
            player.takeDamage()
            binding.playerHealthBar.progress = player.getHealth()
            // If the player health is zero, save high score and move to game over screen.
            if (player.getHealth() == 0) {
                val result = 0
                player.saveHighScore(this.scoreDb!!)
                setFragmentResult("firstFragment", bundleOf("score" to result))
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        // Show new question.
        refreshQuestion()
    }

    /**
     * Generate a new question for the Player to answer.
     */
    private fun refreshQuestion() {
        enemy.newQuestion(difficulty)
        binding.textviewFirst.text = enemy.GetQuestion().getText()
        val answers = enemy.GetQuestion().getAnswers()
        binding.rightButton.text = answers[0].toString()
        binding.midButton.text = answers[1].toString()
        binding.leftButton.text = answers[2].toString()
        binding.playerDisplay.text = player.getHealth().toString()
        binding.enemyDisplay.text = enemy.getHealth().toString()
        binding.scoreDisplay.text = player.getScore().toString()
    }

    private fun refreshEnemy() {
        enemy = when (qMode) {
            0 -> AdditionEnemy(difficulty)
            1 -> SubtractionEnemy(difficulty)
            else -> SuperEnemy(difficulty)
        }
        binding.enemyHealthBar.max = enemy.getHealth()
        binding.enemyHealthBar.progress = enemy.getHealth()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}