import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:android_system_properties/android_system_properties_method_channel.dart';

void main() {
  MethodChannelAndroidSystemProperties platform = MethodChannelAndroidSystemProperties();
  const MethodChannel channel = MethodChannel('android_system_properties');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
