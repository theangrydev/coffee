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
package io.github.theangrydev.coffee.infrastructure.classfile.constants;

import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryWriter;

/**
 * https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.1
 */
public class CONSTANT_Class_info implements BinaryWriter {
    private static final int TAG = 7;

    private final ConstantIndex<CONSTANT_Utf8_info> nameIndex;

    public CONSTANT_Class_info(ConstantIndex<CONSTANT_Utf8_info> nameIndex) {
        this.nameIndex = nameIndex;
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(TAG);
        nameIndex.writeTo(binaryOutput);
    }
}
