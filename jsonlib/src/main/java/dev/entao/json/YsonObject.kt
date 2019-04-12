@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package dev.entao.json

import dev.entao.base.defaultValue
import dev.entao.base.nameProp
import yet.ext.TextConverts
import yet.ext.defaultValueOfProperty
import yet.ext.strToV
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty

class YsonObject(val data: LinkedHashMap<String, YsonValue> = LinkedHashMap(32)) : YsonValue(), MutableMap<String, YsonValue> by data {

	constructor(capcity: Int) : this(LinkedHashMap<String, YsonValue>(capcity))

	constructor(json: String) : this() {
		val p = YsonParser(json)
		val v = p.parse(true)
		if (v is YsonObject) {
			data.putAll(v.data)
		}
	}

	override fun yson(buf: StringBuilder) {
		buf.append("{")
		var first = true
		for ((k, v) in data) {
			if (!first) {
				buf.append(",")
			}
			first = false
			buf.append("\"").append(escapeJson(k)).append("\":")
			v.yson(buf)
		}
		buf.append("}")
	}

	override fun preferBufferSize(): Int {
		return 256
	}

	override fun toString(): String {
		return yson()
	}

	private val _changedProperties = ArrayList<KMutableProperty<*>>(8)
	private var gather: Boolean = false

	@Synchronized
	fun gather(block: () -> Unit): ArrayList<KMutableProperty<*>> {
		this.gather = true
		this._changedProperties.clear()
		block()
		val ls = ArrayList<KMutableProperty<*>>(_changedProperties)
		this.gather = false
		return ls
	}
	operator fun <V> setValue(thisRef: Any?, property: KProperty<*>, value: V) {
		this[property.nameProp] = Yson.toYson(value)
		if (this.gather) {
			if (property is KMutableProperty) {
				if (property !in this._changedProperties) {
					this._changedProperties.add(property)
				}
			}
		}
	}

	@Suppress("UNCHECKED_CAST")
	operator fun <V> getValue(thisRef: Any?, property: KProperty<*>): V {
		val retType = property.returnType
		val v = this.get(property.nameProp) ?: YsonNull.inst
		if (v !is YsonNull) {
			val pv = YsonDecoder.decodeByType(v, retType, null)
			if (pv != null || retType.isMarkedNullable) {
				return pv as V
			}
		}
		if (retType.isMarkedNullable) {
			return null as V
		}
		val defVal = property.defaultValue
		if (defVal != null) {
			return strToV(defVal, property)
		}
		return defaultValueOfProperty(property)
	}

	fun removeProperty(p: KProperty<*>) {
		this.remove(p.nameProp)
	}

	fun str(key: String, value: String?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, YsonString(value))
		}
	}

	fun str(key: String): String? {
		val v = get(key)
		return when (v) {
			null -> null
			is YsonString -> v.data
			is YsonBool -> v.data.toString()
			is YsonNum -> v.data.toString()
			is YsonNull -> null
			is YsonObject -> v.toString()
			is YsonArray -> v.toString()
			else -> null
		}
	}

	fun int(key: String, value: Int?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, YsonNum(value))
		}
	}

	fun int(key: String): Int? {
		val v = get(key)
		return when (v) {
			is YsonNum -> v.data.toInt()
			is YsonString -> v.data.toIntOrNull()
			else -> null
		}
	}

	fun long(key: String, value: Long?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, YsonNum(value))
		}
	}

	fun long(key: String): Long? {
		val v = get(key)
		return when (v) {
			is YsonNum -> v.data.toLong()
			is YsonString -> v.data.toLongOrNull()
			else -> null
		}
	}

	fun real(key: String, value: Double?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, YsonNum(value))
		}
	}

	fun real(key: String): Double? {
		val v = get(key)
		return when (v) {
			is YsonNum -> v.data.toDouble()
			is YsonString -> v.data.toDoubleOrNull()
			else -> null
		}
	}

	fun bool(key: String, value: Boolean?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, YsonBool(value))
		}
	}

	fun bool(key: String): Boolean? {
		val v = get(key) ?: return null
		return BoolFromYson.fromYsonValue(v)
	}

	fun obj(key: String, value: YsonObject?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, value)
		}
	}

	fun obj(key: String): YsonObject? {
		return get(key) as? YsonObject
	}

	fun arr(key: String, value: YsonArray?) {
		if (value == null) {
			put(key, YsonNull.inst)
		} else {
			put(key, value)
		}
	}

	fun arr(key: String): YsonArray? {
		return get(key) as? YsonArray
	}


	fun any(key: String, value: Any?) {
		put(key, from(value))
	}

	fun any(key: String): Any? {
		return get(key)
	}

	fun putNull(key: String) {
		put(key, YsonNull.inst)
	}

	companion object {
	    init {
			TextConverts[YsonObject::class] = YsonObjectText
	    }
	}
}