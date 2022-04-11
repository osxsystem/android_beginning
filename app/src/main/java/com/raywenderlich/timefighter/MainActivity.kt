package com.raywenderlich.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log

import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
  private val TAG = MainActivity::class.java.simpleName

  private lateinit var gameScoreTextView: TextView
  private lateinit var timeLeftTextView: TextView
  private lateinit var tapMeBtn: Button
  private  var score = 0
  private var gameStart = false
  private lateinit var countDownTimer: CountDownTimer
  private var initialCountDown: Long = 15000
  private var countDownInterval: Long = 1000
  private var timeLeft = 60

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Log.d(TAG, "onCreate call === score: $score")
    gameScoreTextView = findViewById(R.id.game_score_text_view)
    gameScoreTextView.text = getString(R.string.your_score, score)

    timeLeftTextView = findViewById(R.id.time_left_text_view)
    timeLeftTextView.text = getString(R.string.time_left, timeLeft)

    tapMeBtn = findViewById(R.id.tap_me_btn)
    tapMeBtn.setOnClickListener { incrementScore() }
    resetGame()
  }

  override fun onSaveInstanceState(outState: Bundle) {

    super.onSaveInstanceState(outState)

    outState.putInt(SCORE_KEY, score)
    outState.putInt(TIME_LEFT_KEY, timeLeft)
    countDownTimer.cancel()

    Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left: $timeLeft")
  }

  override fun onDestroy() {
    super.onDestroy()

    Log.d(TAG, "onDestroy called.")
  }

  private fun incrementScore() {
    if(!gameStart) {
      startGame()
    }
    score++
    val newScore = getString(R.string.your_score, score)
    gameScoreTextView.text = newScore
  }

  private fun resetGame() {
    // reset game logic
    score = 0
    val initialScore = getString(R.string.your_score, score)
    gameScoreTextView.text = initialScore

    val initialTimeLeft = getString(R.string.time_left, 60)
    timeLeftTextView.text = initialTimeLeft

    countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval) {
      override fun onTick(millisUntilFinished: Long) {
        timeLeft = millisUntilFinished.toInt() / 1000
        val timeLeftString = getString(R.string.time_left, timeLeft)
        timeLeftTextView.text = timeLeftString
      }

      override fun onFinish() {
        endGame()
      }
    }
    // 4
    gameStart = false
  }

  private fun startGame() {
    // start game logic
    countDownTimer.start()
    gameStart = true
  }

  private fun endGame() {
    Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
    resetGame()
  }

  // 1
  companion object {

    private const val SCORE_KEY = "SCORE_KEY"

    private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
  }
}