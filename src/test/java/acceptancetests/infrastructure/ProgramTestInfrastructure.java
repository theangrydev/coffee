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
package acceptancetests.infrastructure;

import io.github.theangrydev.coffee.entrypoint.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;

public class ProgramTestInfrastructure {

    private final Path sourceDirectory = Paths.get("src/test/resources/code");
    private Path compilationDirectory;

    private final CompilerProcess compilerProcess;
    private final RuntimeProcess runtimeProcess;

    public ProgramTestInfrastructure(CompilerProcess compilerProcess, RuntimeProcess runtimeProcess) {
        this.compilerProcess = compilerProcess;
        this.runtimeProcess = runtimeProcess;
    }

    public void setUp(Path baseDirectory) {
        compilationDirectory = baseDirectory.resolve("compilation");
        createCompilationDirectory(compilationDirectory);
    }

    private static void createCompilationDirectory(Path compilationDirectory) {
        try {
            java.nio.file.Files.createDirectory(compilationDirectory);
        } catch (IOException e) {
            throw new IllegalStateException("Could not create compilation directory", e);
        }
    }

    public ProgramOutput invokeRuntime(ProgramExecution execution) {
        return runtimeProcess.run(compilationDirectory, execution);
    }

    @SuppressWarnings("PMD") // TODO: why suprress?
    public void compile(String sourceCode) {
        Path sourceCodeFile = compilationDirectory.resolve(sourceCode);
        if (acrossProcessBoundary()) {
            compilerProcess.compile(sourceCodeFile);
        } else {
            CommandLine.main(sourceCodeFile.toString());
        }
    }

    private boolean acrossProcessBoundary() {
        return "true".equals(System.getProperty("across.process.boundary"));
    }

    public File sourceCode(String fileName) {
        return sourceDirectory.resolve(fileName).toFile();
    }

    public void copyToCompilationDirectory(File fileToBeCompiled) {
        try {
            java.nio.file.Files.copy(new FileInputStream(fileToBeCompiled), compilationDirectory.resolve(fileToBeCompiled.getName()));
        } catch (IOException e) {
            throw new IllegalStateException(format("Could not copy file '%s' to compilation directory", fileToBeCompiled), e);
        }
    }
}
