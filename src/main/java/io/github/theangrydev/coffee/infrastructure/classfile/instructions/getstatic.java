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

import io.github.theangrydev.coffee.infrastructure.classfile.constants.CONSTANT_Fieldref_info;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ConstantIndex;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.getstatic
 */
public class getstatic implements Instruction {
    private final ConstantIndex<CONSTANT_Fieldref_info> fieldIndex;

    public getstatic(ConstantIndex<CONSTANT_Fieldref_info> fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    @Override
    public int lengthInBytes() {
        return 3;
    }

    @Override
    public int operandSizeInBytes() {
        return 0;
    }

    @Override
    public int resultSizeInBytes() {
        return 1;
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(0xb2);
        fieldIndex.writeTo(binaryOutput);
    }
}
