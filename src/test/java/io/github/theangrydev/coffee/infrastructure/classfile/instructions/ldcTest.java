/*
 * Copyright 2016-2017 Liam Williams <liam.williams@zoho.com>.
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
package io.github.theangrydev.coffee.infrastructure.classfile.instructions;

import io.github.theangrydev.coffee.infrastructure.TestCase;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ByteConstantIndex;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.CONSTANT_String_info;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc
 */
public class ldcTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);
    private final ByteConstantIndex<CONSTANT_String_info> constantIndex = mockGeneric(ByteConstantIndex.class);

    @Test
    public void writesOpCode0x12AndConstantIndex() {
        new ldc(constantIndex).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0x12);
        verify(constantIndex).writeTo(binaryOutput);
    }

    @Test
    public void isTwoBytesLong() {
        assertThat(new ldc(constantIndex).lengthInBytes()).isEqualTo(2);
    }

    @Test
    public void hasNoOperands() {
        assertThat(new ldc(constantIndex).operandSizeInBytes()).isEqualTo(0);
    }

    @Test
    public void resultIsOneByteLong() {
        assertThat(new ldc(constantIndex).resultSizeInBytes()).isEqualTo(1);
    }
}