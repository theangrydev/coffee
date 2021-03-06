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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static acceptancetests.infrastructure.Streams.concat;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class RuntimeProcess {

    private final ProcessExecutor processExecutor = new ProcessExecutor();

    public ProgramOutput run(Path classPath, ProgramExecution execution) {
        String java = Paths.get(System.getProperty("java.home")).resolve("bin").resolve("java").toString();
        List<String> commandLine = concat(asList(java, "-cp", classPath.toString(), execution.entryPoint().toString()), execution.arguments().arguments()).collect(toList());
        return processExecutor.execute(commandLine);
    }
}
