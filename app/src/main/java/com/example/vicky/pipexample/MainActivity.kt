package com.example.vicky.pipexample

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.graphics.Point
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Rational
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {


    val timer = Timer()
    var sec = 0
    lateinit var task :TimerTask
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this
        task = object: TimerTask(){
            override fun run() {
                sec++
                activity.runOnUiThread {
                    if(sec<=10){
                        if(sec>9){
                            textview.text ="00 : $sec"
                        }else{
                            textview.text ="00 : 0$sec"
                        }
                    }else{
                        timer.cancel()
                        timer.purge()
                    }
                }
            }
        }
        textview.visibility = View.GONE

        button.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mpipParams = PictureInPictureParams.Builder()
                val display = windowManager.defaultDisplay
                val point = Point()
                display.getSize(point)
                mpipParams.setAspectRatio(Rational(point.x,point.y))
                enterPictureInPictureMode(mpipParams.build())
            }
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        if(isInPictureInPictureMode){
            textview.visibility = View.VISIBLE
            button.visibility = View.GONE
            sec = 0
            timer.schedule(task,0,1000)
            supportActionBar?.hide()
        }else{
            textview.visibility = View.GONE
            button.visibility = View.VISIBLE
            supportActionBar?.show()
        }
    }

}
