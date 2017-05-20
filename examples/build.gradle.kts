dependencies {
  compile(kotlinModule("stdlib"))
  compile(rootProject)

  kapt(project(":apt"))
}