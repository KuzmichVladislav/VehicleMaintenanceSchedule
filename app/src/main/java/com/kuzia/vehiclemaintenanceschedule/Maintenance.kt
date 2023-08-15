package com.kuzia.vehiclemaintenanceschedule

import java.io.Serializable

// Класс, который представляет собой модель данных о ТО
class Maintenance(
    val id: Int, // Идентификатор ТО в базе данных
//    val icon: Int, // Идентификатор ресурса изображения с иконкой ТО
    val work: String, // Название работы, которая была выполнена при ТО
    var date: String, // Дата последнего ТО в формате "dd.MM.yyyy"
    val mileage: Int, // Пробег автомобиля на момент последнего ТО в километрах
    val nextDate: String, // Расчетная дата следующего ТО в формате "dd.MM.yyyy"
    val nextMileage: Int // Расчетный пробег следующего ТО в километрах
) : Serializable // Имплементируем интерфейс Serializable для передачи объектов этого класса через интенты
