package com.example.mathattack

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.mathattack.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStartBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set a button to get the player's name.
        binding.button.setOnClickListener() {
            val result = binding.editTextName.text.toString()
            Log.d("START_FRAGMENT", result)
            val mode = when(binding.modeRadioGroup.checkedRadioButtonId){
                binding.modeAdditionRadio.id -> 0
                binding.modeSubtractionRadio.id -> 1
                else -> -1
            }
            // Set player name to be accessible to other fragments.
            setFragmentResult("startFragment", bundleOf("player_name" to result, "question_mode" to mode))
            findNavController().navigate(R.id.action_startFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}