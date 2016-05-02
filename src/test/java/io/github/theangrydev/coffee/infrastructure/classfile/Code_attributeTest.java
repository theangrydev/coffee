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
import io.github.theangrydev.coffee.infrastructure.classfile.instructions.Instruction;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import org.junit.Test;

import static java.util.Arrays.asList;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3
 */
public class Code_attributeTest extends TestCase {
    private static final int MAX_OPERAND_STACK_SIZE_LENGTH = 2;
    private static final int MAX_LOCAL_STACK_SIZE_LENGTH = 2;
    private static final int CODE_LENGTH_LENGTH = 4;
    private static final int EXCEPTION_TABLE_LENGTH_LENGTH = 2;
    private static final int ATTRIBUTES_COUNT_LENGTH = 2;
    private static final int BASE_ATTRIBUTE_LENGTH = MAX_OPERAND_STACK_SIZE_LENGTH + MAX_LOCAL_STACK_SIZE_LENGTH + CODE_LENGTH_LENGTH + EXCEPTION_TABLE_LENGTH_LENGTH + ATTRIBUTES_COUNT_LENGTH;

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);
    private final Instruction firstInstruction = mock(Instruction.class);
    private final Instruction secondInstruction = mock(Instruction.class);

    @Test
    public void writesCodeAttribute() {
        given(firstInstruction.lengthInBytes()).willReturn(1);
        given(firstInstruction.operandSizeInBytes()).willReturn(1);
        given(firstInstruction.resultSizeInBytes()).willReturn(1);

        given(secondInstruction.lengthInBytes()).willReturn(2);
        given(secondInstruction.operandSizeInBytes()).willReturn(2);
        given(secondInstruction.resultSizeInBytes()).willReturn(2);

        int attributeNameIndex = someUnsignedShort();
        int codeLength = firstInstruction.lengthInBytes() + secondInstruction.lengthInBytes();
        int attributeLength =  BASE_ATTRIBUTE_LENGTH + codeLength;
        int maxOperandStackSize = (firstInstruction.resultSizeInBytes() - firstInstruction.operandSizeInBytes()) + (secondInstruction.resultSizeInBytes() - secondInstruction.operandSizeInBytes());
        int maxLocalStackSize = 1;

        Code_attribute code = Code_attribute.code(attributeNameIndex, asList(firstInstruction, secondInstruction));

        code.writeTo(binaryOutput);

        verify(binaryOutput).writeShort(attributeNameIndex);
        verify(binaryOutput).writeInt(attributeLength);
        verify(binaryOutput).writeShort(maxOperandStackSize);
        verify(binaryOutput).writeShort(maxLocalStackSize);
        verify(binaryOutput).writeInt(codeLength);
        verify(firstInstruction).writeTo(binaryOutput);
        verify(secondInstruction).writeTo(binaryOutput);
        verify(binaryOutput, times(2)).writeShort(0); // no exception table or attributes
    }
}