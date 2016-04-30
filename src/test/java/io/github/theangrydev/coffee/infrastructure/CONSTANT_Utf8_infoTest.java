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

import assertions.WithAssertions;
import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4.7
 */
@RunWith(TableRunner.class)
public class CONSTANT_Utf8_infoTest implements WithByteArrayBinaryWriter, WithAssertions {

    @Table({
        @Row(""),
        @Row("non empty string")
    })
    @Test
    public void writesTagThenLengthBytesThenUtf8Bytes(String string) {
        assertThat(bytesWrittenBy(new CONSTANT_Utf8_info(string))).containsExactly(1, string.length() >> 8, string.length(), string.getBytes(UTF_8));
    }
}