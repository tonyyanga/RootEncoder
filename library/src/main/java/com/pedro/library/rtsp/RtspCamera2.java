/*
 * Copyright (C) 2021 pedroSG94.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pedro.library.rtsp;

import android.content.Context;
import android.media.MediaCodec;
import android.os.Build;
import android.view.SurfaceView;
import android.view.TextureView;

import androidx.annotation.RequiresApi;

import com.pedro.library.base.Camera2Base;
import com.pedro.library.util.VideoCodec;
import com.pedro.library.util.streamclient.RtspStreamClient;
import com.pedro.library.util.streamclient.StreamClientListener;
import com.pedro.library.view.LightOpenGlView;
import com.pedro.library.view.OpenGlView;
import com.pedro.rtsp.rtsp.RtspClient;
import com.pedro.rtsp.utils.ConnectCheckerRtsp;

import java.nio.ByteBuffer;

/**
 * More documentation see:
 * {@link com.pedro.library.base.Camera2Base}
 *
 * Created by pedro on 4/06/17.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RtspCamera2 extends Camera2Base {

  private final RtspClient rtspClient;
  private final RtspStreamClient streamClient;
  private final StreamClientListener streamClientListener = this::requestKeyFrame;

  /**
   * @deprecated This view produce rotations problems and could be unsupported in future versions.
   * Use {@link Camera2Base#Camera2Base(OpenGlView)} or {@link Camera2Base#Camera2Base(LightOpenGlView)}
   * instead.
   */
  @Deprecated
  public RtspCamera2(SurfaceView surfaceView, ConnectCheckerRtsp connectCheckerRtsp) {
    super(surfaceView);
    rtspClient = new RtspClient(connectCheckerRtsp);
    streamClient = new RtspStreamClient(rtspClient, streamClientListener);
  }

  /**
   * @deprecated This view produce rotations problems and could be unsupported in future versions.
   * Use {@link Camera2Base#Camera2Base(OpenGlView)} or {@link Camera2Base#Camera2Base(LightOpenGlView)}
   * instead.
   */
  @Deprecated
  public RtspCamera2(TextureView textureView, ConnectCheckerRtsp connectCheckerRtsp) {
    super(textureView);
    rtspClient = new RtspClient(connectCheckerRtsp);
    streamClient = new RtspStreamClient(rtspClient, streamClientListener);
  }

  public RtspCamera2(OpenGlView openGlView, ConnectCheckerRtsp connectCheckerRtsp) {
    super(openGlView);
    rtspClient = new RtspClient(connectCheckerRtsp);
    streamClient = new RtspStreamClient(rtspClient, streamClientListener);
  }

  public RtspCamera2(LightOpenGlView lightOpenGlView, ConnectCheckerRtsp connectCheckerRtsp) {
    super(lightOpenGlView);
    rtspClient = new RtspClient(connectCheckerRtsp);
    streamClient = new RtspStreamClient(rtspClient, streamClientListener);
  }

  public RtspCamera2(Context context, boolean useOpengl, ConnectCheckerRtsp connectCheckerRtsp) {
    super(context, useOpengl);
    rtspClient = new RtspClient(connectCheckerRtsp);
    streamClient = new RtspStreamClient(rtspClient, streamClientListener);
  }

  @Override
  public RtspStreamClient getStreamClient() {
    return streamClient;
  }

  @Override
  protected void setVideoCodecImp(VideoCodec codec) {
    rtspClient.setVideoCodec(codec == VideoCodec.H264 ? com.pedro.rtsp.rtsp.VideoCodec.H264 : com.pedro.rtsp.rtsp.VideoCodec.H265);
  }

  @Override
  protected void prepareAudioRtp(boolean isStereo, int sampleRate) {
    rtspClient.setAudioInfo(sampleRate, isStereo);
  }

  @Override
  protected void startStreamRtp(String url) {
    rtspClient.connect(url);
  }

  @Override
  protected void stopStreamRtp() {
    rtspClient.disconnect();
  }

  @Override
  protected void getAacDataRtp(ByteBuffer aacBuffer, MediaCodec.BufferInfo info) {
    rtspClient.sendAudio(aacBuffer, info);
  }

  @Override
  protected void onSpsPpsVpsRtp(ByteBuffer sps, ByteBuffer pps, ByteBuffer vps) {
    rtspClient.setVideoInfo(sps, pps, vps);
  }

  @Override
  protected void getH264DataRtp(ByteBuffer h264Buffer, MediaCodec.BufferInfo info) {
    rtspClient.sendVideo(h264Buffer, info);
  }
}

