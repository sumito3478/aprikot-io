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
import java.nio.ByteOrder

import scala.concurrent.util.Unsafe.{ instance => _unsafe }

import org.scalatest.FunSpec

class PointerSpec extends FunSpec {
  describe("Pointer") {
    it("should be able to access to the native memory ") {
      using(Memory(4)) {
        block =>
          var p = block.pointer
          p.int(le(0xcafebabe))
          assert((p.byte & 0xff) === 0xbe)
          p += 1
          assert((p.byte & 0xff) === 0xba)
          p += 1
          assert((p.byte & 0xff) === 0xfe)
          p += 1
          assert((p.byte & 0xff) === 0xca)
      }
    }

    /*
     * TODO: This should be moved to somewhere else. sbt plugin for benchmarking
     *   should probably be introduced.
     */
    it("benchmark") {
      val s = 10240
      System.gc()
      // Use Long and Unsafe directly
      val ret = benchmark {
        val p = com.sun.jna.Native.malloc(s) //_unsafe.allocateMemory(1024)
        var cp = p
        var i = 0
        while (i < s) {
          _unsafe.putByte(cp, 1)
          cp += 1
          i += 1
        }
        var ret = 0
        i = 0
        cp = p
        while (i < s - 4) {
          ret += _unsafe.getInt(cp)
          cp += 1
          i += 1
        }
        com.sun.jna.Native.free(p) //_unsafe.freeMemory(p)
      }
      println(ret)
      System.gc()
      // use DirectBuffer.
      val ret2 = benchmark(1) {
        val block = ByteBuffer.allocateDirect(s).order(ByteOrder.LITTLE_ENDIAN)
        var i = 0
        while (i < s) {
          block.put(i, 1)
          i += 1
        }
        var ret = 0
        i = 0
        while (i < s - 4) {
          ret += block.getInt(i)
          i += 1
        }
      }
      println(ret2)
      System.gc()
      // Use Memory and Pointer.
      val ret3 = benchmark(1) {
        using(Memory(s)) {
          block =>
            val p = block.pointer
            var cp = p
            var i = 0
            while (i < s) {
              cp.byte(1)
              cp += 1
              i += 1
            }
            var ret = 0
            i = 0
            cp = p
            while (i < s - 4) {
              ret += cp.int
              cp += 1
              i += 1
            }
        }
      }
      println(ret3)
    }
  }
}