/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ilanguage.aublog.service;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

import ca.ilanguage.aublog.R;

/**
 * This is based on an example of service that will update its status bar balloon 
 * every 5 seconds for a minute.
 * 
 * It was modified to use an IntentService so that it could be backgrounded. 
 * 
 * Usage:
 *  Intent intent = new Intent(this, NotifyingTranscriptionService.class)
 *  intent.putExtra(String filepathandnametoupload);
 *  intent.startservice()
 * 
 */
public class NotifyingTranscriptionService extends IntentService {
    
    public NotifyingTranscriptionService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	// Use a layout id for a unique identifier
    private static int MOOD_NOTIFICATIONS = R.layout.status_bar_notifications;

    

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		
		/*
		 * upload logic goes here
		 * 
		 * getStringExtra for the filename
		 * 
		 */
		
        
		
	
        for (int i = 0; i < 4; ++i) {
            showNotification(R.drawable.stat_happy,  R.string.status_bar_notifications_sending_message);
            try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
        }
        
		/*
		 * Service will auto shut down once onHandleIntent is complete. 
		 */
		
	}

	@Override
    public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    }

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.
        mNM.cancel(MOOD_NOTIFICATIONS);
       
    }

    private void showNotification(int moodId, int textId) {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(textId);

        // Set the icon, scrolling text and timestamp.
        // Note that in this example, we pass null for tickerText.  We update the icon enough that
        // it is distracting to show the ticker text every time it changes.  We strongly suggest
        // that you do this as well.  (Think of of the "New hardware found" or "Network connection
        // changed" messages that always pop up)
        Notification notification = new Notification(moodId, null, System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NotifyingController.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, "notifications_mood_title",
                       text, contentIntent);

        // Send the notification.
        // We use a layout id because it is a unique number.  We use it later to cancel.
        mNM.notify(MOOD_NOTIFICATIONS, notification);
    }


    private NotificationManager mNM;
}
