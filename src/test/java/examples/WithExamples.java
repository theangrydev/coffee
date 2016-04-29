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

import static java.lang.System.currentTimeMillis;

@SuppressWarnings("PMD.TooManyMethods") // Intentional to contain lots of helpers
public interface WithExamples {
    Random RANDOM = new Random(currentTimeMillis());

    default byte someByte() {
        if (someBoolean()) {
            return somePositiveByte();
        } else {
            return someNegativeByte();
        }
    }

    default byte someNegativeByte() {
        return (byte) -somePositiveByte();
    }

    default byte somePositiveByte() {
        return (byte) RANDOM.nextInt(Byte.MAX_VALUE);
    }

    default short someShort() {
        if (someBoolean()) {
            return somePositiveShort();
        } else {
            return someNegativeShort();
        }
    }

    default boolean someBoolean() {
        return RANDOM.nextBoolean();
    }

    default short someNegativeShort() {
        return (short) -somePositiveShort();
    }

    default short somePositiveShort() {
        return (short) RANDOM.nextInt(Short.MAX_VALUE);
    }

    default int someInt() {
        if (someBoolean()) {
            return somePositiveInt();
        } else {
            return someNegativeInt();
        }
    }

    default int someNegativeInt() {
        return -somePositiveInt();
    }

    default int somePositiveInt() {
        return RANDOM.nextInt();
    }

    default String someString() {
        return "someString" + somePositiveInt();
    }
}
