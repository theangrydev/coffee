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
package io.github.theangrydev.coffee.domain.base;

import org.junit.Test;

public class ValueTypeTest implements org.assertj.core.api.WithAssertions {

    @Test
    public void equalIfTheFieldsAreEqual() {
        assertThat(new TestType(1, "one")).isEqualTo(new TestType(1, "one"));
    }

    @Test
    public void notEqualIfTheFieldsAreDifferent() {
        assertThat(new TestType(1, "one")).isNotEqualTo(new TestType(2, "two"));
    }

    @Test
    public void sameHashCodeIfTheFieldsAreEqual() {
        assertThat(new TestType(1, "one").hashCode()).isNotEqualTo(new TestType(2, "two").hashCode());
    }

    @Test
    public void differentHashCodeIfTheFieldsAreDifferent() {
        assertThat(new TestType(1, "one").hashCode()).isNotEqualTo(new TestType(2, "two").hashCode());
    }

    @Test
    public void toStringContainsTypeNameFieldNamesAndValues() {
        assertThat(new TestType(1, "one").toString()).contains("TestType", "primitive=1", "object=one");
    }

    @SuppressWarnings("unused")
    private static class TestType extends ValueType {
        private final int primitive;
        private final String object;

        TestType(int primitive, String object) {
            this.primitive = primitive;
            this.object = object;
        }
    }
}