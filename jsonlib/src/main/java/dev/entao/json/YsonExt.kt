@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

import yet.ext.ITextConvert
import kotlin.reflect.KClass

object YsonObjectText : ITextConvert {
    override val defaultValue: Any = YsonObject()
    override fun fromText(text: String): Any? {
        return YsonObject(text)
    }
}

object YsonArrayText : ITextConvert {
    override val defaultValue: Any = YsonArray()
    override fun fromText(text: String): Any? {
        return YsonArray(text)
    }
}


@Suppress("UNCHECKED_CAST")
fun <T> KClass<*>.createYsonModel(argValue: YsonObject): T {
    val c =
        this.constructors.first { it.parameters.size == 1 && it.parameters.first().type.classifier == YsonObject::class }
    return c.call(argValue) as T
}