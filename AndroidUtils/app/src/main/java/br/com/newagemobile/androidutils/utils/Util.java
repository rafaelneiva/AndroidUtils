package br.com.newagemobile.androidutils.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EdgeEffect;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by rafaelneiva on 3/23/15.
 */
public class Util {

    // ********** JSON Utils ********** //

    /**
     * @param ctx      Activity/Context
     * @param jsonFile The file in assets folder
     * @return Return a JSON String
     */
    public static String loadJSONFromAsset(Context ctx, String jsonFile) {
        String json;
        try {
            InputStream is = ctx.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    // ********** JSON Utils ********** //


    // ********** Format Utils ********** //

    /**
     * @param day    Day of month
     * @param month  Month of the year start with 0
     * @param year   Year number
     * @param format Format to be used
     * @return Return a String formatted with 'format'
     */
    public static String formatDate(int day, int month, int year, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        return sdf.format(date);
    }

    /**
     * @param value    The value that will be formatted
     * @param currency To be used before value
     * @return Return a String formatted with 'currency'
     */
    public static String formatValue(Double value, String currency) {
        if (value == null)
            return "";
        DecimalFormat format = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        format.setMinimumFractionDigits(2);
        format.setParseBigDecimal(true);

        return currency + format.format(value);
    }

    /**
     * @param phoneNumber The number to be formatted with ( and -
     * @return The number formatted with nine or eight numbers
     */
    public static String formatPhone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        if (phoneNumber == null)
            return "";
        String phoneF = "(";
        if (phoneNumber.length() == 10) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (i == 1) {
                    phoneF += phoneNumber.charAt(i) + ")";
                } else if (i == 5) {
                    phoneF += phoneNumber.charAt(i) + "-";
                } else {
                    phoneF += phoneNumber.charAt(i);
                }
            }
        } else if (phoneNumber.length() == 11) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (i == 1) {
                    phoneF += phoneNumber.charAt(i) + ")";
                } else if (i == 6) {
                    phoneF += phoneNumber.charAt(i) + "-";
                } else {
                    phoneF += phoneNumber.charAt(i);
                }
            }
        }

        return phoneF;
    }

    // ********** Format Utils ********** //


    // ********** Misc Utils ********** //

    /**
     * @param context Activity/Context
     * @param view    Field that holds the keyboard focus
     */
    public static void closeKeyboard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception ex) {
            Log.e("AndroidUtils", "Error occurred trying to hide the keyboard. Exception: " + ex);
        }
    }

    /**
     * @param context Activity/Context
     * @param view    Field that requests focus
     */
    public static void showKeyboard(Context context, View view) {
        try {
            view.requestFocus();
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception ex) {
            Log.e("AndroidUtils", "Error occurred trying to show the keyboard. Exception: " + ex);
        }
    }

    /**
     * Default SHORT duration
     *
     * @param ctx Context/Activity
     * @param msg Message to show
     */
    public static void quickToast(Context ctx, String msg) {
        quickToast(ctx, msg, false);
    }

    /**
     * @param ctx    Context/Activity
     * @param msg    Message to show
     * @param isLong If true, toast duration is LONG
     */
    public static void quickToast(Context ctx, String msg, boolean isLong) {
        Toast.makeText(ctx, msg, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * @param colorId  The color id used to set notification bar color
     * @param name     The name that will appear on recent apps
     * @param activity The activity that will be applied
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tintNotificationBar(int colorId, String name, Activity activity) {
        activity.getWindow().setStatusBarColor(activity.getResources().getColor(colorId));
    }

    /**
     * @param colorId  The color id used to set task description color
     * @param name     The name that will appear on recent apps
     * @param activity The activity that will be applied
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void tintTaskDescription(int colorId, String name, Activity activity) {
        ActivityManager.TaskDescription tDesc =
                new ActivityManager.TaskDescription(name, null, activity.getResources().getColor(colorId));
        activity.setTaskDescription(tDesc);
    }

    /**
     * @param listView ListView to apply edge effect color
     * @param color    Color of the edge effect
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setListEdgeGlowColor(AbsListView listView, int color) {

        final Class<?> CLASS_LIST_VIEW = AbsListView.class;
        final Field LIST_VIEW_FIELD_EDGE_GLOW_TOP;
        final Field LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM;

        Field edgeGlowTop = null, edgeGlowBottom = null;

        for (Field f : CLASS_LIST_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mEdgeGlowTop":
                    f.setAccessible(true);
                    edgeGlowTop = f;
                    break;
                case "mEdgeGlowBottom":
                    f.setAccessible(true);
                    edgeGlowBottom = f;
                    break;
            }
        }

        LIST_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
        LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                EdgeEffect ee;
                assert LIST_VIEW_FIELD_EDGE_GLOW_TOP != null;
                ee = (EdgeEffect) LIST_VIEW_FIELD_EDGE_GLOW_TOP.get(listView);
                ee.setColor(color);
                assert LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM != null;
                ee = (EdgeEffect) LIST_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(listView);
                ee.setColor(color);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * @param scrollView ScrollView to apply edge effect color
     * @param color      Color of the edge effect
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setScrollEdgeGlowColor(ScrollView scrollView, int color) {

        final Class<?> CLASS_SCROLL_VIEW = ScrollView.class;
        final Field SCROLL_VIEW_FIELD_EDGE_GLOW_TOP;
        final Field SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM;

        Field edgeGlowTop = null, edgeGlowBottom = null;

        for (Field f : CLASS_SCROLL_VIEW.getDeclaredFields()) {
            switch (f.getName()) {
                case "mEdgeGlowTop":
                    f.setAccessible(true);
                    edgeGlowTop = f;
                    break;
                case "mEdgeGlowBottom":
                    f.setAccessible(true);
                    edgeGlowBottom = f;
                    break;
            }
        }

        SCROLL_VIEW_FIELD_EDGE_GLOW_TOP = edgeGlowTop;
        SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM = edgeGlowBottom;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                EdgeEffect ee;
                assert SCROLL_VIEW_FIELD_EDGE_GLOW_TOP != null;
                ee = (EdgeEffect) SCROLL_VIEW_FIELD_EDGE_GLOW_TOP.get(scrollView);
                ee.setColor(color);
                assert SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM != null;
                ee = (EdgeEffect) SCROLL_VIEW_FIELD_EDGE_GLOW_BOTTOM.get(scrollView);
                ee.setColor(color);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // ********** Misc Utils ********** //

}
