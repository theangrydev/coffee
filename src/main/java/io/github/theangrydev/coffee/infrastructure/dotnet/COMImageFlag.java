package io.github.theangrydev.coffee.infrastructure.dotnet;

import io.github.theangrydev.coffee.infrastructure.Flag;

public enum COMImageFlag implements Flag {
    COMIMAGE_FLAGS_ILONLY(0x00000001)
    ;

    private final int value;

    COMImageFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
