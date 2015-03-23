package br.com.newagemobile.androidutils.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
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

    // return a String JSON from a .json file on assets folder
    public static String loadJSONFromAsset(Context ctx, String jsonFile) {
        String json;
        try {
            InputStream is = ctx.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
//            is.read(buffer);
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

    // return a String formatted with 'format'
    public static String formatDate(int day, int month, int year, String format) {

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        return sdf.format(date);
    }

    // return a String formatted with 'currency'
    public static String formatValue(Double value, String currency) {
        if (value == null)
            return "";
        DecimalFormat format = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        format.setMinimumFractionDigits(2);
        format.setParseBigDecimal(true);

        return currency + format.format(value);
    }

    // ********** Format Utils ********** //

}
