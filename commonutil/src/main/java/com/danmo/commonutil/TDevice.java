package com.danmo.commonutil;


import android.content.Context;
import android.content.pm.PackageManager;

public class TDevice {

    public static int getVersionCode(Context context) {
        try {
            String packageName = context.getPackageName();
            return context
                    .getPackageManager()
                    .getPackageInfo(packageName, 0)
                    .versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            return 0;
        }
    }

}
