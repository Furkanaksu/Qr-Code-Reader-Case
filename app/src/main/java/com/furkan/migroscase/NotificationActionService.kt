package com.furkan.migroscase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.sendBroadcast(
            Intent("TRACKS_TRACKS")
                .putExtra(BroadcastModel.NOTIFICATION_PLAYER.broadcastCode, intent.action)
        )
    }
}