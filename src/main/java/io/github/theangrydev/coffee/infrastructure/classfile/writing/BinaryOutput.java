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
package io.github.theangrydev.coffee.infrastructure.classfile.writing;

import com.google.common.base.Preconditions;

import java.io.*;

import static java.lang.String.format;

public class BinaryOutput {

    public static final int UNSIGNED_SHORT_MAX_VALUE = 65535;
    public static final int UNSIGNED_BYTE_MAX_VALUE = 255;

    private final DataOutput dataOutput;

    public BinaryOutput(DataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    public static BinaryOutput fileBinaryOutput(String file) {
        try {
            return new BinaryOutput(new DataOutputStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(format("Could not write to file '%s'", file), e);
        }
    }

    public void writeByte(int byteToWrite) {
		Preconditions.checkArgument(byteToWrite >= 0, "byte must not be negative but was %s", byteToWrite);
		Preconditions.checkArgument(byteToWrite <= UNSIGNED_BYTE_MAX_VALUE, "byte can be at most %s but was %s", UNSIGNED_BYTE_MAX_VALUE, byteToWrite);
        try {
            dataOutput.writeByte(byteToWrite);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing byte", e);
        }
    }

    public void writeShort(int shortToWrite) {
        Preconditions.checkArgument(shortToWrite >= 0, "short must not be negative but was %s", shortToWrite);
        Preconditions.checkArgument(shortToWrite <= UNSIGNED_SHORT_MAX_VALUE, "short can be at most %s but was %s", UNSIGNED_SHORT_MAX_VALUE, shortToWrite);
        try {
            dataOutput.writeShort(shortToWrite);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing short", e);
        }
    }

    public void writeInt(int intToWrite) {
        Preconditions.checkArgument(intToWrite >= 0, "int must not be negative but was %s", intToWrite);
        try {
            dataOutput.writeInt(intToWrite);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing int", e);
        }
    }

    public void writeUTF8(String string) {
        try {
            dataOutput.writeUTF(string);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing UTF8", e);
        }
    }
}
