@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

import android.util.Base64

class YsonBlob(val data: ByteArray) : YsonValue() {

	constructor(v: java.sql.Blob) : this(v.getBytes(1, v.length().toInt()))

	override fun yson(buf: StringBuilder) {
		buf.append("\"")
		buf.append(encoded)
		buf.append("\"")
	}

	val encoded: String get() = YsonBlob.encode(data)

	override fun equals(other: Any?): Boolean {
		if (other is YsonBlob) {
			return other.data.contentEquals(data)
		}
		return false
	}

	override fun hashCode(): Int {
		return data.hashCode()
	}

	override fun preferBufferSize(): Int {
		return data.size * 4 / 3 + 4
	}

	companion object {

		fun encode(data: ByteArray): String {
			return Base64.encodeToString(data, Base64.URL_SAFE)
//			val e = Base64.getUrlEncoder()
//			return e.encodeToString(data)
		}

		fun decode(s: String): ByteArray {
			return Base64.decode(s, Base64.URL_SAFE)
//			return Base64.getUrlDecoder().decode(s)
		}

	}

}