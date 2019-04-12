@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

class YsonBool(val data: Boolean) : YsonValue() {


	override fun yson(buf: StringBuilder) {
		if (data) {
			buf.append("true")
		} else {
			buf.append("false")
		}
	}

	override fun equals(other: Any?): Boolean {
		if (other is YsonBool) {
			return other.data == data
		}
		return false
	}

	override fun hashCode(): Int {
		return data.hashCode()
	}

	override fun preferBufferSize(): Int {
		return 8
	}

	companion object {
		val True: YsonBool = YsonBool(true)
		val False: YsonBool = YsonBool(false)
	}
}