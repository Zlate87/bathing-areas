package com.example.zlatko.beaches.base.client.brandenburg.model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "kml", strict = false)
data class Kml (
    @field:Element(name = "Document", required = false)
    @param:Element(name = "Document")
    var document: Document? = null
)

data class Document(
    @field:Element(name = "name", required = false)
    @param:Element(name = "name")
    var name: String? = "N/A",

    @field:ElementList(inline = true, entry = "Placemark", required = false, )
    var placemark: List<Placemark?>? = null
)

data class Placemark(
    @field:Element(name = "name", required = false)
    @param:Element(name = "name")
    var name: String = "N/A",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description")
    var description: String = "N/A",

    @field:Element(name = "styleUrl", required = false)
    @param:Element(name = "styleUrl")
    var styleUrl: String = "N/A",

    @field:Element(name = "Point", required = false)
    @param:Element(name = "Point")
    var point: Point? = null,

    @field:Element(name = "ExtendedData", required = false)
    @param:Element(name = "ExtendedData")
    var extendedData: ExtendedData? = null
)

data class ExtendedData(
    @field:ElementList(inline = true, entry = "Data", required = false, )
    var data: List<Data?>? = null
)

data class Point(
    @field:Element(name = "coordinates", required = false)
    @param:Element(name = "coordinates")
    var coordinates: String = "N/A"
)


data class Data(
    @field:Attribute(name = "name", required = false)
    @param:Attribute(name = "name")
    var name: String = "N/A",
    @field:Element(name = "value", required = false)
    @param:Element(name = "value")
    var value: String = "N/A"
)