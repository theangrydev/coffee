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

import static com.google.common.collect.Sets.newHashSet;
import static io.github.theangrydev.coffee.infrastructure.classfile.Flag.combine;
import static io.github.theangrydev.coffee.infrastructure.classfile.MethodAccessFlag.*;
import static java.lang.Integer.parseInt;

@RunWith(TableRunner.class)
public class MethodAccessFlagTest implements WithAssertions {

    @Table({
        @Row({"ACC_PUBLIC", "0x001"}),
        @Row({"ACC_STATIC", "0x008"})
    })
    @Test
    public void flagValues(String flag, String hexValue) {
        assertThat(valueOf(flag).value()).isEqualTo(decimalValue(hexValue));
    }

    @Test
    public void combineOrsTheValues() {
        assertThat(combine(newHashSet(ACC_PUBLIC, ACC_STATIC))).isEqualTo(ACC_PUBLIC.value() | ACC_STATIC.value());
    }

    private int decimalValue(String hexadecimalValue) {
        return parseInt(hexadecimalValue.substring(2), 16);
    }
}