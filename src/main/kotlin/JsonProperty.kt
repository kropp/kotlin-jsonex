package com.github.kropp.jsonex

import kotlin.reflect.KProperty

class JsonProperty<T : Any> {
  operator fun getValue(o: JsonObject<*>, property: KProperty<*>): T = o[property.name] as T
  operator fun getValue(o: MutableJsonObject<*>, property: KProperty<*>): T = o[property.name] as T
  operator fun setValue(o: MutableJsonObject<*>, property: KProperty<*>, value: T) { o[property.name] = value }
}