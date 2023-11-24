// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}

// Add the repositories and dependencies for the buildscript if required.
// For example:
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Include any classpath for the buildscript dependencies if needed.
        // Example: classpath "com.android.tools.build:gradle:7.2.0"
    }
}



