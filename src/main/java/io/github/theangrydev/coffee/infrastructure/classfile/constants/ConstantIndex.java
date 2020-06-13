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

import io.github.theangrydev.coffee.domain.base.ValueType;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryWriter;

public class ConstantIndex<T extends BinaryWriter> extends ValueType implements BinaryWriter {

    private final T constant;
    private final int index;

    public ConstantIndex(T constant, int index) {
        this.constant = constant;
        this.index = index;
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(index);
    }

    @Override
    public String toString() {
        return String.format("Constant[%d]: %s", index, constant);
    }
}
