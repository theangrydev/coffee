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
import io.github.theangrydev.coffee.infrastructure.classfile.constants.CONSTANT_Fieldref_info;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ConstantIndex;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.getstatic
 */
public class getstaticTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);
    private final ConstantIndex<CONSTANT_Fieldref_info> fieldIndex = mockGeneric(ConstantIndex.class);

    @Test
    public void writesOpCode0xb2AndFieldIndex() {
        new getstatic(fieldIndex).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0xb2);
        verify(fieldIndex).writeTo(binaryOutput);
    }

    @Test
    public void isThreeBytesLong() {
        assertThat(new getstatic(fieldIndex).lengthInBytes()).isEqualTo(3);
    }

    @Test
    public void hasNoOperands() {
        assertThat(new getstatic(fieldIndex).operandSizeInBytes()).isEqualTo(0);
    }

    @Test
    public void resultIsOneByteLong() {
        assertThat(new getstatic(fieldIndex).resultSizeInBytes()).isEqualTo(1);
    }
}