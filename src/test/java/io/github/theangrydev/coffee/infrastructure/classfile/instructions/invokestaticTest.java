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
package io.github.theangrydev.coffee.infrastructure.classfile.instructions;

import io.github.theangrydev.coffee.infrastructure.TestCase;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.CONSTANT_Methodref_info;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ConstantIndex;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokestatic
 */
public class invokestaticTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);
    private final ConstantIndex<CONSTANT_Methodref_info> methodIndex = mockGeneric(ConstantIndex.class);

    @Test
    public void writesOpCode0xb8AndMethodIndex() {
        new invokestatic(methodIndex, someUnsignedInt(), someBoolean()).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0xb8);
        verify(methodIndex).writeTo(binaryOutput);
    }

    @Test
    public void isThreeBytesLong() {
        assertThat(new invokestatic(methodIndex, someUnsignedInt(), someBoolean()).lengthInBytes()).isEqualTo(3);
    }

    @Test
    public void hasNumberOfOperandsEqualToConstructorArgument() {
        int numberOfArguments = someUnsignedInt();

        invokestatic invokestatic = new invokestatic(methodIndex, numberOfArguments, someBoolean());

        assertThat(invokestatic.operandSizeInBytes()).isEqualTo(numberOfArguments);
    }

    @Test
    public void hasResultWhenResultFlagIsPresent() {
        assertThat(new invokestatic(methodIndex, someUnsignedInt(), true).resultSizeInBytes()).isEqualTo(1);
        assertThat(new invokestatic(methodIndex, someUnsignedInt(), false).resultSizeInBytes()).isEqualTo(0);
    }
}