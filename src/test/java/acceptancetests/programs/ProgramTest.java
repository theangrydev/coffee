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
package acceptancetests.programs;

import acceptancetests.given.GivenTheCompilerHasCompiled;
import acceptancetests.then.ThenTheProgramOutput;
import acceptancetests.when.WhenTheProgramIsRun;
import com.googlecode.yatspec.junit.SpecRunner;
import infrastructure.programs.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import yatspec.fluent.ThenFactory;
import yatspec.fluent.FluentTest;

@RunWith(SpecRunner.class)
public abstract class ProgramTest extends FluentTest<ProgramTestInfrastructure, ProgramExecution, ProgramOutput> {

    protected final GivenTheCompilerHasCompiled theCompiler = new GivenTheCompilerHasCompiled();
    protected final WhenTheProgramIsRun theProgram = new WhenTheProgramIsRun();
    protected final ThenFactory<ThenTheProgramOutput, ProgramOutput> theProgramOutput = ThenTheProgramOutput::new;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    ProgramTest() {
        super(new ProgramTestInfrastructure(new CompilerProcess(), new RuntimeProcess()));
    }

    @Override
    protected void setUp(ProgramTestInfrastructure programTestInfrastructure) {
        programTestInfrastructure.setUp(temporaryFolder.getRoot().toPath());
    }

    @Override
    protected void tearDown(ProgramTestInfrastructure programTestInfrastructure) {
        temporaryFolder.delete();
    }
}
