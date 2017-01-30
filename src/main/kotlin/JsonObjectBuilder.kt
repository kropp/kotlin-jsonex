package com.github.kropp.jsonex

import kotlin.reflect.KProperty

@JsonDslMarker
abstract class JsonObjectBuilder<T : JsonObjectBuilder<T, I>, I : Any>(map: MutableMap<String,Any> = mutableMapOf()) : JsonObject<T>(map) {
  public override val _map: MutableMap<String, Any> = map

  operator fun set(key: String, value: Any) { _map[key] = value }

  operator fun provideDelegate(o: JsonObjectBuilder<*, *>, property: KProperty<*>): JsonObjectBuilder<T, I> {
    if (property.name in o) {
      val v = o[property.name]
      when(v) {
        is JsonObjectBuilder<*, *> -> _map.putAll(v._map)
        is Map<*, *> -> _map.putAll(v as Map<out String, Any>)
      }
    }
    o[property.name] = this
    return this
  }

  //operator fun invoke(body: T.() -> Unit) { (this as T).apply(body) }
  operator fun setValue(o: JsonObjectBuilder<*, *>, property: KProperty<*>, value: I) { o[property.name] = value }
}