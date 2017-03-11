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
package io.github.theangrydev.coffee.infrastructure.classfile.writing;

import assertions.WithMockito;
import examples.WithExamples;
import org.junit.Test;

import java.io.DataOutput;
import java.io.IOException;

/**
 * https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4
 */
public class BinaryOutputTest implements WithMockito, WithExamples, org.assertj.core.api.WithAssertions {

    private final DataOutput dataOutput = mock(DataOutput.class);
    private final BinaryOutput binaryOutput = new BinaryOutput(dataOutput);

    @Test
    public void fileNotFoundRethrownAsRuntimeException() {
        assertThatThrownBy(() -> BinaryOutput.fileBinaryOutput("bad\u0000")).hasMessage("Could not write to file 'bad\u0000'");
    }

    @Test
    public void maximumByteThatCanBeWrittenIs256() throws IOException {
        assertThatThrownBy(() -> binaryOutput.writeByte(256)).hasMessage("byte can be at most 255 but was 256");
        verifyThat(() -> binaryOutput.writeByte(255)).willMake(dataOutput).writeByte(255);
    }

    @Test
    public void bytesMustNotBeNegative() throws IOException {
        assertThatThrownBy(() -> binaryOutput.writeByte(-1)).hasMessage("byte must not be negative but was -1");
        verifyThat(() -> binaryOutput.writeByte(0)).willMake(dataOutput).writeByte(0);
    }

    @Test
    public void maximumShortThatCanBeWrittenIs32767() throws IOException {
        assertThatThrownBy(() -> binaryOutput.writeShort(65536)).hasMessage("short can be at most 65535 but was 65536");
        verifyThat(() -> binaryOutput.writeShort(65535)).willMake(dataOutput).writeShort(65535);
    }

    @Test
    public void shortsMustNotBeNegative() throws IOException {
        assertThatThrownBy(() -> binaryOutput.writeShort(-1)).hasMessage("short must not be negative but was -1");
        verifyThat(() -> binaryOutput.writeShort(0)).willMake(dataOutput).writeShort(0);
    }

    @Test
    public void intsMustNotBeNegative() throws IOException {
        assertThatThrownBy(() -> binaryOutput.writeInt(-1)).hasMessage("int must not be negative but was -1");
        verifyThat(() -> binaryOutput.writeInt(0)).willMake(dataOutput).writeInt(0);
    }

    @Test
    public void exceptionsAreRethrownAsRuntimeExceptions() throws IOException {
        given(dataOutput, willThrow(new IOException())).when().writeByte(anyByte());
        assertThatThrownBy(() -> binaryOutput.writeByte(someUnsignedByte())).isInstanceOf(RuntimeException.class);

        given(dataOutput, willThrow(new IOException())).when().writeShort(anyShort());
        assertThatThrownBy(() -> binaryOutput.writeShort(someUnsignedShort())).isInstanceOf(RuntimeException.class);

        given(dataOutput, willThrow(new IOException())).when().writeInt(anyInt());
        assertThatThrownBy(() -> binaryOutput.writeInt(someUnsignedInt())).isInstanceOf(RuntimeException.class);

        given(dataOutput, willThrow(new IOException())).when().writeUTF(anyString());
        assertThatThrownBy(() -> binaryOutput.writeUTF8(someString())).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void validValuesAreDelegatedToTheDataOutput() throws IOException {
        verifyThat(() -> binaryOutput.writeByte(0)).willMake(dataOutput).writeByte(0);
        verifyThat(() -> binaryOutput.writeShort(0)).willMake(dataOutput).writeShort(0);
        verifyThat(() -> binaryOutput.writeInt(0)).willMake(dataOutput).writeInt(0);
        verifyThat(() -> binaryOutput.writeUTF8("")).willMake(dataOutput).writeUTF("");
    }
}