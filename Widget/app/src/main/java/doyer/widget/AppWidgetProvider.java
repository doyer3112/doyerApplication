package doyer.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by jie.du on 16/11/24.
 */
public class AppWidgetProvider extends android.appwidget.AppWidgetProvider {

    public static final String BROADCAST_STR = "doyer.widget";
    public static final String MY_WIDGET_PROVIDER = "WidgetProvider";

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        //创建一个Intent对象
        Intent intent = new Intent();
        intent.setClass(context, AppWidgetProvider.class);
        intent.setAction(BROADCAST_STR);

        //设置pendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        //Retrieve a PendingIntent that will perform a broadcast
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        //为button按钮绑定一个事件便于发送广播
        remoteViews.setOnClickPendingIntent(R.id.widget_btn, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}
