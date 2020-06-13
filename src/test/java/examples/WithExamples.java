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
package examples;

import io.github.theangrydev.coffee.infrastructure.classfile.constants.*;

import java.util.Random;

import static io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput.UNSIGNED_BYTE_MAX_VALUE;
import static io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput.UNSIGNED_SHORT_MAX_VALUE;
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

    default ConstantIndex<CONSTANT_Methodref_info> someMethodConstantReference() {
        return new ConstantIndex<>(someMethodConstant(), someUnsignedInt());
    }

    default CONSTANT_Methodref_info someMethodConstant() {
        return new CONSTANT_Methodref_info(someClassInfoConstantReference(), someNameAndTypeConstantReference());
    }

    default ConstantIndex<CONSTANT_NameAndType_info> someNameAndTypeConstantReference() {
        return new ConstantIndex<>(someNameAndTypeConstant(), someUnsignedInt());
    }

    default ConstantIndex<CONSTANT_Class_info> someClassInfoConstantReference() {
        return new ConstantIndex<>(someClassInfoConstant(), someUnsignedInt());
    }

    default CONSTANT_Class_info someClassInfoConstant() {
        return new CONSTANT_Class_info(someUtf8ConstantReference());
    }

    default CONSTANT_NameAndType_info someNameAndTypeConstant() {
        return new CONSTANT_NameAndType_info(someUtf8ConstantReference(), someUtf8ConstantReference());
    }

    default ConstantIndex<CONSTANT_Utf8_info> someUtf8ConstantReference() {
        return new ConstantIndex<>(someUtf8Constant(), someUnsignedInt());
    }

    default CONSTANT_Utf8_info someUtf8Constant() {
        return new CONSTANT_Utf8_info(someString());
    }
}
