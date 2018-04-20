package com.ceotic.clubtrack.control;

public class AppControl {

    public final String TAG = AppControl.class.toString();

    private static final AppControl ourInstance = new AppControl();

    public static AppControl getInstance() {
        return ourInstance;
    }

    public AppControl() {
    }
}
