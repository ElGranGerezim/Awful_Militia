package com.example.mathattack

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.mathattack.database.HighScoreRepository
import com.example.mathattack.databinding.FragmentFirstBinding
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var question: Question = AdditionQuestion(arrayOf(0, 0));
    private var difficulty = 9;
    private val player = Player()
    private var enemy = Enemy()
    private var mainContext: Context? = null
    private var scoreDb: HighScoreRepository? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scoreDb = (activity as MainActivity).highScore
        mainContext = this.activity?.applicationContext

        setFragmentResultListener("startFragment") { key, bundle ->
            val result = bundle.getString("player_name")
            player.setName(result!!)
            binding.playerLabel.text = player.getName()
        }

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rightButton.setOnClickListener {
            //Snackbar.make(view, binding.rightButton.text, Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            val answer = binding.rightButton.text.toString().toInt()
            selectAnswer(answer)
        }

        binding.leftButton.setOnClickListener {
            //Snackbar.make(view, binding.leftButton.text, Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
            val answer = binding.leftButton.text.toString().toInt()
            selectAnswer(answer)
        }

        binding.midButton.setOnClickListener {
            //Snackbar.make(view, binding.midButton.text, Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
            val answer = binding.midButton.text.toString().toInt()
            selectAnswer(answer)
        }

        binding.refreshButton.setOnClickListener {
            refreshQuestion()
        }

        binding.playerDisplay.text = player.getHealth().toString()
        binding.enemyDisplay.text = enemy.getHealth().toString()
        binding.scoreDisplay.text = player.getScore().toString()
        refreshQuestion()
    }

    private fun selectAnswer(answer: Int) {
        if (answer == question.getCorrectAnswer()) {
            enemy.takeDamage()
            if (enemy.getHealth() < 1) {
                player.increaseScore()
                Toast.makeText(mainContext, "Enemy Died: ${player.getScore()}", Toast.LENGTH_LONG)
                    .show()
                this.enemy = Enemy()
            }

        } else {
            player.takeDamage()
            if (player.getHealth() == 0) {
                val result = 0
                player.saveHighScore(this.scoreDb!!)
                Toast.makeText(mainContext, "Player Died", Toast.LENGTH_LONG).show()
                setFragmentResult("firstFragment", bundleOf("score" to result))
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }

        refreshQuestion()
    }

    private fun refreshQuestion() {
        var numbs = arrayOf<Int>()
        numbs += Random.nextInt(difficulty + 1);
        numbs += Random.nextInt(difficulty + 1)
        question = when (Random.nextBoolean()) {
            true -> AdditionQuestion(numbs)
            false -> SubtractionQuestion(numbs);
        }
        binding.textviewFirst.text = question.getText()
        val answers = question.getAnswers()
        binding.rightButton.text = answers[0].toString()
        binding.midButton.text = answers[1].toString()
        binding.leftButton.text = answers[2].toString()
        binding.playerDisplay.text = player.getHealth().toString()
        binding.enemyDisplay.text = enemy.getHealth().toString()
        binding.scoreDisplay.text = player.getScore().toString()
    }

    private fun submitAnswer(answer: Int) {
        if (question.getCorrectAnswer() == answer) {
            enemy.takeDamage()
            refreshQuestion()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}