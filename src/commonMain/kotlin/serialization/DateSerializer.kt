package io.github.mrasterisco.Date.serialization

import io.github.mrasterisco.Date.Date
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DateSerializer: KSerializer<Date> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("date", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) =
        ISO8601DateFormat.parse(decoder.decodeString()) ?: Date()

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(ISO8601DateFormat.format(value))
    }

}