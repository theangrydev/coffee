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
package io.github.theangrydev.coffee.infrastructure.classfile;

public class StackSize {

    private int current;
    private int max;

    public StackSize(int initial) {
        current = initial;
        max = current;
    }

    public void push(int numberOfBytes) {
        current+= numberOfBytes;
        max = Math.max(current, max);
    }

    public void pop(int numberOfBytes) {
        current-= numberOfBytes;
    }

    public int max() {
        return max;
    }
}
