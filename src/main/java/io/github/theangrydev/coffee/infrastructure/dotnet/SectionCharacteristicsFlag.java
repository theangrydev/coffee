package io.github.theangrydev.coffee.infrastructure.dotnet;

import io.github.theangrydev.coffee.infrastructure.Flag;

public enum SectionCharacteristicsFlag implements Flag {
    IMAGE_SCN_CNT_CODE(0x00000020),
    IMAGE_SCN_CNT_INITIALIZED_DATA(0x00000040),
    IMAGE_SCN_CNT_UNINITIALIZED_DATA(0x00000080),
    IMAGE_SCN_MEM_DISCARDABLE(0x02000000),
    IMAGE_SCN_MEM_EXECUTE(0x20000000),
    IMAGE_SCN_MEM_READ(0x40000000),
    IMAGE_SCN_MEM_WRITE(0x80000000);

    private final int value;

    SectionCharacteristicsFlag(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
