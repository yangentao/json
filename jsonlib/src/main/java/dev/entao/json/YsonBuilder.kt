@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

/**
 * Created by entaoyang@163.com on 2016-12-29.
 */


class YsonObjectBuilder {
    val jo = YsonObject()

    fun toJson(): String {
        return jo.toString()
    }

    infix fun <V> String.to(value: V) {
        jo.any(this, value)
    }


    infix fun String.to(value: YsonObject) {
        jo.obj(this, value)
    }

    infix fun String.to(value: YsonArray) {
        jo.arr(this, value)
    }


}

fun ysonObject(block: YsonObjectBuilder.() -> Unit): YsonObject {
    val b = YsonObjectBuilder()
    b.block()
    return b.jo
}

fun ysonArray(values: Collection<Any?>): YsonArray {
    val arr = YsonArray()
    for (v in values) {
        arr.addAny(v)
    }
    return arr
}

fun ysonArray(vararg values: Any): YsonArray {
    val arr = YsonArray()
    for (v in values) {
        arr.addAny(v)
    }
    return arr
}

fun <T> ysonArray(values: Collection<T>, block: (T) -> Any?): YsonArray {
    val arr = YsonArray()
    for (v in values) {
        arr.addAny(block(v))
    }
    return arr
}