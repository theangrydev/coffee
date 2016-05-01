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
package io.github.theangrydev.coffee.infrastructure.classfile;

import assertions.WithAssertions;
import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.github.theangrydev.coffee.infrastructure.classfile.ClassAccessFlag.valueOf;
import static java.lang.Integer.parseInt;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
 */
@RunWith(TableRunner.class)
public class ClassAccessFlagTest implements WithAssertions {

    @Table({
        @Row({"ACC_PUBLIC", "0x0001"}),
        @Row({"ACC_SUPER", "0x0020"})
    })
    @Test
    public void flagValues(String flag, String hexValue) {
        assertThat(valueOf(flag).value()).isEqualTo(decimalValue(hexValue));
    }

    private int decimalValue(String hexadecimalValue) {
        return parseInt(hexadecimalValue.substring(2), 16);
    }
}