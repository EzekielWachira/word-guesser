package com.ezzy.wordguesser.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ezzy.wordguesser.R
import com.ezzy.wordguesser.databinding.ActivityMainBinding
import com.ezzy.wordguesser.databinding.FragmentGameBinding
import com.ezzy.wordguesser.viewmodel.GameViewModel
import java.lang.StringBuilder

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null

    private val binding get() = _binding!!

    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(
            inflater, container, false
        )

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java);


        binding.correctButton.setOnClickListener {
            gameViewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            gameViewModel.onSkip()
        }

        gameViewModel.score.observe(viewLifecycleOwner, Observer<Int> { newScore: Int? ->
            binding.scoreText.text = newScore.toString()
        })

        gameViewModel.word.observe(viewLifecycleOwner, Observer<String> {
            newWord: String? ->
                binding.wordText.text = newWord
        })

        return binding.root
    }

    private fun gameFinished(){
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(
            gameViewModel.score.value ?: 0
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}