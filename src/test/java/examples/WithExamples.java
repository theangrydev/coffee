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
package examples;

import java.util.Random;

import static io.github.theangrydev.coffee.infrastructure.BinaryOutput.UNSIGNED_BYTE_MAX_VALUE;
import static io.github.theangrydev.coffee.infrastructure.BinaryOutput.UNSIGNED_SHORT_MAX_VALUE;
import static java.lang.System.currentTimeMillis;

@SuppressWarnings("PMD.TooManyMethods") // Intentional to contain lots of helpers
public interface WithExamples {
    Random RANDOM = new Random(currentTimeMillis());

    default int someUnsignedByte() {
        return RANDOM.nextInt(UNSIGNED_BYTE_MAX_VALUE + 1);
    }

    default int someUnsignedShort() {
        return RANDOM.nextInt(UNSIGNED_SHORT_MAX_VALUE + 1);
    }

    default int someUnsignedInt() {
        return Math.abs(RANDOM.nextInt(Integer.MAX_VALUE));
    }

    default String someString() {
        return "someString" + someUnsignedInt();
    }

    default boolean someBoolean() {
        return RANDOM.nextBoolean();
    }
}
