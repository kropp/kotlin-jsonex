package com.github.kropp.jsonex

import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KProperty

abstract class JsonObject<T : JsonObject<T>>(map: Map<String,Any> = mapOf()) {
  protected open val _map: MutableMap<String,Any> = LinkedHashMap(map)

  private companion object {
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
  }

  override fun toString() = "{${_map.map { (k,v) -> "\"$k\":${toLiteral(v)}" }.joinToString(",")}}"
  private fun toLiteral(v: Any?): String = when(v) {
    null -> "null"
    is String -> "\"$v\""
    is Date -> "\"${dateFormat.format(v)}\""
    is Array<*> -> "[${v.map { toLiteral(it) }.joinToString(",")}]"
    is Map<*,*> -> "{${v.map { (k,v) -> "\"$k\":${toLiteral(v)}" }.joinToString(",")}}"
    else -> v.toString()
  }

  operator fun get(key: String) = _map[key]

  operator fun provideDelegate(o: JsonObject<*>, property: KProperty<*>): JsonObject<T> {
    if (property.name in o) {
      val v = o[property.name]
      when(v) {
        is JsonObject<*> -> _map.putAll(v._map)
        is Map<*, *> -> _map.putAll(v as Map<out String, Any>)
      }
    }
    o._map[property.name] = this
    return this
  }

  operator fun contains(key: String) = key in _map
  operator fun getValue(o: JsonObject<*>, property: KProperty<*>): T = o[property.name] as T
}