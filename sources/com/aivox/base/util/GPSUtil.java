package com.aivox.base.util;

/* JADX INFO: loaded from: classes.dex */
public class GPSUtil {

    /* JADX INFO: renamed from: a */
    public static double f192a = 6378245.0d;

    /* JADX INFO: renamed from: ee */
    public static double f193ee = 0.006693421622965943d;

    /* JADX INFO: renamed from: pi */
    public static double f194pi = 3.141592653589793d;
    public static double x_pi = 52.35987755982988d;

    public static boolean outOfChina(double d, double d2) {
        return d2 < 72.004d || d2 > 137.8347d || d < 0.8293d || d > 55.8271d;
    }

    public static double transformLat(double d, double d2) {
        double d3 = d * 2.0d;
        return (-100.0d) + d3 + (d2 * 3.0d) + (d2 * 0.2d * d2) + (0.1d * d * d2) + (Math.sqrt(Math.abs(d)) * 0.2d) + ((((Math.sin((d * 6.0d) * f194pi) * 20.0d) + (Math.sin(d3 * f194pi) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(f194pi * d2) * 20.0d) + (Math.sin((d2 / 3.0d) * f194pi) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d2 / 12.0d) * f194pi) * 160.0d) + (Math.sin((d2 * f194pi) / 30.0d) * 320.0d)) * 2.0d) / 3.0d);
    }

    public static double transformLon(double d, double d2) {
        double d3 = d * 0.1d;
        return d + 300.0d + (d2 * 2.0d) + (d3 * d) + (d3 * d2) + (Math.sqrt(Math.abs(d)) * 0.1d) + ((((Math.sin((6.0d * d) * f194pi) * 20.0d) + (Math.sin((d * 2.0d) * f194pi) * 20.0d)) * 2.0d) / 3.0d) + ((((Math.sin(f194pi * d) * 20.0d) + (Math.sin((d / 3.0d) * f194pi) * 40.0d)) * 2.0d) / 3.0d) + ((((Math.sin((d / 12.0d) * f194pi) * 150.0d) + (Math.sin((d / 30.0d) * f194pi) * 300.0d)) * 2.0d) / 3.0d);
    }

    public static double[] transform(double d, double d2) {
        if (outOfChina(d, d2)) {
            return new double[]{d, d2};
        }
        double d3 = d2 - 105.0d;
        double d4 = d - 35.0d;
        double dTransformLat = transformLat(d3, d4);
        double dTransformLon = transformLon(d3, d4);
        double d5 = (d / 180.0d) * f194pi;
        double dSin = Math.sin(d5);
        double d6 = 1.0d - ((f193ee * dSin) * dSin);
        double dSqrt = Math.sqrt(d6);
        double d7 = f192a;
        return new double[]{d + ((dTransformLat * 180.0d) / ((((1.0d - f193ee) * d7) / (d6 * dSqrt)) * f194pi)), d2 + ((dTransformLon * 180.0d) / (((d7 / dSqrt) * Math.cos(d5)) * f194pi))};
    }

    public static double[] gps84_To_Gcj02(double d, double d2) {
        if (outOfChina(d, d2)) {
            return new double[]{d, d2};
        }
        double d3 = d2 - 105.0d;
        double d4 = d - 35.0d;
        double dTransformLat = transformLat(d3, d4);
        double dTransformLon = transformLon(d3, d4);
        double d5 = (d / 180.0d) * f194pi;
        double dSin = Math.sin(d5);
        double d6 = 1.0d - ((f193ee * dSin) * dSin);
        double dSqrt = Math.sqrt(d6);
        double d7 = f192a;
        return new double[]{d + ((dTransformLat * 180.0d) / ((((1.0d - f193ee) * d7) / (d6 * dSqrt)) * f194pi)), d2 + ((dTransformLon * 180.0d) / (((d7 / dSqrt) * Math.cos(d5)) * f194pi))};
    }

    public static double[] gcj02_To_Gps84(double d, double d2) {
        double[] dArrTransform = transform(d, d2);
        return new double[]{(d * 2.0d) - dArrTransform[0], (d2 * 2.0d) - dArrTransform[1]};
    }

    public static double[] gcj02_To_Bd09(double d, double d2) {
        double dSqrt = Math.sqrt((d2 * d2) + (d * d)) + (Math.sin(x_pi * d) * 2.0E-5d);
        double dAtan2 = Math.atan2(d, d2) + (Math.cos(d2 * x_pi) * 3.0E-6d);
        return new double[]{(dSqrt * Math.sin(dAtan2)) + 0.006d, (Math.cos(dAtan2) * dSqrt) + 0.0065d};
    }

    public static double[] bd09_To_Gcj02(double d, double d2) {
        double d3 = d2 - 0.0065d;
        double d4 = d - 0.006d;
        double dSqrt = Math.sqrt((d3 * d3) + (d4 * d4)) - (Math.sin(x_pi * d4) * 2.0E-5d);
        double dAtan2 = Math.atan2(d4, d3) - (Math.cos(d3 * x_pi) * 3.0E-6d);
        return new double[]{dSqrt * Math.sin(dAtan2), Math.cos(dAtan2) * dSqrt};
    }

    public static double[] gps84_To_bd09(double d, double d2) {
        double[] dArrGps84_To_Gcj02 = gps84_To_Gcj02(d, d2);
        return gcj02_To_Bd09(dArrGps84_To_Gcj02[0], dArrGps84_To_Gcj02[1]);
    }

    public static double[] bd09_To_gps84(double d, double d2) {
        double[] dArrBd09_To_Gcj02 = bd09_To_Gcj02(d, d2);
        double[] dArrGcj02_To_Gps84 = gcj02_To_Gps84(dArrBd09_To_Gcj02[0], dArrBd09_To_Gcj02[1]);
        dArrGcj02_To_Gps84[0] = retain6(dArrGcj02_To_Gps84[0]);
        dArrGcj02_To_Gps84[1] = retain6(dArrGcj02_To_Gps84[1]);
        return dArrGcj02_To_Gps84;
    }

    private static double retain6(double d) {
        return Double.valueOf(String.format("%.6f", Double.valueOf(d))).doubleValue();
    }
}
