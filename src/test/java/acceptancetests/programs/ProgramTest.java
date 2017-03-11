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
import io.github.theangrydev.fluentbdd.core.FluentBdd;
import io.github.theangrydev.fluentbdd.core.ThenAssertion;
import io.github.theangrydev.fluentbdd.core.WithFluentBdd;
import io.github.theangrydev.fluentbdd.yatspec.FluentYatspec;
import io.github.theangrydev.fluentbdd.yatspec.WithFluentYatspec;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

@RunWith(SpecRunner.class)
public abstract class ProgramTest implements WithFluentBdd<ProgramOutput>, WithFluentYatspec {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Rule
    public final FluentBdd<ProgramOutput> fluentBdd = new FluentBdd<>();

    private final FluentYatspec fluentYatspec = new FluentYatspec();

    protected final ProgramTestInfrastructure infrastructure = new ProgramTestInfrastructure(new CompilerProcess(), new RuntimeProcess());
    protected final GivenTheCompilerHasCompiled theCompiler = new GivenTheCompilerHasCompiled(this, infrastructure);
    protected final WhenTheProgramIsRun theProgram = new WhenTheProgramIsRun(this, infrastructure);
    protected final ThenAssertion<ThenTheProgramOutput, ProgramOutput> theProgramOutput = ThenTheProgramOutput::new;

    @Override
    public FluentBdd<ProgramOutput> fluentBdd() {
        return fluentBdd;
    }

    @Override
    public FluentYatspec fluentYatspec() {
        return fluentYatspec;
    }

    @Before
    public void setUp() {
        infrastructure.setUp(temporaryFolder.getRoot().toPath());
    }
}
