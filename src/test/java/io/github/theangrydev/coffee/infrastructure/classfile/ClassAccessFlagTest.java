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
import org.junit.Test;

import static io.github.theangrydev.coffee.infrastructure.classfile.ClassAccessFlag.ACC_PUBLIC;
import static io.github.theangrydev.coffee.infrastructure.classfile.ClassAccessFlag.ACC_SUPER;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.1-200-E.1
 */
public class ClassAccessFlagTest implements WithAssertions {

    //TODO: https://github.com/hcoles/pitest/issues/263
    @Test
    public void flagValues() {
        flagValues(ACC_PUBLIC, 0x0001);
        flagValues(ACC_SUPER, 0x0020);
    }

    private void flagValues(ClassAccessFlag flag, int hexValue) {
        assertThat(flag.value()).isEqualTo(hexValue);
    }
}