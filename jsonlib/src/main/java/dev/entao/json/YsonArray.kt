@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package dev.entao.json

import yet.ext.TextConverts

class YsonArray(val data: ArrayList<YsonValue> = ArrayList(16)) : YsonValue(), MutableList<YsonValue> by data {

	constructor(capcity: Int) : this(ArrayList<YsonValue>(capcity))

	constructor(json: String) : this() {
		val v = YsonParser(json).parse(true)
		if (v is YsonArray) {
			data.addAll(v.data)
		}
	}

	override fun yson(buf: StringBuilder) {
		buf.append("[")
		for (i in data.indices) {
			if (i != 0) {
				buf.append(",")
			}
			data[i].yson(buf)
		}
		buf.append("]")
	}

	override fun preferBufferSize(): Int {
		return data.sumBy { it.preferBufferSize() }
	}

	override fun toString(): String {
		return yson()
	}

	fun toBoolArray(): BooleanArray {
		return this.map { (it as YsonBool).data }.toBooleanArray()
	}

	fun toByteArray(): ByteArray {
		return this.map { (it as YsonNum).data.toByte() }.toByteArray()
	}

	fun toShortArray(): ShortArray {
		return this.map { (it as YsonNum).data.toShort() }.toShortArray()
	}

	fun toIntArray(): IntArray {
		return this.map { (it as YsonNum).data.toInt() }.toIntArray()
	}

	fun toLongArray(): LongArray {
		return this.map { (it as YsonNum).data.toLong() }.toLongArray()
	}

	fun toFloatArray(): FloatArray {
		return this.map { (it as YsonNum).data.toFloat() }.toFloatArray()
	}

	fun toDoubleArray(): DoubleArray {
		return this.map { (it as YsonNum).data.toDouble() }.toDoubleArray()
	}

	fun toCharArray(): CharArray {
		return this.map { (it as YsonString).data.first() }.toCharArray()
	}

	fun toStringArray(): Array<String> {
		return this.map { (it as YsonString).data }.toTypedArray()
	}

	fun toByteList(): List<Byte> {
		return this.map { (it as YsonNum).data.toByte() }
	}

	fun toShortList(): List<Short> {
		return this.map { (it as YsonNum).data.toShort() }
	}

	fun toIntList(): List<Int> {
		return this.map { (it as YsonNum).data.toInt() }
	}

	fun toLongList(): List<Long> {
		return this.map { (it as YsonNum).data.toLong() }
	}

	fun toFloatList(): List<Float> {
		return this.map { (it as YsonNum).data.toFloat() }
	}

	fun toDoubleList(): List<Double> {
		return this.map { (it as YsonNum).data.toDouble() }
	}

	fun toCharList(): List<Char> {
		return this.map { (it as YsonString).data.first() }
	}

	fun toStringList(): List<String> {
		return this.map { (it as YsonString).data }
	}

	fun toObjectList(): List<YsonObject> {
		return this.map { it as YsonObject }
	}

	fun add(value: String?) {
		if (value == null) {
			add(YsonNull.inst)
		} else {
			add(YsonString(value))
		}
	}

	fun add(value: Boolean?) {
		if (value == null) {
			add(YsonNull.inst)
		} else {
			add(YsonBool(value))
		}
	}

	fun add(value: Int?) {
		if (value == null) {
			add(YsonNull.inst)
		} else {
			add(YsonNum(value))
		}
	}

	fun add(value: Long?) {
		if (value == null) {
			add(YsonNull.inst)
		} else {
			add(YsonNum(value))
		}
	}

	fun add(value: Float?) {
		add(value?.toDouble())
	}

	fun add(value: Double?) {
		if (value == null) {
			add(YsonNull.inst)
		} else {
			add(YsonNum(value))
		}
	}

	fun addBlob(value: ByteArray?) {
		if (value == null) {
			add(YsonNull.inst)
		} else {
			add(YsonBlob(value))
		}
	}

	fun addAny(value: Any?) {
		when (value) {
			null -> add(YsonNull.inst)
			is YsonValue -> add(value)
			is String -> add(value)
			is Boolean -> add(value)
			is Byte -> add(value.toInt())
			is Short -> add(value.toInt())
			is Int -> add(value)
			is Long -> add(value)
			is Float -> add(value.toDouble())
			is Double -> add(value)
			is ByteArray -> add(YsonBlob(value))
			else -> add(value.toString())
		}

	}

	inline fun eachObject(block: (YsonObject) -> Unit) {
		for (item in this) {
			block(item as YsonObject)
		}
	}

	inline fun <R> mapObject(transform: (YsonObject) -> R): ArrayList<R> {
		val ls = ArrayList<R>(this.size)
		for (yo in this) {
			ls += transform(yo as YsonObject)
		}
		return ls
	}

	companion object {
		init {
			TextConverts[YsonArray::class] = YsonArrayText
		}
	}
}