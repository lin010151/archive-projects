package org.androidpn.client;

import java.util.List;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceUtil {
	/**
	 * �ж�ĳ��service�Ƿ�����
	 * 
	 * @Description:
	 * @param mContext
	 * @param className
	 * @return
	 * @return ������ڷ���service���򷵻�null
	 */
	public static ActivityManager.RunningServiceInfo isServiceRunning(
			Context mContext, String className) {

		ActivityManager activityManager = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(30);

		if (!(serviceList.size() > 0)) {
			return null;
		}
		ActivityManager.RunningServiceInfo runningService = null;
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				runningService = serviceList.get(i);
				break;
			}
		}
		return runningService;
	}
}
