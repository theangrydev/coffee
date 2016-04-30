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

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.2
 */
public class CONSTANT_Methodref_info implements BinaryWriter {

    private static final int TAG = 10;

    private final int classIndex;
    private final int nameAndTypeIndex;

    public CONSTANT_Methodref_info(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(TAG);
        binaryOutput.writeShort(classIndex);
        binaryOutput.writeShort(nameAndTypeIndex);
    }
}
