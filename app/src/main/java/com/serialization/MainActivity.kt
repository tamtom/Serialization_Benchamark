package com.serialization

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.serialization.model.CharacterParcelable
import com.serialization.model.CharacterSerializable
import kotlinx.android.synthetic.main.activity_main.benchmarkButton
import kotlinx.android.synthetic.main.activity_main.container
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NANO = "nanos"
        private const val MS = "ms"
    }
    val bundle = bundleOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benchmarkButton.setOnClickListener {
            benchMark()
        }
    }


    private val runCount = 100 // Specify the number of times to run the operation

    private fun benchMark() {
        if (container.childCount > 1) {
            container.removeViews(1, container.childCount - 1)
        }

        //runTestWithCount(10, NANO)
        //runTestWithCount(100, NANO)
        //runTestWithCount(1_000, MS)
        runTestWithCount(10_0, NANO)
        //runTestWithCount(50_000, MS)
        startActivity(Intent(this, SecondActivity::class.java).apply { putExtras(bundle) })
    }

    private fun measureNanoRepeatedly(block: () -> Unit): Long {
        val runTimes = mutableListOf<Long>()
        for (i in 0 until runCount) {
            val time = measureNanoTime { block() }
            runTimes.add(time)
        }
        return runTimes.average().toLong()

    }

    private fun measureMillisRepeatedly(block: () -> Unit): Long {
        val runTimes = mutableListOf<Long>()
        for (i in 0 until runCount) {
            val time = measureTimeMillis { block() }
            runTimes.add(time)
        }
        return runTimes.average().toLong()
    }

    private fun runTestWithCount(count: Int, timeFormat: String) {
        if (timeFormat == NANO) {
            logResult("Parcelable", measureNanoRepeatedly { performParcelable(count) }, count, timeFormat)

            logResult("Serializable", measureNanoRepeatedly { performSerializable(count) }, count, timeFormat)
        } else {

            logResult("Parcelable", measureTimeMillis { performParcelable(count) }, count, timeFormat)

            logResult("Serializable", measureTimeMillis { performSerializable(count) }, count, timeFormat)
        }
    }

    private fun performParcelable(testCount: Int) {
        val parcelable = CharacterParcelable()
        for (i in 0..testCount) {
            bundle.putParcelable("parse_$i", parcelable)
        }
   /*     for (i in 0..testCount) {
            bundle.getParcelable<CharacterParcelable>(i.toString())
        }*/
    }

    private fun performSerializable(testCount: Int) {
        val serializable = CharacterSerializable()
        for (i in 0..testCount) {
            bundle.putSerializable("serial_$i", serializable)

        }
      /*  for (i in 0..testCount) {
            bundle.getSerializable(i.toString()) as CharacterSerializable
        }*/
    }

    private fun logResult(testSubject: String, time: Long, count: Int, timeFormat: String) {
        val message = "$count items: $time $timeFormat for $testSubject"
        addText(message)
    }

    private fun addText(message: String) {
        val textView = TextView(this)
        textView.text = message
        container.addView(textView)
    }
}