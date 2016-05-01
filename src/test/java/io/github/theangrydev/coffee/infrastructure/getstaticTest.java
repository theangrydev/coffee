/*
 * Copyright 2016 Liam Williams <liam.williams@zoho.com>.
 *
 * This file is part of coffee.
 *
 * coffee is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * coffee is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with coffee.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.theangrydev.coffee.infrastructure;

import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.getstatic
 */
public class getstaticTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void writesOpCode0xb2AndFieldIndex() {
        int fieldIndex = someUnsignedShort();

        new getstatic(fieldIndex).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0xb2);
        verify(binaryOutput).writeShort(fieldIndex);
    }

    @Test
    public void isThreeBytesLong() {
        assertThat(new getstatic(someUnsignedShort()).lengthInBytes()).isEqualTo(3);
    }

    @Test
    public void hasNoOperands() {
        assertThat(new getstatic(someUnsignedShort()).operandSizeInBytes()).isEqualTo(0);
    }

    @Test
    public void resultIsOneByteLong() {
        assertThat(new getstatic(someUnsignedShort()).resultSizeInBytes()).isEqualTo(1);
    }
}