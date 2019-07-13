package dev.entao.utilapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.entao.kan.json.YsonObject

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
