package com.ezzy.wordguesser.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            updateWordText()
            updateScoreText()
        }
        binding.skipButton.setOnClickListener {
            gameViewModel.onSkip()
            updateScoreText()
            updateWordText()
        }
        updateWordText()
        updateScoreText()

        return binding.root
    }

    private fun gameFinished(){
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment()
        findNavController().navigate(action)
    }

    private fun updateWordText(){
        binding.wordText.text = gameViewModel.word
    }

    private fun updateScoreText(){
        binding.scoreText.text = gameViewModel.score.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}