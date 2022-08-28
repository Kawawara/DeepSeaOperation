package fr.theo.berton.deepseaoperation.network.payload

import fr.theo.berton.deepseaoperation.model.Player
import fr.theo.berton.deepseaoperation.network.payload.Payload

data class GamePlayerPayload(
    val type: String,
    val data: List<Player>
) : Payload(){
    fun jsonEncodeToString(): String {
        return super.jsonEncodeToString(this)
    }
}

data class GameStartPayload(
    val type: String = PayloadType.start.toString(),
) : Payload() {

    fun jsonEncodeToString(): String {
        return super.jsonEncodeToString(this)
    }
}

data class ShipIntegrityData(
    val integrity: Int,
)

data class ShipIntegrityPayload(
    val type: String,
    val data : ShipIntegrityData
): Payload(){
    fun jsonEncodeToString(): String {
        return super.jsonEncodeToString(this)
    }
}

data class DestroyedShipData(
    val turns : Int
)

data class DestroyedShipDataPayload(
    val type: String,
    val data : DestroyedShipData
): Payload(){
    fun jsonEncodeToString(): String {
        return super.jsonEncodeToString(this)
    }
}