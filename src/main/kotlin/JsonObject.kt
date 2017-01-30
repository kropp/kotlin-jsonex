package com.github.kropp.jsonex

import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.KProperty

@JsonDslMarker
abstract class MutableJsonObject<T : MutableJsonObject<T>>(val _map: MutableMap<String,Any> = mutableMapOf()) {
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
  operator fun set(key: String, value: Any) { _map[key] = value }

  private lateinit var _parent: MutableJsonObject<*>
  private lateinit var _name: String

  operator fun provideDelegate(o: MutableJsonObject<*>, property: KProperty<*>): MutableJsonObject<T> {
    _parent = o
    _name = property.name
    if (_name in _parent) {
      (_parent[_name] as? Map<*,*>)?.forEach { k, v -> if (k is String && v != null) this[k] = v }
    }
    _parent[_name] = this
    return this
  }

  operator fun invoke(body: T.() -> Unit) { (_parent[_name] as T).apply(body) }
  operator fun contains(key: String) = key in _map
  operator fun getValue(o: MutableJsonObject<*>, property: KProperty<*>): T = o[property.name] as T
  operator fun setValue(o: MutableJsonObject<*>, property: KProperty<*>, value: T) { o[property.name] = value }
}

abstract class JsonObject<T : JsonObject<T>>(map: Map<String,Any> = mapOf()) {
  private val _map: MutableMap<String,Any> = LinkedHashMap(map)

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

  private lateinit var _parent: JsonObject<*>
  private lateinit var _name: String

  operator fun provideDelegate(o: JsonObject<*>, property: KProperty<*>): JsonObject<T> {
    _parent = o
    _name = property.name
    if (_name in _parent) {
      (_parent[_name] as? Map<*,*>)?.forEach { k, v -> if (k is String && v != null) _map[k] = v }
    }
    _parent._map[_name] = this
    return this
  }

  operator fun contains(key: String) = key in _map
  operator fun getValue(o: JsonObject<*>, property: KProperty<*>): T = o[property.name] as T
}