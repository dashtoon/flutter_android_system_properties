package com.example.android_system_properties

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.io.BufferedReader
import java.io.IOException

/** AndroidSystemPropertiesPlugin */
class AndroidSystemPropertiesPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "android_system_properties")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "getSystemProperties") {
      var key = call.argument<String>("key")
      result.success(getSystemProperties(key?: ""))
    } else {
      result.notImplemented()
    }
  }

  private fun getSystemProperties(propName: String): String {
    return try {
      print("getprop $propName")
      val p = Runtime.getRuntime().exec("getprop $propName")
      val res = p.inputStream.bufferedReader().use { it.readText() }
      return res
    } catch (ex: IOException) {
      ""
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}