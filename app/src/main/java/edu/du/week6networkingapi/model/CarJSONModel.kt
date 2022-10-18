package edu.du.week6networkingapi.model

data class CarJSONModel(
    var id: Int?,
    var make: String?,
    var model: String?,
    var trim: String?,
    var year: Int?,
    var transmission: Transmission?,
    var wheels: Wheels?,
    var tires: Tires?
)