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
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokestatic
 */
public class invokestaticTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void writesOpCode0xb8AndMethodIndex() {
        int methodIndex = someShort();

        new invokestatic(methodIndex, someInt(), someBoolean()).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0xb8);
        verify(binaryOutput).writeShort(methodIndex);
    }

    @Test
    public void isThreeBytesLong() {
        assertThat(new invokestatic(someShort(), someInt(), someBoolean()).lengthInBytes()).isEqualTo(3);
    }

    @Test
    public void hasNumberOfOperandsEqualToConstructorArgument() {
        int numberOfArguments = someInt();

        invokestatic invokestatic = new invokestatic(someShort(), numberOfArguments, someBoolean());

        assertThat(invokestatic.operandSizeInBytes()).isEqualTo(numberOfArguments);
    }

    @Test
    public void hasResultWhenResultFlagIsPresent() {
        assertThat(new invokestatic(someShort(), someInt(), true).resultSizeInBytes()).isEqualTo(1);
        assertThat(new invokestatic(someShort(), someInt(), false).resultSizeInBytes()).isEqualTo(0);
    }
}