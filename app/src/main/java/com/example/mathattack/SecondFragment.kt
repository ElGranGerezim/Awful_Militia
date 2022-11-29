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
    private var mainContext: Context? = null
    private var scoreDb: HighScoreRepository? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        scoreDb = (activity as MainActivity).highScore
        mainContext = this.activity?.applicationContext

        val arrayAdapter: ArrayAdapter<*>
        val highScores = scoreDb!!.getAllScores()
        val listView = binding.highScoreList
        arrayAdapter = ArrayAdapter(
            mainContext!!,
            android.R.layout.simple_list_item_1, // XML item layout id. https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
            highScores
        )
        listView.adapter = arrayAdapter


        setFragmentResultListener("startFragment") { _, bundle ->
            val name = bundle.getString("name")
            println("$name received")
        }

        setFragmentResultListener("firstFragment") { _, bundle ->
            val score = bundle.getInt("score")
            println("$score received")
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