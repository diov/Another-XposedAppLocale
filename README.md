# Introduction

Another-XposedAppLocale is a simple Xposed module that allows you to set language for per-app.

I wrote this App because [XposedAppLocale](https://github.com/Flo354/XposedAppLocale) makes my phone stuck in a boot loop for Android Pie.

It seems like [IXposedHookZygoteInit](https://github.com/rovo89/XposedBridge/blob/art/app/src/main/java/de/robv/android/xposed/IXposedHookZygoteInit.java) can not work well on Android Pie (or later).

Inspired by [XposedAppLocale](https://github.com/Flo354/XposedAppLocale)
