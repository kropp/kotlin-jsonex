package com.github.kropp.jsonex

import java.util.Date

fun string() = JsonProperty<String>()
fun int() = JsonProperty<Int>()
fun double() = JsonProperty<Double>()
fun bool() = JsonProperty<Boolean>()
fun date() = JsonProperty<Date>()
fun <T : Any> array() = JsonProperty<Array<out T>>()
