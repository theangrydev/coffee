package io.github.theangrydev.coffee.domain.base;

import assertions.WithAssertions;
import org.junit.Test;

public class ValueTypeTest implements WithAssertions {

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
        assertThat(new TestType(1, "one").hashCode()).isEqualTo(new TestType(1, "one").hashCode());
    }

    @Test
    public void toStringContainsFieldNamesAndValues() {
        assertThat(new TestType(1, "one").toString()).contains("primitive=1", "object=one");
    }

    @SuppressWarnings("unused")
    private static class TestType extends ValueType {
        private final int primitive;
        private final String object;

        private TestType(int primitive, String object) {
            this.primitive = primitive;
            this.object = object;
        }
    }
}