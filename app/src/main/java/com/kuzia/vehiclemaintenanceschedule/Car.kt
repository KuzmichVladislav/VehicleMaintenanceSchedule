package com.kuzia.vehiclemaintenanceschedule

import java.io.Serializable

// Класс, который представляет собой модель данных об автомобиле
class Car(
    var id: Int,
    var brand: String, var model: String, var mileage: Int, var date: String, var work: String
) : Serializable