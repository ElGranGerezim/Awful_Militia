package com.example.mathattack

import android.content.Context
import android.os.Bundle
import android.util.Log
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
    private var difficulty = 9
    private var qMode = -1
    private val player = Player()
    private lateinit var enemy: Enemy
    private var mainContext: Context? = null
    private var scoreDb: HighScoreRepository? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        scoreDb = (activity as MainActivity).highScore
        mainContext = this.activity?.applicationContext

        setFragmentResultListener("startFragment") { key, bundle ->
            val name = bundle.getString("player_name")
            val mode = bundle.getInt("question_mode")
            player.setName(name!!)
            Log.d("FIRST_FRAG", mode.toString())
            qMode = mode
            Log.d("FIRST_FRAG", qMode.toString())
            binding.playerLabel.text = player.getName()
            refreshEnemy()
            refreshQuestion()
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

    }

    private fun selectAnswer(answer: Int) {
        if (answer == enemy.GetQuestion().getCorrectAnswer()) {
            enemy.takeDamage()
            if (enemy.getHealth() < 1) {
                player.increaseScore()
                Toast.makeText(mainContext, "Enemy Died: ${player.getScore()}", Toast.LENGTH_LONG)
                    .show()
                refreshEnemy()
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

    private fun refreshEnemy(){
        enemy = when (qMode) {
            0    -> AdditionEnemy(difficulty)
            1    -> SubtractionEnemy(difficulty)
            else -> SuperEnemy(difficulty)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}