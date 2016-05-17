package io.github.theangrydev.coffee.infrastructure.dotnet;

public enum SubSystem {
    IMAGE_SUBSYSTEM_WINDOWS_CUI(0x3),
    IMAGE_SUBSYSTEM_WINDOWS_GUI(0x2);

    public final int value;

    SubSystem(int value) {
        this.value = value;
    }
}
