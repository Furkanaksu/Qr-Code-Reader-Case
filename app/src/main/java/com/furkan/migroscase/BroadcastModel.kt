package com.furkan.migroscase

enum class BroadcastModel(val broadcastCode: String) {
    LOADING("finish_loading"),
    PIP_MODE("finish_activity"),
    MINI_PLAYER("miniPlayerStart"),
    SETTINGS("goSettings"),
    NOTIFICATION_PLAYER("actionname"),
}