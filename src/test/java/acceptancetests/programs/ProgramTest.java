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

import acceptancetests.when.WhenTheProgramIsRun;
import acceptancetests.then.ThenTheProgramOutput;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.InterestingGivens;
import acceptancetests.given.GivenTheCompilerHasCompiled;
import infrastructure.programs.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import yatspec.fluent.FluentTest;

@RunWith(SpecRunner.class)
public abstract class ProgramTest extends FluentTest<ProgramTestInfrastructure, WhenTheProgramIsRun, ProgramExecution, ProgramOutput, ThenTheProgramOutput> {

    private final ProgramTestInfrastructure programTestInfrastructure = new ProgramTestInfrastructure(new CompilerProcess(), new RuntimeProcess());
    private final InterestingGivens interestingGivens = new InterestingGivens();
    private final CapturedInputAndOutputs capturedInputAndOutputs = new CapturedInputAndOutputs();

    protected final GivenTheCompilerHasCompiled theCompiler = new GivenTheCompilerHasCompiled();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Override
    protected WhenTheProgramIsRun systemUnderTest() {
        return new WhenTheProgramIsRun();
    }

    @Override
    protected void beforeSystemHasBeenCalled(ProgramExecution execution) {

    }

    @Override
    protected void afterSystemHasBeenCalled(ProgramOutput programOutput) {

    }

    @Override
    protected ThenTheProgramOutput responseAssertions(ProgramOutput programOutput) {
        return new ThenTheProgramOutput(programOutput);
    }

    @Override
    protected ProgramTestInfrastructure testInfrastructure() {
        return programTestInfrastructure;
    }

    @Override
    public CapturedInputAndOutputs capturedInputAndOutputs() {
        return capturedInputAndOutputs;
    }

    @Override
    public InterestingGivens interestingGivens() {
        return interestingGivens;
    }

    @Before
    public void setUp() {
        programTestInfrastructure.setUp(temporaryFolder.getRoot().toPath());
    }
}
