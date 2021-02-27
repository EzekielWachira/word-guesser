package com.ezzy.wordguesser.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val TAG = "GameViewModel"
    var word = MutableLiveData<String>()
    var score = MutableLiveData<Int>()
    private lateinit var wordList: MutableList<String>
    init {
        Log.i(TAG, ": Game View Model Created ")
        resetList()
        nextWord()
        score.value = 0
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
            word.value = wordList.removeAt(0)
        }
    }

    fun onSkip(){
        score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect(){
        score.value = (score.value)?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: Game view model creared")
    }
}