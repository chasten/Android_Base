package com.android.base.androidbaseproject.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by turbo on 2017/2/16.
 */

public class LanguageUtil {

    public static final String KEY_LANGUAGE_LOCALE = "key_language_locale";

    public static final int LANGUAGE_LOCALE_CN = 0X11;
    public static final int LANGUAGE_LOCALE_TR = 0X12;
    public static final int LANGUAGE_LOCALE_EN = 0X13;

    public static void changeLanguageType(Context context, Locale localelanguage) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (VersionUtils.isAfter24()) {
            config.setLocale(localelanguage);
        } else {
            config.locale = localelanguage;
            resources.updateConfiguration(config, dm);
        }
    }

    public static void changeLanguageType(Context context, int locale) {
        Locale localelanguage = Locale.SIMPLIFIED_CHINESE;
        switch (locale) {
            case LANGUAGE_LOCALE_CN:
                localelanguage = Locale.SIMPLIFIED_CHINESE;
                break;

            case LANGUAGE_LOCALE_TR:
                localelanguage = Locale.TRADITIONAL_CHINESE;
                break;

            case LANGUAGE_LOCALE_EN:
                localelanguage = Locale.ENGLISH;
                break;
        }
        changeLanguageType(context, localelanguage);
    }

    public static Locale getLanguageType(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        Configuration config = resources.getConfiguration();
        if (VersionUtils.isAfter24()) {
            return config.getLocales().get(0);
        } else {
            return config.locale;
        }
    }
}
