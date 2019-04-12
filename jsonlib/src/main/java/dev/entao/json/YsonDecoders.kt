@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

import dev.entao.base.MyDate
import java.math.BigDecimal
import java.math.BigInteger

interface IFromYson {
	fun fromYsonValue(yv: YsonValue): Any?
}

object BoolFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Boolean? {
		return when (yv) {
			is YsonBool -> yv.data
			is YsonNum -> yv.data.toInt() == 1
			is YsonString -> yv.data == "true"
			else -> null
		}
	}
}

object ByteFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Byte? {
		if (yv is YsonNum) {
			return yv.data.toByte()
		}
		if (yv is YsonString) {
			return yv.data.toByteOrNull()
		}
		return null
	}
}

object ShortFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Short? {
		if (yv is YsonNum) {
			return yv.data.toShort()
		}
		if (yv is YsonString) {
			return yv.data.toShortOrNull()
		}
		return null
	}
}

object IntFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Int? {
		if (yv is YsonNum) {
			return yv.data.toInt()
		}
		if (yv is YsonString) {
			return yv.data.toIntOrNull()
		}
		return null
	}
}

object LongFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Long? {
		if (yv is YsonNum) {
			return yv.data.toLong()
		}
		if (yv is YsonString) {
			return yv.data.toLongOrNull()
		}
		return null
	}
}

object BigIntegerFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): BigInteger? {
		if (yv is YsonNum) {
			return BigInteger(yv.data.toString())
		}
		if (yv is YsonString) {
			return yv.data.toBigIntegerOrNull()
		}
		return null
	}
}

object FloatFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Float? {
		if (yv is YsonNum) {
			return yv.data.toFloat()
		}
		if (yv is YsonString) {
			return yv.data.toFloatOrNull()
		}
		return null
	}
}

object DoubleFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Double? {
		if (yv is YsonNum) {
			return yv.data.toDouble()
		}
		if (yv is YsonString) {
			return yv.data.toDoubleOrNull()
		}
		return null
	}
}

object BigDecimalFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): BigDecimal? {
		if (yv is YsonNum) {
			return BigDecimal(yv.data.toDouble())
		}
		if (yv is YsonString) {
			return yv.data.toBigDecimalOrNull()
		}
		return null
	}
}

object CharFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): Char? {
		if (yv is YsonString) {
			return yv.data.firstOrNull()
		}
		return null
	}
}

object StringFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): String? {
		if (yv is YsonString) {
			return yv.data
		} else if (yv is YsonBlob) {
			return yv.encoded
		}
		return null
	}
}

//

object StringBufferFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): StringBuffer? {
		if (yv is YsonString) {
			return StringBuffer(yv.data)
		} else if (yv is YsonBlob) {
			return StringBuffer(yv.encoded)
		}
		return null
	}
}

object StringBuilderFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): StringBuilder? {
		if (yv is YsonString) {
			return StringBuilder(yv.data)
		} else if (yv is YsonBlob) {
			return StringBuilder(yv.encoded)
		}
		return null
	}
}

object DateFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): java.util.Date? {
		if (yv is YsonString) {
			val d = MyDate.parseDateTime(yv.data) ?: return null
			return java.util.Date(d.time)
		} else if (yv is YsonNum) {
			return java.util.Date(yv.data.toLong())
		}
		return null
	}
}

object SQLDateFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): java.sql.Date? {
		if (yv is YsonString) {
			val d = MyDate.parseDate(yv.data) ?: return null
			return java.sql.Date(d.time)
		} else if (yv is YsonNum) {
			return java.sql.Date(yv.data.toLong())
		}
		return null
	}
}

object SQLTimeFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): java.sql.Time? {
		if (yv is YsonString) {
			val d = MyDate.parseTime(yv.data) ?: return null
			return java.sql.Time(d.time)
		} else if (yv is YsonNum) {
			return java.sql.Time(yv.data.toLong())
		}
		return null
	}
}

object SQLTimestampFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): java.sql.Timestamp? {
		if (yv is YsonString) {
			val d = MyDate.parseDateTime(yv.data) ?: return null
			return java.sql.Timestamp(d.time)
		} else if (yv is YsonNum) {
			return java.sql.Timestamp(yv.data.toLong())
		}
		return null
	}
}

object BoolArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): BooleanArray? {
		return (yv as? YsonArray)?.toBoolArray()
	}
}

object ByteArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): ByteArray? {
		if (yv is YsonBlob) {
			return yv.data
		} else if (yv is YsonString) {
			return YsonBlob.decode(yv.data)
		}
		return (yv as? YsonArray)?.toByteArray()
	}
}

object ShortArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): ShortArray? {
		return (yv as? YsonArray)?.toShortArray()
	}
}

object IntArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): IntArray? {
		return (yv as? YsonArray)?.toIntArray()
	}
}

object LongArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): LongArray? {
		return (yv as? YsonArray)?.toLongArray()
	}
}

object FloatArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): FloatArray? {
		return (yv as? YsonArray)?.toFloatArray()
	}
}

object DoubleArrayFromYson : IFromYson {
	override fun fromYsonValue(yv: YsonValue): DoubleArray? {
		return (yv as? YsonArray)?.toDoubleArray()
	}
}
