package net.osh.lux_staff.item.lux_staff.tiers;

public enum StaffTier {
    Elementalist    (5f, 4, 5, 2, 80, 110, 8 , 6 , 30, 10),
    Cosmic          (4f, 3, 4, 1, 75, 100, 9 , 7 , 30, 8),
    Blossom         (3f, 2, 3, 1, 70, 90 , 10, 8 , 40, 8),
    Air             (2f, 1, 2, 0, 65, 80 , 11, 9 , 50, 6),
    Classic         (1f, 0, 1, 0, 60, 70 , 12, 10, 60, 6);

    private final float damageBonus;
    private final int effectAmplifier;
    private final int effectAddition;
    private final int slowBonus;
    private final int WmanaCost;
    private final int EmanaCost;
    private final int Wcooldown;
    private final int Ecooldown;
    private final int Rcooldown;
    private final float ultDamage;

    StaffTier(float damageBonus, int effectAmplification, int effectAddition, int slowBonus,
              int WmanaCost, int EmanaCost, int Wcooldown, int Ecooldown, int Rcooldown, float ultDamage) {
        this.damageBonus = damageBonus;
        this.effectAmplifier = effectAmplification;
        this.effectAddition = effectAddition;
        this.slowBonus = slowBonus;
        this.WmanaCost = WmanaCost;
        this.EmanaCost = EmanaCost;
        this.Wcooldown = Wcooldown;
        this.Ecooldown = Ecooldown;
        this.Rcooldown = Rcooldown;
        this.ultDamage = ultDamage;
    }

    public float DamageBonus() {
        return damageBonus;
    }
    public int effectAmplifier() {
        return effectAmplifier;
    }
    public int effectAddition() {
        return effectAddition;
    }
    public int slowBonus() {
        return  slowBonus;
    }
    public int WmanaCost() {
        return WmanaCost;
    }
    public int EmanaCost() {
        return EmanaCost;
    }
    public int Wcooldown() {
        return Wcooldown;
    }
    public int Ecooldown() {
        return Ecooldown;
    }
    public int Rcooldown() {
        return Rcooldown;
    }
    public float UltDamage() {
        return ultDamage;
    }
}
