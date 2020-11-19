package com.serialization

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  companion object {
    private const val TAG = "BENCHMARK"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    benchmarkButton.setOnClickListener {
      benchMark()
    }
  }

  fun benchMark() {
    if (container.childCount > 1){
      container.removeViews(1, container.childCount - 1)
    }

    runTestWithCount(1_000)
    runTestWithCount(10_000)
    runTestWithCount(50_000)
  }

  private fun runTestWithCount(count: Int){
    val parcelableTime = measureTime { performParcelable(count) }
    logResult("Parcelable", parcelableTime, count)

    val serializableTime = measureTime { performSerializable(count) }
    logResult("Serializable", serializableTime, count)
  }

  private fun performParcelable(testCount: Int) {
    val parcelable = CharacterParcelable()
    val bundle = bundleOf()
    for (i in 0..testCount) {
      bundle.putParcelable("parcelable$i", parcelable)
    }
    for (i in 0..testCount) {
      bundle.getParcelable<CharacterParcelable>("parcelable$i")
    }
  }

  private fun performSerializable(testCount: Int) {
    val serializable = CharacterSerializable()
    val bundle = bundleOf()
    for (i in 0..testCount) {
      bundle.putSerializable("serializable$i", serializable)
    }
    for (i in 0..testCount) {
      bundle.getSerializable("serializable$i") as CharacterSerializable
    }
  }

  private fun measureTime(measured: () -> Unit): Long {
    val startTime = System.currentTimeMillis()
    measured.invoke()
    val endTime = System.currentTimeMillis()
    return endTime - startTime
  }

  private fun logResult(testSubject: String, time: Long, count: Int) {
    val message = "$count items: $time ms $testSubject took to perform"
//    Log.d(TAG, message)
    addText(message)
  }

  private fun addText(message: String){
    val textView = TextView(this)
    textView.text = message
    container.addView(textView)
  }
}