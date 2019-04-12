@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

class YsonError(msg: String = "YsonError") : Exception("Json解析错误, $msg") {

    constructor(msg: String, text: String, pos: Int) : this(
        msg + ", " + if (pos < text.length) text.substring(pos, Math.min(pos + 20, text.length)) else text
    )
}