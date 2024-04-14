package com.mariomanhique.quintadoeden.model

data class Room(
    val roomState: String = ROOMSTATE.CLEAN.displayName,
    val isRoomAvailable: Boolean = false,
    val roomNr: Int = 0,
    val roomType: String = "",
    val observation: String = ""//I need to filter this by date when I show
)


val roomsList = listOf(
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.displayName,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
    Room(
        roomType = ROOMTYPE.DELUXE_VARANDA.name,
        roomNr = 201,
        roomState = ROOMSTATE.CLEAN.name
    ),
)
