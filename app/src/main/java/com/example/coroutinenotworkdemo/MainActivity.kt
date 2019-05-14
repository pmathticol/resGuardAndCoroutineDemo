package com.example.coroutinenotworkdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clickid.setOnClickListener {
            GlobalScope.async {
                Log.i("pisa","start call async")
                val cost=measureTimeMillis {
                    val result=demoSupendFun()
                    Log.i("pisa","get result=$result")
                    withContext(Dispatchers.Main){
                        textview.text=result
                    }
                }
                Log.i("pisa","cost=$cost")
                0
            }
            Toast.makeText(this,"click result",LENGTH_SHORT)
        }

    }


    suspend fun demoSupendFun(): String {
        return suspendCoroutine {
            //模拟一个异步请求，然后回调，得到结果
            async {
                delay(1000)
                it.resume("get result")
            }
        }
    }
}
