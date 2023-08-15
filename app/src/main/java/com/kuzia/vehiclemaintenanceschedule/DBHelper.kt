package com.kuzia.vehiclemaintenanceschedule

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Класс DBHelper, который наследует от класса SQLiteOpenHelper
class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Константы для имени и версии базы данных
    companion object {
        const val DATABASE_NAME = "cars.db"
        const val DATABASE_VERSION = 1
    }

    // Константы для имени и столбцов таблицы автомобилей
    object CarTable {
        const val TABLE_NAME = "cars"
        const val COLUMN_ID = "_id"
        const val COLUMN_BRAND = "brand"
        const val COLUMN_MODEL = "model"
        const val COLUMN_MILEAGE = "mileage"
    }

    // Константы для имени и столбцов таблицы технического обслуживания
    object MaintenanceTable {
        const val TABLE_NAME = "maintenance"
        const val COLUMN_ID = "_id"
        const val COLUMN_CAR_ID = "car_id"
//        const val COLUMN_ICON = "icon"
        const val COLUMN_WORK = "work"
        const val COLUMN_DATE = "date"
        const val COLUMN_MILEAGE = "mileage"
        const val COLUMN_NEXT_DATE = "next_date"
        const val COLUMN_NEXT_MILEAGE = "next_mileage"
    }

    // SQL-запрос для создания таблицы автомобилей
    private val SQL_CREATE_CAR_TABLE = """
        CREATE TABLE ${CarTable.TABLE_NAME} (
            ${CarTable.COLUMN_ID} INTEGER PRIMARY KEY,
            ${CarTable.COLUMN_BRAND} TEXT NOT NULL,
            ${CarTable.COLUMN_MODEL} TEXT NOT NULL,
            ${CarTable.COLUMN_MILEAGE} INTEGER NOT NULL
        )
    """.trimIndent()

    // SQL-запрос для создания таблицы технического обслуживания
    private val SQL_CREATE_MAINTENANCE_TABLE = """
        CREATE TABLE ${MaintenanceTable.TABLE_NAME} (
            ${MaintenanceTable.COLUMN_ID} INTEGER PRIMARY KEY,
            ${MaintenanceTable.COLUMN_CAR_ID} INTEGER NOT NULL,
            ${MaintenanceTable.COLUMN_WORK} TEXT NOT NULL,
            ${MaintenanceTable.COLUMN_DATE} TEXT NOT NULL,
            ${MaintenanceTable.COLUMN_MILEAGE} INTEGER NOT NULL,
            ${MaintenanceTable.COLUMN_NEXT_DATE} TEXT NOT NULL,
            ${MaintenanceTable.COLUMN_NEXT_MILEAGE} INTEGER NOT NULL,
            FOREIGN KEY (${MaintenanceTable.COLUMN_CAR_ID}) REFERENCES ${CarTable.TABLE_NAME} (${CarTable.COLUMN_ID})
        )
    """.trimIndent()

    // SQL-запрос для удаления таблицы автомобилей
    private val SQL_DROP_CAR_TABLE = "DROP TABLE IF EXISTS ${CarTable.TABLE_NAME}"

    // SQL-запрос для удаления таблицы технического обслуживания
    private val SQL_DROP_MAINTENANCE_TABLE = "DROP TABLE IF EXISTS ${MaintenanceTable.TABLE_NAME}"

    // Метод, который вызывается при создании базы данных
    override fun onCreate(db: SQLiteDatabase) {
        // Выполняем SQL-запросы для создания таблиц автомобилей и технического обслуживания
        db.execSQL(SQL_CREATE_CAR_TABLE)
        db.execSQL(SQL_CREATE_MAINTENANCE_TABLE)
    }

    // Метод, который вызывается при обновлении версии базы данных
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Выполняем SQL-запросы для удаления таблиц автомобилей и технического обслуживания
        db.execSQL(SQL_DROP_CAR_TABLE)
        db.execSQL(SQL_DROP_MAINTENANCE_TABLE)
        // Вызываем метод onCreate для создания новых таблиц автомобилей и технического обслуживания
        onCreate(db)
    }

    // Метод для добавления нового автомобиля в базу данных
    fun insertCar(brand: String, model: String, mileage: Int): Long {
        // Получаем объект SQLiteDatabase для записи данных
        val db = this.writableDatabase
        // Создаем объект ContentValues для хранения значений столбцов
        val values = ContentValues()
        // Добавляем значения в объект ContentValues
        values.put(CarTable.COLUMN_BRAND, brand)
        values.put(CarTable.COLUMN_MODEL, model)
        values.put(CarTable.COLUMN_MILEAGE, mileage)
        // Вставляем новую строку в таблицу автомобилей и возвращаем ее идентификатор
        return db.insert(CarTable.TABLE_NAME, null, values)
    }

    // Метод для получения всех автомобилей из базы данных
    fun getAllCars(): Cursor {
        // Получаем объект SQLiteDatabase для чтения данных
        val db = this.readableDatabase
        // Выполняем SQL-запрос для выборки всех данных из таблицы автомобилей и возвращаем объект Cursor
        return db.query(CarTable.TABLE_NAME, null, null, null, null, null, null)
    }

    // Метод для обновления пробега автомобиля в базе данных по идентификатору
    fun updateCarMileage(id: Long, mileage: Int): Int {
        // Получаем объект SQLiteDatabase для записи данных
        val db = this.writableDatabase
        // Создаем объект ContentValues для хранения нового значения столбца пробега
        val values = ContentValues()
        // Добавляем значение в объект ContentValues
        values.put(CarTable.COLUMN_MILEAGE, mileage)
        // Обновляем строку в таблице автомобилей по идентификатору и возвращаем количество обновленных строк
        return db.update(
            CarTable.TABLE_NAME,
            values,
            "${CarTable.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    // Метод для удаления автомобиля из базы данных по идентификатору
    fun deleteCar(id: Long): Int {
        // Получаем объект SQLiteDatabase для записи данных
        val db = this.writableDatabase
        // Удаляем строку из таблицы автомобилей по идентификатору и возвращаем количество удаленных строк
        return db.delete(CarTable.TABLE_NAME, "${CarTable.COLUMN_ID} = ?", arrayOf(id.toString()))
    }

    // Метод для добавления нового ТО в базу данных
    fun insertMaintenance(
        carId: Long,
//        icon: Int,
        work: String,
        date: String,
        mileage: Int,
        nextDate: String,
        nextMileage: Int
    ): Long {
        // Получаем объект SQLiteDatabase для записи данных
        val db = this.writableDatabase
        // Создаем объект ContentValues для хранения значений столбцов
        val values = ContentValues()
        // Добавляем значения в объект ContentValues
        values.put(MaintenanceTable.COLUMN_CAR_ID, carId)
//        values.put(MaintenanceTable.COLUMN_ICON, icon)
        values.put(MaintenanceTable.COLUMN_WORK, work)
        values.put(MaintenanceTable.COLUMN_DATE, date)
        values.put(MaintenanceTable.COLUMN_MILEAGE, mileage)
        values.put(MaintenanceTable.COLUMN_NEXT_DATE, nextDate)
        values.put(MaintenanceTable.COLUMN_NEXT_MILEAGE, nextMileage)
        // Вставляем новую строку в таблицу технического обслуживания и возвращаем ее идентификатор
        return db.insert(MaintenanceTable.TABLE_NAME, null, values)
    }

    // Метод для получения всех ТО по идентификатору автомобиля из базы данных
    fun getMaintenanceByCarId(carId: Int): Cursor {
        // Получаем объект SQLiteDatabase для чтения данных
        val db = this.readableDatabase
        // Выполняем SQL-запрос для выборки всех данных из таблицы технического обслуживания по идентификатору автомобиля и возвращаем объект Cursor
        return db.query(
            MaintenanceTable.TABLE_NAME,
            null,
            "${MaintenanceTable.COLUMN_CAR_ID} = ?",
            arrayOf(carId.toString()),
            null,
            null,
            null
        )
    }

    // Метод для обновления ТО в базе данных по идентификатору
    fun updateMaintenance(id: Int, column: String, value: String): Int {
        // Получаем объект SQLiteDatabase для записи данных
        val db = this.writableDatabase
        // Создаем объект ContentValues для хранения нового значения столбца
        val values = ContentValues()
        // Добавляем значение в объект ContentValues
        values.put(column, value)
        // Обновляем строку в таблице технического обслуживания по идентификатору и возвращаем количество обновленных строк
        return db.update(
            MaintenanceTable.TABLE_NAME,
            values,
            "${MaintenanceTable.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    // Метод для удаления ТО из базы данных по идентификатору
    fun deleteMaintenance(id: Long): Int {
        // Получаем объект SQLiteDatabase для записи данных
        val db = this.writableDatabase
        // Удаляем строку из таблицы технического обслуживания по идентификатору и возвращаем количество удаленных строк
        return db.delete(
            MaintenanceTable.TABLE_NAME,
            "${MaintenanceTable.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }
}

