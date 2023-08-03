package com.serialization

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.TextView
import com.serialization.model.CharacterParcelable
import com.serialization.model.CharacterSerializable
import kotlinx.android.synthetic.main.activity_main.container
import java.io.Serializable
import kotlin.system.measureTimeMillis

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        runTestWithCount(10_0)

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

    private fun performParcelable(testCount: Int) {
        for (i in 0..testCount) {
            val p  = intent.extras?.parcelable<CharacterParcelable>("parse_$i")
        }
    }

    private fun performSerializable(testCount: Int) {
        for (i in 0..testCount) {
            val s = intent.extras?.serializable("serial_$i") as CharacterSerializable?
        }
    }

    private fun runTestWithCount(count: Int) {
        logResult("Serializable", measureTimeMillis { performSerializable(count) }, count, "MS")
        logResult("Parcelable", measureTimeMillis { performParcelable(count) }, count, "MS")


    }
}
inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}
inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}