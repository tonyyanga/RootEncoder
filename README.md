# RootEncoder for Android

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-rtmp%20rtsp%20stream%20client%20java-green.svg?style=true)](https://android-arsenal.com/details/1/5333)
[![Release](https://jitpack.io/v/pedroSG94/RootEncoder.svg)](https://jitpack.io/#pedroSG94/RootEncoder)
[![Documentation](https://img.shields.io/badge/library-documentation-orange)](https://pedroSG94.github.io/RootEncoder)

RootEncoder (rtmp-rtsp-stream-client-java) is a stream encoder to push video/audio to media servers using protocols RTMP, RTSP and SRT with all code written in Java/Kotlin

Note: The library was renamed from rtmp-rtsp-stream-client-java to RootEncoder after add SRT protocol because the name has no sense anymore

If you need enterprise Grade APIs for Feeds & Chat, Stream is a great tool. <a href="https://getstream.io/tutorials/android-chat/?utm_source=https://github.com/pedroSG94/rtmp-rtsp-stream-client-java&utm_medium=github&utm_content=developer&utm_term=java" target="_blank">Try the Android Chat tutorial</a> 💬

If you need a player see this project:

https://github.com/pedroSG94/vlc-example-streamplayer

## iOS version (under develop):

https://github.com/pedroSG94/rtmp-rtsp-stream-client-swift

## Wiki

https://github.com/pedroSG94/RootEncoder/wiki

## Permissions:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.CAMERA" />
<!--Only for record video/audio-->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<!--Optional for play store-->
<uses-feature android:name="android.hardware.camera" android:required="false" />
<uses-feature android:name="android.hardware.camera.autofocus" android:required="false" />
```

## Compile

To use this library in your project with gradle add this to your build.gradle:

### Version 2.2.6 or less

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
dependencies {
  implementation 'com.github.pedroSG94.RootEncoder:rtplibrary:2.2.6'
}
```

### Version 2.2.7 or more

```gradle
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
dependencies {
  implementation 'com.github.pedroSG94.RootEncoder:library:2.3.1'
}

```

## Features:

- [x] Android min API 16.

### Encoder:

- [x] Support [camera1](https://developer.android.com/reference/android/hardware/Camera.html) and [camera2](https://developer.android.com/reference/android/hardware/camera2/package-summary.html) API
- [x] Encoder type buffer to buffer.
- [x] Encoder type surface to buffer.
- [x] Audio noise suppressor.
- [x] Audio echo cancellation.
- [x] Disable/Enable video and audio while streaming.
- [x] Switch camera while streaming.
- [x] Change video bitrate while streaming (API 19+).
- [x] H264, H265 and AAC hardware/software encoding.
- [x] Force video and audio Codec to use hardware/software encoding (Not recommended).
- [X] Record MP4 file while streaming (API 18+).
- [X] Set Image, Gif or Text to stream on real time.
- [X] OpenGL real time filters. [More info](https://github.com/pedroSG94/RootEncoder/wiki/Real-time-filters)
- [x] Stream from video and audio files like mp4, webm, mp3, etc (Limited by device decoders). [More info](https://github.com/pedroSG94/RootEncoder/wiki/Stream-from-file)
- [x] Stream device screen (API 21+).

### RTMP:

- [X] Get upload bandwidth used.
- [x] RTSP auth (adobe and llnw).
- [x] H264, H265 ([Using RTMP enhanced](https://github.com/veovera/enhanced-rtmp/tree/main)) and AAC support.
- [x] RTMPS (under TLS)
- [x] RTMPT and RTMPTS (tunneled and tunneled under TLS)
- [x] AMF0
- [ ] AMF3

### RTSP:

- [X] Get upload bandwidth used.
- [x] RTMP auth (basic and digest).
- [x] H264, H265 and AAC support.
- [x] TCP/UDP.
- [x] RTSPS.

### SRT (beta):

- [X] Get upload bandwidth used.
- [X] H264, H265 and AAC support.
- [X] Resend lost packets
- [X] Encrypt (AES128, AES192 and AES256)
- [ ] SRT auth.

https://haivision.github.io/srt-rfc/draft-sharabayko-srt.html


## Other related projects:

https://github.com/pedroSG94/RTSP-Server

https://github.com/pedroSG94/AndroidReStreamer

https://github.com/pedroSG94/Stream-USB-test

### 3rd party projects:

Projects related with the library developed by other users.
Contact with user owner if you have any problem or question.

https://github.com/FunnyDevs/rtmp-rtsp-stream-client-java-recordcontrollers

## Real time filters:

### NOTE:
In library version 2.0.9, the filters was refactored. Check the wiki link to migrate your implementation.

https://github.com/pedroSG94/RootEncoder/wiki/Real-time-filters

## Looking for sponsors

This library need sponsors to get new devices or pay platforms to test and debug errors. Any donation or sponsor is welcome!
If you are interested. You can contact me by email or donate directly on [Github](https://github.com/sponsors/pedroSG94) or [Paypal](https://www.paypal.com/paypalme/pedroSG94)
Thank you!

## Use example:

This code is a basic example.
I recommend you go to Activities in app module and see all examples.

### RTMP:

```java

//default

//create builder
RtmpCamera1 rtmpCamera1 = new RtmpCamera1(openGlView, connectCheckerRtmp);
//start stream
if (rtmpCamera1.prepareAudio() && rtmpCamera1.prepareVideo()) {
  rtmpCamera1.startStream("rtmp://yourEndPoint");
} else {
 /**This device cant init encoders, this could be for 2 reasons: The encoder selected doesnt support any configuration setted or your device hasnt a H264 or AAC encoder (in this case you can see log error valid encoder not found)*/
}
//stop stream
rtmpCamera1.stopStream();

//with params

//create builder
RtmpCamera1 rtmpCamera1 = new RtmpCamera1(openGlView, connectCheckerRtmp);
//start stream
if (rtmpCamera1.prepareAudio(int bitrate, int sampleRate, boolean isStereo, boolean echoCanceler,
      boolean noiseSuppressor) && rtmpCamera1.prepareVideo(int width, int height, int fps, int bitrate, int rotation)) {
  rtmpCamera1.startStream("rtmp://yourEndPoint");
} else {
 /**This device cant init encoders, this could be for 2 reasons: The encoder selected doesnt support any configuration setted or your device hasnt a H264 or AAC encoder (in this case you can see log error valid encoder not found)*/
}
//stop stream
rtmpCamera1.stopStream();

```

### RTSP:

```java

//default

//create builder
//by default TCP protocol.
RtspCamera1 rtspCamera1 = new RtspCamera1(openGlView, connectCheckerRtsp);
//start stream
if (rtspCamera1.prepareAudio() && rtspCamera1.prepareVideo()) {
  rtspCamera1.startStream("rtsp://yourEndPoint");
} else {
 /**This device cant init encoders, this could be for 2 reasons: The encoder selected doesnt support any configuration setted or your device hasnt a H264 or AAC encoder (in this case you can see log error valid encoder not found)*/
}
//stop stream
rtspCamera1.stopStream();

//with params

//create builder
RtspCamera1 rtspCamera1 = new RtspCamera1(openGlView, connectCheckerRtsp);
rtspCamera1.setProtocol(protocol);
//start stream
if (rtspCamera1.prepareAudio(int bitrate, int sampleRate, boolean isStereo, boolean echoCanceler,
      boolean noiseSuppressor) && rtspCamera1.prepareVideo(int width, int height, int fps, int bitrate, int rotation)) {
  rtspCamera1.startStream("rtsp://yourEndPoint");
} else {
 /**This device cant init encoders, this could be for 2 reasons: The encoder selected doesnt support any configuration setted or your device hasnt a H264 or AAC encoder (in this case you can see log error valid encoder not found)*/
}
//stop stream
rtspCamera1.stopStream();

```
