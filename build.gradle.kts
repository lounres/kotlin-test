val kotlin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.31"
    id("org.jetbrains.dokka") version "1.5.30"
}

group = "com.lounres"
version = "0.0.1"
application {
    mainClass.set("Main")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
}

tasks.dokkaHtml {
    dokkaSourceSets {
        configureEach {
            perPackageOption {
                matchingRegex.set("""(problems|theTrueHat|utils)(.\w*)?""")
                suppress.set(true)
            }
        }
    }
}