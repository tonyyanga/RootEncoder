package com.pedro.srt.srt.packets.control.handshake.extension

import com.pedro.srt.srt.packets.data.KeyBasedEncryption
import com.pedro.srt.utils.EncryptInfo
import com.pedro.srt.utils.writeUInt16
import com.pedro.srt.utils.writeUInt32
import java.io.ByteArrayOutputStream

/**
 * Created by pedro on 13/11/23.
 *
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |S|  V  |   PT  |              Sign             |   Resv1   | KK|
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                              KEKI                             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |     Cipher    |      Auth     |       SE      |     Resv2     |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |             Resv3             |     SLen/4    |     KLen/4    |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                              Salt                             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                                                               |
 * +                          Wrapped Key                          +
 * |                                                               |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 *
 */
class KeyMaterialMessage(
  private val encryptInfo: EncryptInfo,
  private val streamEncapsulation: StreamEncapsulationType = StreamEncapsulationType.MPEG_TS_SRT
) {

  fun getData(): ByteArray {
    val buffer = ByteArrayOutputStream()
    val sVersionPacketType = (0 shl 7) or (1 shl 4) or 2
    buffer.write(sVersionPacketType)
    //Sign
    buffer.write(0x20)
    buffer.write(0x29)
    val resv1KeyBasedEncryption = 0 shl 2 or encryptInfo.keyBasedEncryption.value
    buffer.write(resv1KeyBasedEncryption)
    buffer.writeUInt32(0) //keki
    buffer.write(encryptInfo.cipher.value)
    buffer.write(if (encryptInfo.cipher == CipherType.GCM) 1 else 0) //auth
    buffer.write(streamEncapsulation.value) //SE
    buffer.write(0) // resv2
    buffer.writeUInt16(0) // resv3
    buffer.write(encryptInfo.salt.size / 4)
    buffer.write(encryptInfo.keyLength / 4)
    buffer.write(encryptInfo.salt)
    buffer.write(encryptInfo.key)
    return buffer.toByteArray()
  }
}