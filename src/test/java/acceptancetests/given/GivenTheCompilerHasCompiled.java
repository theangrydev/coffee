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
package acceptancetests.given;

import acceptancetests.infrastructure.ProgramTestInfrastructure;
import io.github.theangrydev.fluentbdd.core.Given;
import io.github.theangrydev.fluentbdd.yatspec.WriteOnlyTestItems;

import java.io.File;

import static io.github.theangrydev.coffee.infrastructure.FileHelpers.readContent;

public class GivenTheCompilerHasCompiled implements Given {

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final ProgramTestInfrastructure infrastructure;

    private String sourceCodeFileName;

    public GivenTheCompilerHasCompiled(WriteOnlyTestItems writeOnlyTestItems, ProgramTestInfrastructure infrastructure) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.infrastructure = infrastructure;
    }

    @Override
    public void prime() {
        File sourceCodeFile = infrastructure.sourceCode(sourceCodeFileName);
        String code = readContent(sourceCodeFile);
        writeOnlyTestItems.addToCapturedInputsAndOutputs(sourceCodeFileName, code);

        infrastructure.copyToCompilationDirectory(sourceCodeFile);
        infrastructure.compile(sourceCodeFileName);
    }

    public GivenTheCompilerHasCompiled hasCompiled(String sourceCodeFileName) {
        this.sourceCodeFileName = sourceCodeFileName;
        return this;
    }
}
