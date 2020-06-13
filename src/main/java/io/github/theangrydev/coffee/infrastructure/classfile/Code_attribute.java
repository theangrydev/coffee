/*
 * Copyright 2016-2020 Liam Williams <liam.williams@zoho.com>.
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

import io.github.theangrydev.coffee.infrastructure.classfile.constants.CONSTANT_Utf8_info;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ConstantIndex;
import io.github.theangrydev.coffee.infrastructure.classfile.instructions.Instruction;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryWriter;

import java.util.List;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3
 */
public class Code_attribute implements BinaryWriter {
    private static final int MAX_OPERAND_STACK_SIZE_LENGTH = 2;
    private static final int MAX_LOCAL_STACK_SIZE_LENGTH = 2;
    private static final int CODE_LENGTH_LENGTH = 4;
    private static final int EXCEPTION_TABLE_LENGTH_LENGTH = 2;
    private static final int ATTRIBUTES_COUNT_LENGTH = 2;
    private static final int BASE_ATTRIBUTE_LENGTH = MAX_OPERAND_STACK_SIZE_LENGTH + MAX_LOCAL_STACK_SIZE_LENGTH + CODE_LENGTH_LENGTH + EXCEPTION_TABLE_LENGTH_LENGTH + ATTRIBUTES_COUNT_LENGTH;

    private static final int ATTRIBUTE_COUNT = 0;
    private static final int EXCEPTION_TABLE_LENGTH = 0;

    private final ConstantIndex<CONSTANT_Utf8_info> attributeNameIndex;

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.9
     */
    private final int codeLength;

    private final List<Instruction> instructions;

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6.2
     */
    private final int maxOperandStackSize;

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6.1
     */
    private final int maxLocalStackSize;

    private Code_attribute(ConstantIndex<CONSTANT_Utf8_info> attributeNameIndex, int codeLength, List<Instruction> instructions, int maxOperandStackSize, int maxLocalStackSize) {
        this.attributeNameIndex = attributeNameIndex;
        this.codeLength = codeLength;
        this.instructions = instructions;
        this.maxOperandStackSize = maxOperandStackSize;
        this.maxLocalStackSize = maxLocalStackSize;
    }

    public static Code_attribute code(ConstantIndex<CONSTANT_Utf8_info> attributeNameIndex, List<Instruction> instructions) {
        int codeLength = instructions.stream().mapToInt(Instruction::lengthInBytes).sum();
        int maxOperandStackSize = maxOperandStackSize(instructions);
        int maxLocalStackSize = maxLocalStackSize();
        return new Code_attribute(attributeNameIndex, codeLength, instructions, maxOperandStackSize, maxLocalStackSize);
    }

    private static int maxLocalStackSize() {
        StackSize localStackSize = new StackSize(1);
        return localStackSize.max();
    }

    private static int maxOperandStackSize(List<Instruction> instructions) {
        StackSize operandStackSize = new StackSize(0);
        for (Instruction instruction : instructions) {
            operandStackSize.pop(instruction.operandSizeInBytes());
            operandStackSize.push(instruction.resultSizeInBytes());
        }
        return operandStackSize.max();
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        attributeNameIndex.writeTo(binaryOutput);
        binaryOutput.writeInt(BASE_ATTRIBUTE_LENGTH + codeLength);
        binaryOutput.writeShort(maxOperandStackSize);
        binaryOutput.writeShort(maxLocalStackSize);
        binaryOutput.writeInt(codeLength);
        for (BinaryWriter instruction : instructions) {
            instruction.writeTo(binaryOutput);
        }
        binaryOutput.writeShort(EXCEPTION_TABLE_LENGTH);
        binaryOutput.writeShort(ATTRIBUTE_COUNT);
    }
}
