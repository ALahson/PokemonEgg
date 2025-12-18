#!/usr/bin/env sh
# Minimal Gradle wrapper launcher. If this file fails, regenerate wrapper from Android Studio.
DIR="$(cd "$(dirname "$0")" && pwd)"
exec java -classpath "$DIR/gradle/wrapper/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
