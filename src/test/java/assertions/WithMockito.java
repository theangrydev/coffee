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

    default VerifyThat verifyThat(Runnable methodCall) {
        methodCall.run();
        return new VerifyThat();
    }

    class VerifyThat {
        public  <T> T willMake(T mock) {
            return BDDMockito.verify(mock);
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
        return BDDMockito.anyShort();
    }

    default int anyByte() {
        return BDDMockito.anyByte();
    }

    class GivenAction<T> {
        private final T mock;

        GivenAction(T mock) {
            this.mock = mock;
        }

        public T when() {
            return mock;
        }
    }

    default InOrder inOrder() {
        return null;
    }
}
