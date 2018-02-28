package com.google.android.gms.location.sample.basiclocationsample;

/**
 * Created by EFE on 3.01.2018.
 */

public class durak {
    private double x;
    private double y;

    private String otobus;
    private String busstop;
    private String deststop;

    public durak (double x, double y, String otobus , String busstop, String deststop) {
        this.x = x;
        this.y = y;
        this.otobus = otobus;
        this.busstop = busstop;
        this.deststop = deststop;
    }


    public String getBusstop() {
        return busstop;
    }

    public String getDeststop() {
        return deststop;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getOtobus() {
        return otobus;
    }
}
