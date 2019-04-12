@file:Suppress("unused")

package dev.entao.json

import kotlin.reflect.KClass
import kotlin.reflect.KType

object Yson {
	fun toYson(v: Any?): YsonValue {
		return YsonEncoder.encode(v, null)
	}

	fun toYson(v: Any?, config: ToYsonConfig?): YsonValue {
		return YsonEncoder.encode(v, config)
	}

	inline fun <reified T : Any> toModel(yson: YsonValue, config: FromYsonConfig? = null): T? {
		return YsonDecoder.decodeByClass(yson, T::class, config) as T?
	}

	fun toModelClass(yson: YsonValue, cls: KClass<*>, config: FromYsonConfig? = null): Any? {
		return YsonDecoder.decodeByClass(yson, cls, config)
	}

	inline fun <reified T : Any> toModelGeneric(yson: YsonValue, ktype: KType, config: FromYsonConfig? = null): T? {
		return YsonDecoder.decodeByType(yson, ktype, config) as T?
	}


	object Types {
		val ArrayListString: KType by lazy { object : TypeTake<ArrayList<String>>() {}.type }
		val ArrayListInt: KType by lazy { object : TypeTake<ArrayList<Int>>() {}.type }
		val ArrayListLong: KType by lazy { object : TypeTake<ArrayList<Long>>() {}.type }
		val HashMapStringString: KType by lazy { object : TypeTake<HashMap<String, String>>() {}.type }
		val HashMapStringInt: KType by lazy { object : TypeTake<HashMap<String, Int>>() {}.type }
		val HashMapStringLong: KType by lazy { object : TypeTake<HashMap<String, Long>>() {}.type }
	}
}