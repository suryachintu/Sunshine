package com.example.android.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.sunshine.app.data.WeatherContract;

/**
 * Created by Surya on 30-11-2016.
 */

public class ForecastAdapter extends CursorAdapter {
    private final int VIEW_TYPE_FUTURE = 1;
    private final int VIEW_TYPE_TODAY = 0;
    private boolean mUseTodayLayout =true;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Prepare the weather high/lows for presentation.
     */

    private String formatHighLows(double high, double low) {
        boolean isMetric = Utility.isMetric(mContext);
        String highLowStr = Utility.formatTemperature(high, isMetric) + "/" + Utility.formatTemperature(low, isMetric);
        return highLowStr;
    }


    private String convertCursorRowToUXFormat(Cursor cursor) {
        // get row indices for our cursor


        String highAndLow = formatHighLows(
                cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP),
                cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP));

        return Utility.formatDate(cursor.getLong(ForecastFragment.COL_WEATHER_DATE)) +
                " - " + cursor.getString(ForecastFragment.COL_WEATHER_DESC) +
                " - " + highAndLow;
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1 ;
        if (viewType == 0)
            layoutId = R.layout.list_item_forcast_today;
        else
            layoutId = R.layout.list_item_forecast;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    public void setUseTodayLayout(boolean useTodayLayout){
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE ;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /*
                This is where we fill-in the views with the contents of the cursor.
             */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // Read weather icon ID from cursor
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);
        // Use placeholder image for now

        int viewType = getItemViewType(cursor.getPosition());

        switch (viewType){
            case VIEW_TYPE_FUTURE:
                viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                break;
            case VIEW_TYPE_TODAY:
                viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                break;
        }

        String dateString = Utility.getFriendlyDayString(mContext,cursor.getLong(ForecastFragment.COL_WEATHER_DATE));

        viewHolder.dateView.setText(dateString);

        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);

        viewHolder.descriptionView.setText(description);

        boolean isMetric = Utility.isMetric(mContext);

        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        viewHolder.highView.setText(Utility.formatTemperature(mContext,high, isMetric));

        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.lowView.setText(Utility.formatTemperature(mContext,low, isMetric));

    }

    public static class ViewHolder{
        final ImageView iconView;
        final TextView dateView;
        final TextView descriptionView;
        final TextView highView;
        final TextView lowView;

        ViewHolder(View view) {

            iconView = (ImageView)view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textView);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textView);
            highView = (TextView) view.findViewById(R.id.list_item_high_textView);
            lowView = (TextView) view.findViewById(R.id.list_item_low_textView);

        }
    }
}
