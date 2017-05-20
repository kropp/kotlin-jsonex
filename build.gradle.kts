buildscript {
    repositories {
        gradleScriptKotlin()
    }
    dependencies {
        classpath(kotlinModule("gradle-plugin"))
    }
}

allprojects {
    group = "com.github.kropp"
    version = "0.1"

    apply {
        plugin("kotlin")
    }

    repositories {
        mavenCentral()
    }
}

dependencies {
    compile(kotlinModule("stdlib"))
}