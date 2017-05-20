package com.github.kropp.jsonex

import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement


@SupportedAnnotationTypes("com.github.kropp.jsonex.Json")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class JsonAnnotationProcessor : AbstractProcessor() {
  override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
    for (annotation in annotations.filter { it.qualifiedName.contentEquals("com.github.kropp.jsonex.Json") }) {
      val annotatedElements = roundEnv.getElementsAnnotatedWith(annotation)
      for (element in annotatedElements) {
        processJsonAnnotation(element)
      }
    }

    return true
  }

  private fun processJsonAnnotation(element: Element) {
    println(element.enclosedElements)
  }
}