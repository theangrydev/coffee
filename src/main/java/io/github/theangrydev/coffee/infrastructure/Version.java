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

public class Version implements BinaryWriter {

    private static final int MINOR_VERSION = 0;
    private static final int MAJOR_VERSION = 52;

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(MINOR_VERSION);
        binaryOutput.writeShort(MAJOR_VERSION);
    }
}
