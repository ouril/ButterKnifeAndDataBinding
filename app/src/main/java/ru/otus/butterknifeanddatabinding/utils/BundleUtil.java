package ru.otus.butterknifeanddatabinding.utils;

import android.os.Bundle;
import android.os.Parcelable;

public class BundleUtil {
    private static final String person = "Person";

    public static Bundle fromParcelable(Parcelable model){
        Bundle bundle = new Bundle();
        bundle.putParcelable(person, model);
        return bundle;
    }

    public static Parcelable getParcelable(Bundle bundle){
        return bundle.getParcelable(person);
    }



}
