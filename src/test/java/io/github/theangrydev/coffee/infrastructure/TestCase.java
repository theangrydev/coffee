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
package io.github.theangrydev.coffee.infrastructure;

import assertions.WithMockito;
import examples.WithExamples;
import org.junit.After;
import org.junit.Before;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.internal.util.MockUtil;

import java.lang.reflect.Field;

import static java.util.Arrays.stream;

public class TestCase implements WithMockito, WithExamples, org.assertj.core.api.WithAssertions {

    private final MockUtil mockUtil = new MockUtil();
    private InOrder inOrder;

    @Before
    public void setUp() {
        inOrder = Mockito.inOrder(allMocks());
    }

    @After
    public void tearDown() {
        if (inOrder != null) {
            inOrder.verifyNoMoreInteractions();
        }
    }

    private Object[] allMocks() {
        return stream(getClass().getDeclaredFields()).map(this::fieldValue).filter(mockUtil::isMock).toArray();
    }

    private Object fieldValue(Field field) {
        try {
            field.setAccessible(true);
            return field.get(this);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public InOrder inOrder() {
        return inOrder;
    }
}
