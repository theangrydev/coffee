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
package acceptancetests.infrastructure;

import java.io.IOException;
import java.util.List;

import static acceptancetests.infrastructure.InputStreams.readInputStream;
import static acceptancetests.infrastructure.ProgramOutput.programOutput;
import static java.lang.String.format;

public class ProcessExecutor {

    public ProgramOutput execute(List<String> commandLine) {
        ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
        String commandString = String.join(" ", processBuilder.command());
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            String standardError = readInputStream(process.getErrorStream());
            String standardOutput = readInputStream(process.getInputStream());
            return programOutput(exitCode, standardOutput, standardError);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException(format("Could not execute command '%s'", commandString), e);
        }
    }
}
