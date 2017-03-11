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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static acceptancetests.infrastructure.ProgramEntryPoint.entryPoint;
import static acceptancetests.infrastructure.ProgramExecution.programExecution;

public class ProgramTestInfrastructureTest implements org.assertj.core.api.WithAssertions {

    private final ProgramTestInfrastructure infrastructure = new ProgramTestInfrastructure(new CompilerProcess(), new RuntimeProcess());

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void sourceCodeLoadsFilesFromTestResources() {
        File file = infrastructure.sourceCode("AdditionProgram.coffee");

        assertThat(file).isEqualTo(new File("src/test/resources/code/AdditionProgram.coffee"));
    }

    @Test
    public void compilationProducesAFileThatCanBeRun() {
        File sourceCode = infrastructure.sourceCode("AdditionProgram.coffee");
        infrastructure.copyToCompilationDirectory(sourceCode);
        infrastructure.compile("AdditionProgram.coffee");

        ProgramExecution execution = programExecution(entryPoint("AdditionProgram"), ProgramArguments.programArguments("3 4"));
        ProgramOutput programOutput = infrastructure.invokeRuntime(execution);

        assertThat(programOutput.exitCode).isEqualTo(0);
        assertThat(programOutput.standardOutput).isEqualTo("7");
        assertThat(programOutput.standardError).isEqualTo("");
    }

    @Before
    public void setUp() {
        infrastructure.setUp(temporaryFolder.getRoot().toPath());
    }
}