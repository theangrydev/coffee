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
import acceptancetests.infrastructure.*;
import acceptancetests.then.ThenTheProgramOutput;
import acceptancetests.when.WhenTheProgramIsRun;
import com.googlecode.yatspec.junit.SpecRunner;
import io.github.theangrydev.yatspecfluent.FluentTest;
import io.github.theangrydev.yatspecfluent.ThenFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(SpecRunner.class)
public abstract class ProgramTest extends FluentTest<ProgramExecution, ProgramOutput> {

    protected final ProgramTestInfrastructure infrastructure = new ProgramTestInfrastructure(new CompilerProcess(), new RuntimeProcess());
    protected final GivenTheCompilerHasCompiled theCompiler = new GivenTheCompilerHasCompiled(this, infrastructure);
    protected final WhenTheProgramIsRun theProgram = new WhenTheProgramIsRun(this, infrastructure);
    protected final ThenFactory<ThenTheProgramOutput, ProgramOutput> theProgramOutput = ThenTheProgramOutput::new;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        infrastructure.setUp(temporaryFolder.getRoot().toPath());
    }

    @After
    public void tearDown() {
        temporaryFolder.delete();
    }
}
