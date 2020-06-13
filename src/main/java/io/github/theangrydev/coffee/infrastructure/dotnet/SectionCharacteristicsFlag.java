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

public enum SectionCharacteristicsFlag implements Flag {
    IMAGE_SCN_CNT_CODE(0x00000020),
    IMAGE_SCN_CNT_INITIALIZED_DATA(0x00000040),
    IMAGE_SCN_CNT_UNINITIALIZED_DATA(0x00000080),
    IMAGE_SCN_MEM_DISCARDABLE(0x02000000),
    IMAGE_SCN_MEM_EXECUTE(0x20000000),
    IMAGE_SCN_MEM_READ(0x40000000),
    IMAGE_SCN_MEM_WRITE(0x80000000);

    private final int value;

    SectionCharacteristicsFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
