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

import assertions.WithAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static infrastructure.programs.ProgramEntryPoint.entryPoint;
import static infrastructure.programs.ProgramExecution.programExecution;

public class ProgramTestInfrastructureTest implements WithAssertions {

    private final ProgramTestInfrastructure infrastructure = new ProgramTestInfrastructure(new CompilerProcess(), new RuntimeProcess());

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void sourceCodeLoadsFilesFromTestResources() {
        File file = infrastructure.sourceCode("HelloWorld.code");

        assertThat(file).isEqualTo(new File("src/test/resources/code/HelloWorld.code"));
    }

    @Test
    public void compilationProducesAFileThatCanBeRun() {
        File sourceCode = infrastructure.sourceCode("HelloWorld.java");
        infrastructure.copyToCompilationDirectory(sourceCode);
        infrastructure.compile("HelloWorld.java");

        ProgramExecution execution = programExecution(entryPoint("HelloWorld"), ProgramArguments.programArguments(""));
        ProgramOutput programOutput = infrastructure.invokeRuntime(execution);

        assertThat(programOutput).hasToString("Hello World");
    }

    @Before
    public void setUp() {
        infrastructure.setUp(temporaryFolder.getRoot().toPath());
    }
}