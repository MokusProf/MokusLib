package net.mokus.mokuslib.api;

import net.mokus.mokuslib.cooldown.CooldownManager;

public class MokusLibAPI {
    private static CooldownManager INSTANCE;

    private MokusLibAPI() {}

    public static void register(CooldownManager impl) {
        if (INSTANCE != null) {
            throw new IllegalStateException("MokusLib is already registered!");
        }
        INSTANCE = impl;
    }

    public static CooldownManager get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("MokusLib has not been initialized!");
        }
        return INSTANCE;
    }
}
