package com.ezzy.wordguesser.viewmodel

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {
    private val TAG = "GameViewModel"

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val _word = MutableLiveData<String>()
    val word: LiveData<String> get() = _word

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean> get() = _eventGameFinished

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long> get() = _currentTime

    private val _buzzEvent = MutableLiveData<BuzzType>()
    val buzzEvent: LiveData<BuzzType> get() = _buzzEvent

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean> get() = _eventGameFinish
    
    val currentTimeString = Transformations.map( currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private lateinit var wordList: MutableList<String>

    companion object {
        private const val COUNTDOWN_PANIC_SECONDS = 10L
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 60000L
    }

    private val timer: CountDownTimer

    init {
        Log.i(TAG, ": Game View Model Created ")
        resetList()
        nextWord()
        _score.value = 0
        _eventGameFinished.value = false
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _buzzEvent.value = BuzzType.GAME_OVER
                _eventGameFinished.value = true
            }

        }

        timer.start()
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
            resetList()
            _eventGameFinished.value = true
        }
        _word.value = wordList.removeAt(0)

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

    fun onBuzzComplete(){
        _buzzEvent.value = BuzzType.NO_BUZZ
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i(TAG, "onCleared: Game view model creared")
    }
}