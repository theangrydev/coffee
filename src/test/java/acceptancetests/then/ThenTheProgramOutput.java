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
package acceptancetests.then;

import acceptancetests.infrastructure.ProgramOutput;

public class ThenTheProgramOutput implements org.assertj.core.api.WithAssertions {

    private final ProgramOutput programOutput;

    public ThenTheProgramOutput(ProgramOutput programOutput) {
        this.programOutput = programOutput;
    }

    public void isEqualTo(String output) {
        assertThat(programOutput.standardError).isEqualTo("");
        assertThat(programOutput.standardOutput).isEqualTo(output);
        assertThat(programOutput.exitCode).isEqualTo(0);
    }
}
