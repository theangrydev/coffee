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

import java.util.ArrayList;
import java.util.List;

/**
 * https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4
 */
public class ConstantPool implements BinaryWriter {
    private final List<BinaryWriter> constants = new ArrayList<>();

    public <T extends BinaryWriter> ConstantIndex<T> addConstant(T constant) {
        constants.add(constant);
        return new ConstantIndex<>(constant, constants.size());
    }

    public <T extends BinaryWriter> ByteConstantIndex<T> addByteConstant(T constant) {
        constants.add(constant);
        return new ByteConstantIndex<>(constant, constants.size());
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(constants.size() + 1);
        for (BinaryWriter constant : constants) {
            constant.writeTo(binaryOutput);
        }
    }
}
