/*
 * Copyright 2016-2017 Liam Williams <liam.williams@zoho.com>.
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

public class ConstantIndexTest extends TestCase {

    private final int index = someUnsignedByte();
    private final CONSTANT_Utf8_info constant = new CONSTANT_Utf8_info("something");
    private final ConstantIndex<CONSTANT_Utf8_info> constantIndex = new ConstantIndex<>(constant, index);
    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void toStringMentionsIndexAndConstant() {
        assertThat(constantIndex.toString()).containsSequence(String.valueOf(index), constant.toString());
    }

    @Test
    public void writesIndexAsAByte() {
        verifyThat(() -> constantIndex.writeTo(binaryOutput)).willMake(binaryOutput).writeShort(index);
    }
}