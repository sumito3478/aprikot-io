/* Copyright (C) 2013 sumito3478 <sumito3478@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package info.sumito3478.aprikot.io

import java.nio.ByteBuffer

import scala.concurrent.util.Unsafe.{instance => _unsafe}

import com.sun.jna.{Pointer => JPointer}

/**
 * An implementation of [[Pointer]] underlined with [[com.sun.jna.Pointer]].
 */
class Pointer(val p: Long) extends AnyVal {
  def +(n: Long): Pointer = {
    new Pointer(p + n)
  }

  def -(n: Long): Pointer = {
    new Pointer(p - n)
  }

  def byte: Byte = {
    _unsafe.getByte(p)
  }

  def byte(x: Byte): Unit = {
    _unsafe.putByte(p, x)
  }

  def shoft: Short = {
    _unsafe.getShort(p)
  }

  def short(x: Short): Unit = {
    _unsafe.putShort(p, x)
  }

  def int: Int = {
    _unsafe.getInt(p)
  }

  def int(x: Int): Unit = {
    _unsafe.putInt(p, x)
  }

  def long: Long = {
    _unsafe.getLong(p)
  }

  def long(x: Long): Unit = {
    _unsafe.putLong(p, x)
  }

  def float: Float = {
    _unsafe.getFloat(p)
  }

  def float(x: Float): Unit = {
    _unsafe.putFloat(p, x)
  }

  def double: Double = {
    _unsafe.getDouble(p)
  }

  def double(x: Double): Unit = {
    _unsafe.putDouble(p, x)
  }

  def pointer: Pointer = {
    new Pointer(_unsafe.getAddress(p))
  }

  def pointer(x: Long): Unit = {
    _unsafe.putAddress(p, x)
  }

  def byteBuffer(l: Long): ByteBuffer = {
    new JPointer(p).getByteBuffer(0, l)
  }

  def jna: JPointer = {
    new JPointer(p)
  }
}