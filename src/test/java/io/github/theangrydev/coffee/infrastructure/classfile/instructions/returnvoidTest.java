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
package io.github.theangrydev.coffee.infrastructure.classfile.instructions;

import io.github.theangrydev.coffee.infrastructure.TestCase;
import io.github.theangrydev.coffee.infrastructure.classfile.BinaryOutput;
import org.junit.Test;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-6.html#jvms-6.5.return
 */
public class returnvoidTest extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void hasOpCode0xb1() {
        new returnvoid().writeTo(binaryOutput);
        
        verify(binaryOutput).writeByte(0xb1);
    }

    @Test
    public void isOneByteLong() {
        assertThat(new returnvoid().lengthInBytes()).isEqualTo(1);
    }

    @Test
    public void hasNoOperands() {
        assertThat(new returnvoid().operandSizeInBytes()).isEqualTo(0);
    }

    @Test
    public void hasNoResult() {
        assertThat(new returnvoid().resultSizeInBytes()).isEqualTo(0);
    }
}