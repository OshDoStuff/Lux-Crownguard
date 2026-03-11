package net.osh.lux_staff.effects.Illumination;

public final class IlluminationContext {

    private static boolean proccing = false;

    public static boolean isActive() {
        return proccing;
    }

    public static void enter() {
        proccing = true;
    }

    public static void exit() {
        proccing = false;
    }
}
