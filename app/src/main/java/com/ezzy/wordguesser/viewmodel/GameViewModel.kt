package com.ezzy.wordguesser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val TAG = "GameViewModel"
    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean> get() = _eventGameFinished

    private lateinit var wordList: MutableList<String>
    init {
        Log.i(TAG, ": Game View Model Created ")
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinished.value = false
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
            _eventGameFinished.value = true
        }  else {
            _word.value = wordList.removeAt(0)
        }
    }

    fun onSkip(){
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect(){
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    fun onGameFinishComplete(){
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: Game view model creared")
    }
}