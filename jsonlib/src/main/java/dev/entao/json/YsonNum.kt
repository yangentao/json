@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

class YsonNum(val data: Number) : YsonValue() {

	override fun yson(buf: StringBuilder) {
		buf.append(data.toString())
	}

	override fun equals(other: Any?): Boolean {
		if (other is YsonNum) {
			return other.data == data
		}
		return false
	}

	override fun hashCode(): Int {
		return data.hashCode()
	}

	override fun preferBufferSize(): Int {
		return 12
	}

}