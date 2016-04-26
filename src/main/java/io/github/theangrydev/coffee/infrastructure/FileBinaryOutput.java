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

import java.io.*;

import static java.lang.String.format;

public final class FileBinaryOutput implements BinaryOutput {

    private final DataOutput dataOutput;

    private FileBinaryOutput(DataOutput dataOutput) {
        this.dataOutput = dataOutput;
    }

    public static FileBinaryOutput fileBinaryOutput(String file) {
        try {
            return new FileBinaryOutput(new DataOutputStream(new FileOutputStream(file)));
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(format("Could not write to file '%s'", file), e);
        }
    }

    @Override
    public void writeByte(int byteToWrite) {
        try {
            dataOutput.writeByte(byteToWrite);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing byte", e);
        }
    }

    @Override
    public void writeShort(int shortToWrite) {
        try {
            dataOutput.writeShort(shortToWrite);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing short", e);
        }
    }

    @Override
    public void writeInt(int intToWrite) {
        try {
            dataOutput.writeInt(intToWrite);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing int", e);
        }
    }

    @Override
    public void writeUTF8(String string) {
        try {
            dataOutput.writeUTF(string);
        } catch (IOException e) {
            throw new IllegalStateException("Problem writing UTF8", e);
        }
    }
}
