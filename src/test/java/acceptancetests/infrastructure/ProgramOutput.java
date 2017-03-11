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

public final class ProgramOutput extends ValueType {
    public final int exitCode;
    public final String standardOutput;
    public final String standardError;

    private ProgramOutput(int exitCode, String standardOutput, String standardError) {
        this.exitCode = exitCode;
        this.standardOutput = standardOutput;
        this.standardError = standardError;
    }

    public static ProgramOutput programOutput(int exitCode, String standardOutput, String standardError) {
        return new ProgramOutput(exitCode, standardOutput.trim(), standardError.trim());
    }
}
