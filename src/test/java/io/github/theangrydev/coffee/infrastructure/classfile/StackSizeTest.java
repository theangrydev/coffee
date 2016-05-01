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

public class StackSizeTest implements WithAssertions {

    @Test
    public void maxIsInitialBeforeAnyOperations() {
        int initial = 1;

        StackSize stackSize = new StackSize(initial);

        assertThat(stackSize.max()).isEqualTo(initial);
    }

    @Test
    public void pushingCanIncreaseTheMaximum() {
        int initial = 1;
        int firstPush = 2;
        int secondPush = 3;
        int expectedMax = initial + firstPush + secondPush;

        StackSize stackSize = new StackSize(initial);
        stackSize.push(firstPush);
        stackSize.push(secondPush);

        assertThat(stackSize.max()).isEqualTo(expectedMax);
    }

    @Test
    public void poppingReducesTheCurrentStackSize() {
        int initial = 1;
        int pop = 1;
        int push = 2;
        int expectedMax = initial - pop + push;

        StackSize stackSize = new StackSize(initial);
        stackSize.pop(pop);
        stackSize.push(push);

        assertThat(stackSize.max()).isEqualTo(expectedMax);
    }
}