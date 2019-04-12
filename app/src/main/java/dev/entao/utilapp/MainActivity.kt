package dev.entao.utilapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dev.entao.json.YsonObject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val yo = YsonObject(
            """{
            |"name":"Yang",
            |"addr":"China"
            |}""".trimMargin()
        )
        println(yo.str("name"))
        println(yo.toString())
    }


}
