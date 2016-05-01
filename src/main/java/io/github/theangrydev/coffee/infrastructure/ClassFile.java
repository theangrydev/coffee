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

import java.util.List;
import java.util.Set;

import static io.github.theangrydev.coffee.infrastructure.Flag.combine;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1
 */
public class ClassFile implements BinaryWriter {
    private static final int MINOR_VERSION = 0;
    private static final int MAJOR_VERSION = 52;

    private static final int NUMBER_OF_CLASS_INTERFACES = 0;
    private static final int NUMBER_OF_CLASS_FIELDS = 0;
    private static final int NUMBER_OF_CLASS_ATTRIBUTES = 0;

    private final List<MethodInfo> methods;
    private final ConstantPool constantPool;
    private final int accessFlags;
    private final int thisIndex;
    private final int superIndex;

    private ClassFile(ConstantPool constantPool, int accessFlags, int thisIndex, int superIndex, List<MethodInfo> methods) {
        this.constantPool = constantPool;
        this.accessFlags = accessFlags;
        this.thisIndex = thisIndex;
        this.superIndex = superIndex;
        this.methods = methods;
    }

    public static ClassFile classFile(ConstantPool constantPool, Set<ClassAccessFlag> accessFlags, int thisIndex, int superIndex, List<MethodInfo> methods) {
        return new ClassFile(constantPool, combine(accessFlags), thisIndex, superIndex, methods);
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        writeMagic(binaryOutput);
        writeVersion(binaryOutput);
        constantPool.writeTo(binaryOutput);
        binaryOutput.writeShort(accessFlags);
        binaryOutput.writeShort(thisIndex);
        binaryOutput.writeShort(superIndex);
        binaryOutput.writeShort(NUMBER_OF_CLASS_INTERFACES);
        binaryOutput.writeShort(NUMBER_OF_CLASS_FIELDS);
        binaryOutput.writeShort(methods.size());
        for (MethodInfo method : methods) {
            method.writeTo(binaryOutput);
        }
        binaryOutput.writeShort(NUMBER_OF_CLASS_ATTRIBUTES);
    }

    private void writeVersion(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(MINOR_VERSION);
        binaryOutput.writeShort(MAJOR_VERSION);
    }

    private void writeMagic(BinaryOutput binaryOutput) {
        binaryOutput.writeByte(0xCA);
        binaryOutput.writeByte(0xFE);
        binaryOutput.writeByte(0xBA);
        binaryOutput.writeByte(0xBE);
    }
}
