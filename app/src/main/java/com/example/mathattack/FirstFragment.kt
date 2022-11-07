package com.example.mathattack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mathattack.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var question : Question = AdditionQuestion(arrayOf(0,0));
    private var difficulty = 9;
    private val player = Player()
    private val enemy = Enemy()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

        binding.refreshButton.setOnClickListener{
            refreshQuestion()
        }

        binding.playerDisplay.text = player.getHealth().toString()
        binding.enemyDisplay.text = enemy.getHealth().toString()
        refreshQuestion()
    }

    private fun selectAnswer(answer: Int){
        if (answer == question.GetCorrectAnswer()){
            enemy.takeDamage()
        } else {
            player.takeDamage()
        }

        refreshQuestion()
    }

    private fun refreshQuestion(){
        var numbs = arrayOf<Int>()
        numbs += Random.nextInt(difficulty + 1);
        numbs += Random.nextInt(difficulty + 1)
        question = when(Random.nextBoolean()){
            true-> AdditionQuestion(numbs)
            false-> SubtractionQuestion(numbs);
        }
        binding.textviewFirst.text = question.GetText()
        val answers = question.GetAnswers()
        binding.rightButton.text = answers[0].toString()
        binding.midButton.text = answers[1].toString()
        binding.leftButton.text = answers[2].toString()
        binding.playerDisplay.text = player.getHealth().toString()
        binding.enemyDisplay.text = enemy.getHealth().toString()
    }

    private fun submitAnswer(answer : Int){
        if (question.GetCorrectAnswer() == answer){
            enemy.takeDamage()
            refreshQuestion()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}