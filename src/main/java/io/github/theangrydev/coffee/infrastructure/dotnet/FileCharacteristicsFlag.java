package io.github.theangrydev.coffee.infrastructure.dotnet;

import io.github.theangrydev.coffee.infrastructure.Flag;

public enum FileCharacteristicsFlag implements Flag {
    IMAGE_FILE_EXECUTABLE_IMAGE(0x0002),
    IMAGE_FILE_32BIT_MACHINE(0x0100),
    IMAGE_FILE_DLL(0x2000);

    private final int value;

    FileCharacteristicsFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
