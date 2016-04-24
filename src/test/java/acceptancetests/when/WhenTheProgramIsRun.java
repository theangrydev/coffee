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

import infrastructure.programs.*;
import yatspec.fluent.ReadOnlyTestItems;
import yatspec.fluent.SystemUnderTest;

public class WhenTheProgramIsRun implements SystemUnderTest<ProgramTestInfrastructure, ProgramExecution, ProgramOutput> {
    private ProgramArguments arguments;
    private ProgramEntryPoint entryPoint;

    public WhenTheProgramIsRun isRun() {
        return this;
    }

    public WhenTheProgramIsRun withEntryPoint(String entryPoint) {
        this.entryPoint = ProgramEntryPoint.entryPoint(entryPoint);
        return this;
    }

    public WhenTheProgramIsRun andArguments(String arguments) {
        this.arguments = ProgramArguments.programArguments(arguments);
        return this;
    }

    @Override
    public ProgramExecution request(ReadOnlyTestItems readOnlyTestItems, ProgramTestInfrastructure programTestInfrastructure) {
        ProgramExecution programExecution = ProgramExecution.programExecution(entryPoint, arguments);
        readOnlyTestItems.addToCapturedInputsAndOutputs("Program Execution", programExecution);
        return programExecution;
    }

    @Override
    public ProgramOutput call(ProgramExecution programExecution, ReadOnlyTestItems readOnlyTestItems, ProgramTestInfrastructure programTestInfrastructure) {
        ProgramOutput programOutput = programTestInfrastructure.invokeRuntime(programExecution);
        readOnlyTestItems.addToCapturedInputsAndOutputs("Program Output", programOutput);
        return programOutput;
    }
}
