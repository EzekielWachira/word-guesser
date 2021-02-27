package com.ezzy.wordguesser.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.getSystemService
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

//        binding.gameViewModel = gameViewModel
//        binding.lifecycleOwner = this

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

        gameViewModel.eventGameFinished.observe(viewLifecycleOwner, Observer {
            isGameFinished ->
                if (isGameFinished) {
                    gameFinished()
                    gameViewModel.onGameFinishComplete()
                }
        })

        gameViewModel.currentTime.observe(viewLifecycleOwner, Observer {
            currentTime ->
                binding.timerText.text = DateUtils.formatElapsedTime(currentTime).toString()
        })

        gameViewModel.buzzEvent.observe(viewLifecycleOwner, Observer { buzzType ->
            if (buzzType != GameViewModel.BuzzType.NO_BUZZ){
                buzz(buzzType.pattern)
                gameViewModel.onBuzzComplete()
            }
        })

        return binding.root
    }

    private fun gameFinished(){
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(
            gameViewModel.score.value ?: 0
        )
        findNavController().navigate(action)
//        Toast.makeText(this.activity, "Game Has Finished", Toast.LENGTH_SHORT).show()
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()

        buzzer?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}