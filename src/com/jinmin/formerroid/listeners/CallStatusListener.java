package com.jinmin.formerroid.listeners;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.jinmin.formerroid.dao.StoredContactService;
import com.jinmin.formerroid.helpers.SQLHelper;
import com.jinmin.formerroid.model.StoredContact;

public class CallStatusListener extends BroadcastReceiver {

   private AudioManager mAudioManager;

   private SQLHelper sqlHelper;

   public String TAG = getClass().getSimpleName();

   private int StreamType = AudioManager.STREAM_RING;
   static int currVolumnVal = 0;
   static int currRingMode = -1;

   StoredContactService storedContactService;

   @Override
   public void onReceive(Context context, Intent received) {
      String action = received.getAction();
      Bundle bundle = received.getExtras();

      if( storedContactService == null ) {
         storedContactService = new StoredContactService(context);
      }

      List<StoredContact> list = storedContactService.getStoredContactList();

      if( mAudioManager == null ) {
         mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
      }

      if( action.equals("android.intent.action.BOOT_COMPLETED") ) {
         // ComponentName cName = new ComponentName(context.getPackageName(),
         // FormerRoidActivity );
         // ComponentName svcName = context.startService(new
         // Intent().setComponent(cName);
         // if (svc == null) {
         // Log.e(TAG, "Could not start service " + cName.toString());
         // }
      }
      else if( action.equals("android.intent.action.PHONE_STATE") ) {
         String state = bundle.getString(TelephonyManager.EXTRA_STATE);

         if( state.equals(TelephonyManager.EXTRA_STATE_RINGING) ) { // ringing
            if( bundle != null ) {
               String ringingNum = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

               for (StoredContact item : list) {
                  if( ringingNum.equals(item.getTel()) ) {
                     currRingMode = mAudioManager.getRingerMode();
                     mAudioManager.setRingerMode(item.getRing_mode());
                     volumnUP();
                     break;
                  }
               }

            }
         }
         else if( state.equals(TelephonyManager.EXTRA_STATE_IDLE) ) { // ||
            if( currRingMode >= 0 ) {
               mAudioManager.setRingerMode(currRingMode);
            }
         }
      }
   }

   public void volumnUP() {
      if( mAudioManager != null ) {
         currVolumnVal = mAudioManager.getStreamVolume(StreamType);
         int maxVol = mAudioManager.getStreamMaxVolume(StreamType);
         if( currVolumnVal < maxVol ) {
            mAudioManager.setStreamVolume(StreamType, maxVol, AudioManager.FLAG_PLAY_SOUND);
         }
      }
   }
}
