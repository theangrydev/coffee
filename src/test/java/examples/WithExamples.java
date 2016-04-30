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

import com.google.common.primitives.Ints;
import com.google.common.primitives.Shorts;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

@SuppressWarnings("PMD.TooManyMethods") // Intentional to contain lots of helpers
public interface WithExamples {
    Random RANDOM = new Random(currentTimeMillis());

    default byte someByte() {
        byte[] bytes = new byte[1];
        RANDOM.nextBytes(bytes);
        return bytes[0];
    }

    default short someShort() {
        byte[] bytes = new byte[Shorts.BYTES];
        RANDOM.nextBytes(bytes);
        return Shorts.fromByteArray(bytes);
    }

    default int someInt() {
        byte[] bytes = new byte[Ints.BYTES];
        RANDOM.nextBytes(bytes);
        return Ints.fromByteArray(bytes);
    }

    default String someString() {
        return "someString" + someInt();
    }
}
