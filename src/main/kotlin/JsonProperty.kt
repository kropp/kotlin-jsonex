package com.github.kropp.jsonex

import kotlin.reflect.KProperty

class JsonProperty<T : Any> {
  operator fun getValue(o: JsonObject<*>, property: KProperty<*>): T = o[property.name] as T
  operator fun setValue(o: JsonObject<*>, property: KProperty<*>, value: T) { o[property.name] = value }
}