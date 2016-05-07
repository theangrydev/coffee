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
package acceptancetests.when;

import acceptancetests.infrastructure.*;
import io.github.theangrydev.yatspecfluent.ReadOnlyTestItems;
import io.github.theangrydev.yatspecfluent.When;

import static acceptancetests.infrastructure.ProgramArguments.programArguments;

public class WhenTheProgramIsRun implements When<ProgramExecution, ProgramOutput> {

    private final ReadOnlyTestItems readOnlyTestItems;
    private final ProgramTestInfrastructure infrastructure;

    private ProgramArguments arguments = programArguments("");
    private ProgramEntryPoint entryPoint;

    public WhenTheProgramIsRun(ReadOnlyTestItems readOnlyTestItems, ProgramTestInfrastructure infrastructure) {
        this.readOnlyTestItems = readOnlyTestItems;
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
    public ProgramExecution request() {
        ProgramExecution programExecution = ProgramExecution.programExecution(entryPoint, arguments);
        readOnlyTestItems.addToCapturedInputsAndOutputs("Program Execution", programExecution);
        return programExecution;
    }

    @Override
    public ProgramOutput response(ProgramExecution programExecution) {
        ProgramOutput programOutput = infrastructure.invokeRuntime(programExecution);
        readOnlyTestItems.addToCapturedInputsAndOutputs("Program Output", programOutput);
        return programOutput;
    }
}
