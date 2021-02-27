package com.ezzy.wordguesser.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val TAG = "GameViewModel"
    var word = ""
    var score = 0
    private lateinit var wordList: MutableList<String>
    init {
        Log.i(TAG, ": Game View Model Created ")
        resetList()
        nextWord()
    }

    private fun resetList(){
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord(){
        if (wordList.isEmpty()){
//
        }  else {
            word = wordList.removeAt(0)
        }
    }

    fun onSkip(){
        score--
        nextWord()
    }

    fun onCorrect(){
        score++
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: Game view model creared")
    }
}