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

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3
 */
@SuppressWarnings("PMD") // TODO: refactor
public class Code_attribute implements BinaryWriter {
    private static final int MAX_OPERAND_STACK_SIZE_LENGTH = 2;
    private static final int MAX_LOCAL_STACK_SIZE_LENGTH = 2;
    private static final int CODE_LENGTH_LENGTH = 4;
    private static final int EXCEPTION_TABLE_LENGTH_LENGTH = 2;
    private static final int ATTRIBUTES_COUNT_LENGTH = 2;
    private static final int BASE_ATTRIBUTE_LENGTH = MAX_OPERAND_STACK_SIZE_LENGTH + MAX_LOCAL_STACK_SIZE_LENGTH + CODE_LENGTH_LENGTH + EXCEPTION_TABLE_LENGTH_LENGTH + ATTRIBUTES_COUNT_LENGTH;

    private static final int ATTRIBUTE_COUNT = 0;
    private static final int EXCEPTION_TABLE_LENGTH = 0;

    private final int attributeNameIndex;

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6.2
     */
    private final StackSize operandStackSize = new StackSize(0);

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6.1
     */
    private final StackSize localStackSize = new StackSize(1);

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.9
     */
    private int codeLength;

    private List<BinaryWriter> instructions = new ArrayList<>();

    public Code_attribute(int attributeNameIndex) {
        this.attributeNameIndex = attributeNameIndex;
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.aload_n
     */
    public void aload0() {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0x2a));
        codeLength++;
        operandStackSize.push(1);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.aaload
     */
    public void aaload() {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0x32));
        codeLength++;
        operandStackSize.pop(2);
        operandStackSize.push(1);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iconst_i
     */
    public void iconst0() {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0x3));
        codeLength++;
        operandStackSize.push(1);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iconst_i
     */
    public void iconst1() {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0x4));
        codeLength++;
        operandStackSize.push(1);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokespecial
     */
    public void invokespecial(int methodIndex, int numberOfArguments) {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0xb7));
        instructions.add(binaryOutput -> binaryOutput.writeShort(methodIndex));
        codeLength+= 3;
        operandStackSize.pop(numberOfArguments);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokestatic
     */
    public void invokestatic(int methodIndex, int numberOfArguments, boolean hasResult) {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0xb8));
        instructions.add(binaryOutput -> binaryOutput.writeShort(methodIndex));
        codeLength+= 3;
        operandStackSize.pop(numberOfArguments);
        if (hasResult) {
            operandStackSize.push(1);
        }
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.invokevirtual
     */
    public void invokevirtual(int methodIndex, int numberOfArguments, boolean hasResult) {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0xb6));
        instructions.add(binaryOutput -> binaryOutput.writeShort(methodIndex));
        codeLength+= 3;
        operandStackSize.pop(numberOfArguments);
        if (hasResult) {
            operandStackSize.push(1);
        }
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.getstatic
     */
    public void getstatic(int fieldIndex) {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0xb2));
        instructions.add(binaryOutput -> binaryOutput.writeShort(fieldIndex));
        codeLength+= 3;
        operandStackSize.push(1);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.return
     */
    public void returnvoid() {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0xb1));
        codeLength++;
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.iadd
     */
    public void iadd() {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0x60));
        codeLength++;
        operandStackSize.pop(2);
        operandStackSize.push(1);
    }

    /**
     * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.ldc
     */
    public void ldc(int constantIndex) {
        instructions.add(binaryOutput -> binaryOutput.writeByte(0x12));
        instructions.add(binaryOutput -> binaryOutput.writeByte(constantIndex));
        codeLength+=2;
        operandStackSize.push(1);
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(attributeNameIndex);
        binaryOutput.writeInt(BASE_ATTRIBUTE_LENGTH + codeLength);
        binaryOutput.writeShort(operandStackSize.max());
        binaryOutput.writeShort(localStackSize.max());
        binaryOutput.writeInt(codeLength);
        for (BinaryWriter instruction : instructions) {
            instruction.writeTo(binaryOutput);
        }
        binaryOutput.writeShort(EXCEPTION_TABLE_LENGTH);
        binaryOutput.writeShort(ATTRIBUTE_COUNT);
    }
}
