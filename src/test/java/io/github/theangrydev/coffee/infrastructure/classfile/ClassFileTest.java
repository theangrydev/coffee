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
package io.github.theangrydev.coffee.infrastructure.classfile;

import io.github.theangrydev.coffee.infrastructure.TestCase;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.CONSTANT_Class_info;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ConstantIndex;
import io.github.theangrydev.coffee.infrastructure.classfile.constants.ConstantPool;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static io.github.theangrydev.coffee.infrastructure.classfile.ClassAccessFlag.ACC_PUBLIC;
import static io.github.theangrydev.coffee.infrastructure.classfile.ClassAccessFlag.ACC_SUPER;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1
 */
public class ClassFileTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);
    private final ConstantPool constantPool = mock(ConstantPool.class);
    private final MethodInfo firstMethod = mock(MethodInfo.class);
    private final MethodInfo secondMethod = mock(MethodInfo.class);
    private final ConstantIndex<CONSTANT_Class_info> thisIndex = mockGeneric(ConstantIndex.class);
    private final ConstantIndex<CONSTANT_Class_info> superIndex = mockGeneric(ConstantIndex.class);

    @Test
    public void writesClassFile() {
        List<MethodInfo> methods = newArrayList(firstMethod, secondMethod);
        ClassFile classFile = ClassFile.classFile(constantPool, newHashSet(ACC_PUBLIC, ACC_SUPER), thisIndex, superIndex, methods);

        classFile.writeTo(binaryOutput);

        verifyMagic();
        verifyVersion();
        verify(constantPool).writeTo(binaryOutput);
        verify(binaryOutput).writeShort(ACC_PUBLIC.value() | ACC_SUPER.value());
        verify(thisIndex).writeTo(binaryOutput);
        verify(superIndex).writeTo(binaryOutput);
        verify(binaryOutput, times(2)).writeShort(0); // no interfaces or fields
        verify(binaryOutput).writeShort(methods.size());
        verify(firstMethod).writeTo(binaryOutput);
        verify(secondMethod).writeTo(binaryOutput);
        verify(binaryOutput).writeShort(0); // no attributes
    }

    public void verifyMagic() {
        verify(binaryOutput).writeByte(0xCA);
        verify(binaryOutput).writeByte(0xFE);
        verify(binaryOutput).writeByte(0xBA);
        verify(binaryOutput).writeByte(0xBE);
    }

    public void verifyVersion() {
        verify(binaryOutput).writeShort(0);
        verify(binaryOutput).writeShort(52);
    }
}