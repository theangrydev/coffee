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
package acceptancetests.given;

import infrastructure.programs.ProgramTestInfrastructure;
import yatspec.fluent.ReadOnlyTestItems;
import yatspec.fluent.Primer;

import java.io.File;

import static infrastructure.Files.readContent;

public class GivenTheCompilerHasCompiled implements Primer<ProgramTestInfrastructure> {

    private String sourceCodeFileName;

    @Override
    public void prime(ReadOnlyTestItems readOnlyTestItems, ProgramTestInfrastructure programTestInfrastructure) {
        File sourceCodeFile = programTestInfrastructure.sourceCode(sourceCodeFileName);
        String code = readContent(sourceCodeFile);
        readOnlyTestItems.addToCapturedInputsAndOutputs(sourceCodeFileName, code);

        programTestInfrastructure.copyToCompilationDirectory(sourceCodeFile);
        programTestInfrastructure.compile(sourceCodeFileName);
    }

    public GivenTheCompilerHasCompiled hasCompiled(String sourceCodeFileName) {
        this.sourceCodeFileName = sourceCodeFileName;
        return this;
    }
}
