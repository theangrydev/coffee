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
package io.github.theangrydev.coffee.infrastructure.classfile;

import io.github.theangrydev.coffee.infrastructure.TestCase;
import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc
 */
public class ldcTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void writesOpCode0x12AndConstantIndex() {
        int constantIndex = someUnsignedByte();

        new ldc(constantIndex).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0x12);
        verify(binaryOutput).writeByte(constantIndex);
    }

    @Test
    public void isTwoBytesLong() {
        assertThat(new ldc(someUnsignedByte()).lengthInBytes()).isEqualTo(2);
    }

    @Test
    public void hasNoOperands() {
        assertThat(new ldc(someUnsignedByte()).operandSizeInBytes()).isEqualTo(0);
    }

    @Test
    public void resultIsOneByteLong() {
        assertThat(new ldc(someUnsignedByte()).resultSizeInBytes()).isEqualTo(1);
    }
}