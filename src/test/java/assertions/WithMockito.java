/*
 * Copyright 2016-2020 Liam Williams <liam.williams@zoho.com>.
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
package assertions;

import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

@SuppressWarnings("PMD.TooManyMethods") // Intentional to contain lots of helpers
public interface WithMockito {

    default <T> T mock(Class<T> classToMock) {
        return BDDMockito.mock(classToMock);
    }

    @SuppressWarnings("unchecked")
    default <T, S extends T> S mockGeneric(Class<T> genericClassToMock) {
        return (S) BDDMockito.mock(genericClassToMock);
    }

    default VerifyThat verifyThat(Runnable methodCall) {
        methodCall.run();
        return new VerifyThat(this);
    }

    class VerifyThat {

        private final WithMockito withMockito;

        public VerifyThat(WithMockito withMockito) {
            this.withMockito = withMockito;
        }

        public  <T> T willMake(T mock) {
            return withMockito.verify(mock);
        }
    }

    default <T> T verify(T mock, VerificationMode verificationMode) {
        InOrder inOrder = inOrder();
        if (inOrder == null) {
            return BDDMockito.verify(mock, verificationMode);
        } else {
            return inOrder.verify(mock, verificationMode);
        }
    }

    default <T> T verify(T mock) {
        return verify(mock, times(1));
    }

    default VerificationMode times(int number) {
        return Mockito.times(number);
    }

    default <T> BDDMockito.BDDMyOngoingStubbing<T> given(T methodCall) {
        return BDDMockito.given(methodCall);
    }

    default <T> GivenAction<T> given(T mock, BDDMockito.BDDStubber action) {
        return new GivenAction<>(action.given(mock));
    }

    default BDDMockito.BDDStubber willThrow(Throwable throwable) {
        return BDDMockito.willThrow(throwable);
    }

    default <T> T any() {
        return BDDMockito.any();
    }

    default String anyString() {
        return BDDMockito.anyString();
    }

    default int anyInt() {
        return BDDMockito.anyInt();
    }

    default int anyShort() {
        return anyInt();
    }

    default int anyByte() {
        return anyInt();
    }

    class GivenAction<T> {
        private final T mock;

        public GivenAction(T mock) {
            this.mock = mock;
        }

        public T when() {
            return mock;
        }
    }

    /**
     * If this method is implemented then {@link #verify} will verify mocks in order.
     *
     * @return An {@link InOrder} with all the mocks used in the current test.
     */
    default InOrder inOrder() {
        return null;
    }
}
