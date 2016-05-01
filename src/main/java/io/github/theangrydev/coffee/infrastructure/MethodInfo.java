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

import java.util.Set;

public class MethodInfo implements BinaryWriter {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final Code_attribute codeAttribute;

    private MethodInfo(int accessFlags, int nameIndex, int descriptorIndex, Code_attribute codeAttribute) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.codeAttribute = codeAttribute;
    }

    public static MethodInfo methodInfo(Set<MethodAccessFlag> accessFlags, int nameIndex, int descriptorIndex, Code_attribute codeAttribute) {
        return new MethodInfo(MethodAccessFlag.combine(accessFlags), nameIndex, descriptorIndex, codeAttribute);
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(accessFlags);
        binaryOutput.writeShort(nameIndex);
        binaryOutput.writeShort(descriptorIndex);
        binaryOutput.writeShort(1);
        codeAttribute.writeTo(binaryOutput);
    }
}
