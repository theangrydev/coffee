/*
 * Copyright 2016-2017 Liam Williams <liam.williams@zoho.com>.
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
package acceptancetests.infrastructure;

import io.github.theangrydev.coffee.domain.base.ValueType;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public final class ProgramArguments extends ValueType {
    private final List<String> arguments;

    private ProgramArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public static ProgramArguments programArguments(String arguments) {
        return new ProgramArguments(Arrays.asList(arguments.split("\\s")));
    }

    public List<String> arguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return arguments.stream().collect(joining(" "));
    }
}
