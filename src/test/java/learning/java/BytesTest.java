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
package learning.java;

import org.junit.Test;

public class BytesTest implements org.assertj.core.api.WithAssertions {

    @Test
    public void bytesAreSigned() {
        assertThat((byte) 128).isEqualTo((byte) -128);
    }

    @Test
    public void castingIntToByteAndBackLosesInformationBecauseOfSignBit() {
        assertThat((int) ((byte) 255)).isEqualTo(-1);
    }
}
