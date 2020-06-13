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
package acceptancetests.when;

import acceptancetests.infrastructure.*;
import io.github.theangrydev.fluentbdd.core.When;
import io.github.theangrydev.fluentbdd.yatspec.WriteOnlyTestItems;

import static acceptancetests.infrastructure.ProgramArguments.programArguments;

public class WhenTheProgramIsRun implements When<ProgramOutput> {

    private final WriteOnlyTestItems writeOnlyTestItems;
    private final ProgramTestInfrastructure infrastructure;

    private ProgramArguments arguments = programArguments("");
    private ProgramEntryPoint entryPoint;

    public WhenTheProgramIsRun(WriteOnlyTestItems writeOnlyTestItems, ProgramTestInfrastructure infrastructure) {
        this.writeOnlyTestItems = writeOnlyTestItems;
        this.infrastructure = infrastructure;
    }

    public WhenTheProgramIsRun isRun() {
        return this;
    }

    public WhenTheProgramIsRun withEntryPoint(String entryPoint) {
        this.entryPoint = ProgramEntryPoint.entryPoint(entryPoint);
        return this;
    }

    public WhenTheProgramIsRun andArguments(String arguments) {
        this.arguments = programArguments(arguments);
        return this;
    }

    @Override
    public ProgramOutput execute() {
        ProgramExecution programExecution = ProgramExecution.programExecution(entryPoint, arguments);
        writeOnlyTestItems.addToCapturedInputsAndOutputs("Program Execution", programExecution);

        ProgramOutput programOutput = infrastructure.invokeRuntime(programExecution);
        writeOnlyTestItems.addToCapturedInputsAndOutputs("Program Output", programOutput);
        return programOutput;
    }
}
