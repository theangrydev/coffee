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
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3
 */
public class Code_attributeTest extends TestCase {
    private static final int MAX_OPERAND_STACK_SIZE_LENGTH = 2;
    private static final int MAX_LOCAL_STACK_SIZE_LENGTH = 2;
    private static final int CODE_LENGTH_LENGTH = 4;
    private static final int EXCEPTION_TABLE_LENGTH_LENGTH = 2;
    private static final int ATTRIBUTES_COUNT_LENGTH = 2;

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void emptyCode() {
        int attributeNameIndex = someShort();
        int attributeLength = MAX_OPERAND_STACK_SIZE_LENGTH + MAX_LOCAL_STACK_SIZE_LENGTH + CODE_LENGTH_LENGTH + EXCEPTION_TABLE_LENGTH_LENGTH + ATTRIBUTES_COUNT_LENGTH;
        int maxOperandStackSize = 0;
        int maxLocalStackSize = 1;
        int codeLength = 0;

        Code_attribute code = new Code_attribute(attributeNameIndex);

        code.writeTo(binaryOutput);

        verify(binaryOutput).writeShort(attributeNameIndex);
        verify(binaryOutput).writeInt(attributeLength);
        verify(binaryOutput).writeShort(maxOperandStackSize);
        verify(binaryOutput).writeShort(maxLocalStackSize);
        verify(binaryOutput).writeInt(codeLength);
        verify(binaryOutput, times(2)).writeShort(0); // no exception table or attributes
    }
}