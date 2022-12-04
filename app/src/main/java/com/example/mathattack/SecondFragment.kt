package com.example.mathattack

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.mathattack.database.HighScoreRepository
import com.example.mathattack.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Get the main context from MainActivity.
    private var mainContext: Context? = null

    // Initiate the database for high scores.
    private var scoreDb: HighScoreRepository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        // Get database session.
        scoreDb = (activity as MainActivity).highScore
        // Get context from MainActivity.
        mainContext = this.activity?.applicationContext

        // Set up an adapter to display a list of items on the screen.
        val arrayAdapter: ArrayAdapter<*>
        val highScores = scoreDb!!.getAllScores()
        var formattedScore: MutableList<String?> = mutableListOf()
        for (score in highScores) {
            formattedScore.add(
                "${score.name}: ${score.score}"
            )
        }
        val listView = binding.highScoreList
        arrayAdapter = ArrayAdapter(
            mainContext!!,
            android.R.layout.simple_list_item_1, // XML item layout id. https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
            formattedScore
        )
        listView.adapter = arrayAdapter


        // Get the name of player from start fragment.
        setFragmentResultListener("startFragment") { _, bundle ->
            val name = bundle.getString("name")
        }

        // Get the score of the player from first fragment.
        setFragmentResultListener("firstFragment") { _, bundle ->
            val score = bundle.getInt("score")
        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_startFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}