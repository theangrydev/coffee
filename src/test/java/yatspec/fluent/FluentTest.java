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
package yatspec.fluent;

import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.InterestingGivens;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import com.googlecode.yatspec.state.givenwhenthen.WithTestState;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@SuppressWarnings("PMD.TooManyMethods") // Need lots of methods to make the interface fluent
public abstract class FluentTest<
        TestInfrastructure,
        SystemUnderTest extends yatspec.fluent.SystemUnderTest<TestInfrastructure, Request, Response>,
        Request,
        Response,
        Assertions> implements WithTestState, ReadOnlyTestItems {

    private final List<Primer<TestInfrastructure>> primers = new ArrayList<>();
    private final InterestingGivens interestingGivens = new InterestingGivens();
    private final CapturedInputAndOutputs capturedInputAndOutputs = new CapturedInputAndOutputs();
    private final SystemUnderTest systemUnderTest;
    protected final TestInfrastructure testInfrastructure;

    private Stage stage = Stage.GIVEN;
    private Assertions assertions;

    protected FluentTest(SystemUnderTest systemUnderTest, TestInfrastructure testInfrastructure) {
        this.systemUnderTest = systemUnderTest;
        this.testInfrastructure = testInfrastructure;
    }

    private enum Stage {
        GIVEN,
        WHEN,
        THEN
    }

    @Rule
    public TestWatcher makeSureThenIsUsed = new TestWatcher() {
        @Override
        protected void succeeded(Description description) {
            if (stage != Stage.THEN ) {
                throw new IllegalStateException("Each test needs at least a 'when' and a 'then'");
            }
        }
    };

    @Override
    public TestState testState() {
        TestState testState = new TestState();
        testState.interestingGivens = interestingGivens;
        testState.capturedInputAndOutputs = capturedInputAndOutputs;
        return testState;
    }

    @SuppressWarnings("unused")
    protected <D extends Primer<TestInfrastructure>> D given(D dependency, String messageToDisplay) {
        return given(dependency);
    }

    @SuppressWarnings("unused")
    protected <D extends Primer<TestInfrastructure>> D and(D dependency, String messageToDisplay) {
        return given(dependency);
    }

    protected <D extends Primer<TestInfrastructure>> D and(D dependency) {
        return given(dependency);
    }

    protected <D extends Primer<TestInfrastructure>> D given(D dependency) {
        if (stage != Stage.GIVEN) {
            throw new IllegalStateException("The 'given' steps must be specified before the 'when' and 'then' steps");

        }
        boolean alreadyHadGiven = primers.stream().map(Object::getClass).anyMatch(aClass -> aClass.equals(dependency.getClass()));
        if (alreadyHadGiven) {
            throw new IllegalStateException(format("The dependency '%s' has already specified a 'given' step", dependency));
        }
        if (!primers.isEmpty()) {
            primePreviousGiven();
        }
        primers.add(dependency);
        return dependency;
    }

    private void primePreviousGiven() {
        if (!primers.isEmpty()) {
            primers.get(primers.size() - 1).prime(this, testInfrastructure);
        }
    }

    @SuppressWarnings("unused")
    protected SystemUnderTest when(String messageToDisplay) {
        return when();
    }

    protected SystemUnderTest when() {
        if (stage != Stage.GIVEN) {
            throw new IllegalStateException("There should only be one 'when', after the 'given' and before the 'then'");
        }
        if (!primers.isEmpty()) {
            primePreviousGiven();
        }
        stage = Stage.WHEN;
        return systemUnderTest;
    }

    @SuppressWarnings("unused")
    protected Assertions then(String messageToDisplay)  {
        return then();
    }

    protected Assertions then() {
        if (stage == Stage.GIVEN) {
            throw new IllegalStateException("The initial 'then' should be after the 'when'");
        }
        if (stage == Stage.THEN) {
            throw new IllegalStateException("After the first 'then' you should use 'and'");
        }
        Request request = systemUnderTest.request(this, testInfrastructure);
        if (request == null) {
            throw new IllegalStateException(format("%s request was null", systemUnderTest));
        }
        Response response = systemUnderTest.call(request, this, testInfrastructure);
        if (response == null) {
            throw new IllegalStateException(format("%s response was null", systemUnderTest));
        }
        stage = Stage.THEN;
        assertions = responseAssertions(response);
        return assertions;
    }

    protected Assertions and() {
        if (stage != Stage.THEN) {
            throw new IllegalStateException("All of the 'then' statements after the initial then should be 'and'");
        }
        return assertions;
    }

    protected abstract Assertions responseAssertions(Response response);

    @Override
    public void addToGivens(String key, Object instance) {
        interestingGivens.add(key, instance);
    }

    @Override
    public void addToCapturedInputsAndOutputs(String key, Object instance) {
        capturedInputAndOutputs.add(key, instance);
    }
}
