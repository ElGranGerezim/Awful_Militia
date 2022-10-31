package com.example.mathattack

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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
            Snackbar.make(view, R.string.right_button_text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.leftButton.setOnClickListener {
            Snackbar.make(view, R.string.left_button_text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.midButton.setOnClickListener {
            Snackbar.make(view, R.string.mid_button_text, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        binding.refreshButton.setOnClickListener{
            refreshQuestion()
        }

        refreshQuestion()
    }

    fun refreshQuestion(){
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}