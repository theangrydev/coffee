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

import io.github.theangrydev.coffee.infrastructure.TestCase;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se11/html/jvms-4.html#jvms-4.4.6
 */
public class CONSTANT_NameAndType_infoTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);
    private final ConstantIndex<CONSTANT_Utf8_info> nameIndex = mockGeneric(ConstantIndex.class);
    private final ConstantIndex<CONSTANT_Utf8_info> descriptorIndex = mockGeneric(ConstantIndex.class);

    @Test
    public void writesTagThenNameIndexThenDescriptorIndex() {
        int tag = 12;

        new CONSTANT_NameAndType_info(nameIndex, descriptorIndex).writeTo(binaryOutput);

        verify(binaryOutput).writeByte(tag);
        verify(nameIndex).writeTo(binaryOutput);
        verify(descriptorIndex).writeTo(binaryOutput);
    }
}