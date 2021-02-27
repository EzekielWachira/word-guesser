package com.ezzy.wordguesser.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ezzy.wordguesser.R
import com.ezzy.wordguesser.databinding.ActivityMainBinding
import com.ezzy.wordguesser.databinding.FragmentScoreBinding

class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)

        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        binding.scoreText.text = scoreFragmentArgs.score.toString()
        binding.playAgainButton.setOnClickListener {
            onPlayAgain()
        }

        return binding.root
    }

    private fun onPlayAgain(){
        findNavController().navigate(ScoreFragmentDirections.actionScoreFragmentToGameFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}