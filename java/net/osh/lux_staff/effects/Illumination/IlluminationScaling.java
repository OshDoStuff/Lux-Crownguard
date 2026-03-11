package net.osh.lux_staff.effects.Illumination;

public final class IlluminationScaling {

    private IlluminationScaling() {} // utility class

    /**
     * Calculates Illumination magic damage.
     *
     * This damage is dealt as a separate magic damage instance
     * when Illumination is consumed.
     *
     * @param amplifier Illumination amplifier (0 = level I)
     * @param staffTier Lux staff tier (0 if none / unknown)
     * @return magic damage amount
     */
    public static float illumination(
            int amplifier,
            int staffTier
    ) {
        float damage =
                baseDamage() + amplifierBonus(amplifier);
        return damage;
    }

    /* ----------------------------
       Scaling components
       ---------------------------- */

    private static float baseDamage() {
        return 3.0f;
    }

    private static float amplifierBonus(int amplifier) {
        return 2.0f * amplifier;
    }
}
