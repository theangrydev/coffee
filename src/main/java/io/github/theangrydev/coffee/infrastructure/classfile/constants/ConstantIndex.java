package io.github.theangrydev.coffee.infrastructure.classfile.constants;

import io.github.theangrydev.coffee.domain.base.ValueType;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryOutput;
import io.github.theangrydev.coffee.infrastructure.classfile.writing.BinaryWriter;

public class ConstantIndex<T extends BinaryWriter> extends ValueType implements BinaryWriter {

    private final T constant;
    private final int index;

    public ConstantIndex(T constant, int index) {
        this.constant = constant;
        this.index = index;
    }

    @Override
    public void writeTo(BinaryOutput binaryOutput) {
        binaryOutput.writeShort(index);
    }
}
