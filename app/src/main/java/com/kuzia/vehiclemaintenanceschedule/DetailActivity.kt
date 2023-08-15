package com.kuzia.vehiclemaintenanceschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.view.View
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    // Переменные для хранения данных об автомобиле, которые переданы из главной активности через интент
    private lateinit var car: Car

    // Переменные для хранения видов по идентификаторам
    private lateinit var brandTextView: TextView
    private lateinit var modelTextView: TextView
    private lateinit var mileageTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var detailLayout: LinearLayout
    private lateinit var workTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var maintenanceMileageTextView: TextView
    private lateinit var nextTextView: TextView
    private lateinit var updateButton: Button

    // Переменные для хранения списка ТО и адаптера для связывания данных с видами в RecyclerView
    private val maintenanceList = ArrayList<Maintenance>()
    private lateinit var maintenanceAdapter: MaintenanceAdapter

    // Переменная для хранения позиции выбранного элемента списка ТО
    private var selectedPosition = -1

    // Переменная для хранения объекта для работы с базой данных
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Получаем данные об автомобиле из интента
//        car = intent.getSerializableExtra("car") as Car
        car = Car(1, "Renault", "Duster", 10000, "01.01.2023", "Замена масла");
        // Находим виды по идентификаторам
        brandTextView = findViewById(R.id.brandTextView)
        modelTextView = findViewById(R.id.modelTextView)
        mileageTextView = findViewById(R.id.mileageTextView)
        recyclerView = findViewById(R.id.recyclerView)
        detailLayout = findViewById(R.id.detailLayout)
        workTextView = findViewById(R.id.workTextView)
        dateTextView = findViewById(R.id.dateTextView)
        maintenanceMileageTextView = findViewById(R.id.maintenanceMileageTextView)
        nextTextView = findViewById(R.id.nextTextView)
        updateButton = findViewById(R.id.updateButton)

        // Устанавливаем текст для видов с информацией об автомобиле
        brandTextView.text = car.brand
        modelTextView.text = car.model
        mileageTextView.text = "${car.mileage} км"

        // Создаем объект для работы с базой данных
        dbHelper = DBHelper(this)

        dbHelper.insertMaintenance(1, "Work", "01.01.2023", 230000, "01.01.2024", 240000)
        dbHelper.insertMaintenance(1, "Work2", "01.01.2023", 230000, "01.01.2024", 240000)
        dbHelper.insertMaintenance(1, "Work3", "01.01.2023", 230000, "01.01.2024", 240000)

        // Заполняем список ТО данными из базы данных (в реальном приложении вы можете получать данные из базы данных или другого источника)
        val cursor = dbHelper.getMaintenanceByCarId(car.id)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_ID))
//            val icon = cursor.getInt(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_ICON))
            val work =
                cursor.getString(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_WORK))
            val date =
                cursor.getString(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_DATE))
            val mileage =
                cursor.getInt(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_MILEAGE))
            val nextDate =
                cursor.getString(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_NEXT_DATE))
            val nextMileage =
                cursor.getInt(cursor.getColumnIndex(DBHelper.MaintenanceTable.COLUMN_NEXT_MILEAGE))
            val maintenance = Maintenance(id, work, date, mileage, nextDate, nextMileage)
            maintenanceList.add(maintenance)
        }
        cursor.close()

        // Создаем адаптер и передаем ему список ТО и слушатель нажатий на элементы списка
        maintenanceAdapter =
            MaintenanceAdapter(maintenanceList, object : MaintenanceAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    // Проверяем, что позиция действительна и не равна предыдущей выбранной позиции
                    if (position != RecyclerView.NO_POSITION && position != selectedPosition) {
                        // Получаем объект ТО из списка по позиции элемента
                        val maintenance = maintenanceList[position]
                        // Устанавливаем текст для видов с детальной информацией о выбранном ТО
                        workTextView.text = maintenance.work
                        dateTextView.text = maintenance.date
                        maintenanceMileageTextView.text = "${maintenance.mileage} км"
                        nextTextView.text =
                            "Следующее ТО: ${maintenance.nextDate} или ${maintenance.nextMileage} км"
                        // Делаем вид с детальной информацией видимым
                        detailLayout.visibility = View.VISIBLE
                        // Обновляем позицию выбранного элемента
                        selectedPosition = position
                    }
                }
            })

        // Устанавливаем адаптер и менеджер размещения для RecyclerView
        recyclerView.adapter = maintenanceAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Устанавливаем слушатель нажатий на кнопку обновления даты последнего ТО
        updateButton.setOnClickListener {
            // Проверяем, что выбран какой-то элемент списка ТО
            if (selectedPosition != -1) {
                // Получаем объект ТО из списка по выбранной позиции
                val maintenance = maintenanceList[selectedPosition]
                // Создаем объект для хранения текущей даты в формате "dd.MM.yyyy"
                val currentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
                // Обновляем дату последнего ТО в объекте ТО на текущую дату
                maintenance.date = currentDate
                // Обновляем дату последнего ТО в базе данных с помощью метода update из класса DBHelper
                dbHelper.updateMaintenance(
                    maintenance.id,
                    DBHelper.MaintenanceTable.COLUMN_DATE,
                    currentDate
                )
                // Обновляем текст для вида с датой последнего ТО на текущую дату
                dateTextView.text = currentDate
                // Выводим сообщение об успешном обновлении даты последнего ТО на экран с помощью Toast.makeText
                Toast.makeText(this, "Date updated", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
