package com.mariomanhique.quintadoeden.sync

data class NoteState(
    val isEnteringToken: Boolean = true,
    val remoteToken: String ="",
    val messageText: String =""
)
