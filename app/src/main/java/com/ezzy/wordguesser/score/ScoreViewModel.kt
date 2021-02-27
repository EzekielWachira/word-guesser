package com.ezzy.wordguesser.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    private val TAG = "ScoreViewModel"
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score
    init {
        Log.i(TAG, ":Final Score $finalScore")
        _score.value = finalScore
    }
}