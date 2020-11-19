package com.serialization

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.serialization.model.CharacterParcelable
import com.serialization.model.CharacterSerializable
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    benchmarkButton.setOnClickListener {
      benchMark()
    }
  }

  private fun benchMark() {
    if (container.childCount > 1){
      container.removeViews(1, container.childCount - 1)
    }

    runTestWithCount(10)
    runTestWithCount(100)
    runTestWithCount(1_000)
    runTestWithCount(10_000)
//    runTestWithCount(50_000)
  }

  private fun runTestWithCount(count: Int){
    val parcelableTime = measureNanoTime { performParcelable(count) }
    logResult("Parcelable", parcelableTime, count)

    val serializableTime = measureNanoTime { performSerializable(count) }
    logResult("Serializable", serializableTime, count)
  }

  private fun performParcelable(testCount: Int) {
    val parcelable = CharacterParcelable()
    val bundle = bundleOf()
    for (i in 0..testCount) {
      bundle.putParcelable(i.toString(), parcelable)
    }
    for (i in 0..testCount) {
      bundle.getParcelable<CharacterParcelable>(i.toString())
    }
  }

  private fun performSerializable(testCount: Int) {
    val serializable = CharacterSerializable()
    val bundle = bundleOf()
    for (i in 0..testCount) {
      bundle.putSerializable(i.toString(), serializable)
    }
    for (i in 0..testCount) {
      bundle.getSerializable(i.toString()) as CharacterSerializable
    }
  }

  private fun logResult(testSubject: String, time: Long, count: Int) {
    val message = "$count items: $time ns for $testSubject"
    addText(message)
  }

  private fun addText(message: String){
    val textView = TextView(this)
    textView.text = message
    container.addView(textView)
  }
}