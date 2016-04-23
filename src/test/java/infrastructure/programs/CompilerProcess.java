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
package infrastructure.programs;

import infrastructure.ProcessExecutor;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CompilerProcess {

    private final ProcessExecutor processExecutor = new ProcessExecutor();

    public void compile(Path sourceCode) {
        String javac = Paths.get(System.getProperty("java.home")).getParent().resolve("bin").resolve("javac").toString();
        processExecutor.execute(javac, "\"" + sourceCode.toAbsolutePath().toString() + "\"");
    }
}
