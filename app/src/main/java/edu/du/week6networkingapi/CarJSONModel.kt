package edu.du.week6networkingapi

data class CarJSONModel(
    var cars: List<Cars>?
)

data class Cars(
    var id: Int?,
    var make: String?,
    var model: String?,
    var trim: String?,
    var year: Int?,
    var transmission: Transmission?,
    var wheels: Wheels?,
    var tires: Tires?
)

data class Transmission(
    var gears: Int?,
    var drivetrain: String?
)

data class Wheels(
    var oem: Boolean?
)

data class Tires(
    var brand: String?,
    var size: List<String>?
)