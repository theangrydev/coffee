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
package io.github.theangrydev.coffee.infrastructure.dotnet;

import io.github.theangrydev.coffee.infrastructure.Flag;

public enum FileCharacteristicsFlag implements Flag {
    IMAGE_FILE_EXECUTABLE_IMAGE(0x0002),
    IMAGE_FILE_32BIT_MACHINE(0x0100),
    IMAGE_FILE_DLL(0x2000);

    private final int value;

    FileCharacteristicsFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
