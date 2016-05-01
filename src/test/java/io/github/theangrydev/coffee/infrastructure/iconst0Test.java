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

import org.junit.Test;

public class iconst0Test extends TestCase {

    private final BinaryOutput binaryOutput = mock(BinaryOutput.class);

    @Test
    public void hasOpCode0x2a() {
        new iconst0().writeTo(binaryOutput);

        verify(binaryOutput).writeByte(0x3);
    }

    @Test
    public void isOneByteLong() {
        assertThat(new iconst0().lengthInBytes()).isEqualTo(1);
    }

    @Test
    public void hasNoOperands() {
        assertThat(new iconst0().operandSizeInBytes()).isEqualTo(0);
    }

    @Test
    public void resultIsOneByteLong() {
        assertThat(new iconst0().resultSizeInBytes()).isEqualTo(1);
    }
}