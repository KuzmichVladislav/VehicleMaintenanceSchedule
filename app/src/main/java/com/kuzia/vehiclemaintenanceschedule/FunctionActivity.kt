package com.kuzia.vehiclemaintenanceschedule

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FunctionActivity : AppCompatActivity() {

    // Переменные для хранения данных об автомобиле, которые переданы из главной активности через интент
    private lateinit var car: Car

    // Переменные для хранения видов по идентификаторам
    private lateinit var brandTextView: TextView
    private lateinit var modelTextView: TextView
    private lateinit var mileageEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)

        // Получаем данные об автомобиле из интента
        car = intent.getSerializableExtra("car") as Car

        // Находим виды по идентификаторам
        brandTextView = findViewById(R.id.brandTextView)
        modelTextView = findViewById(R.id.modelTextView)
        mileageEditText = findViewById(R.id.mileageEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Устанавливаем текст для видов с информацией об автомобиле
        brandTextView.text = car.brand
        modelTextView.text = car.model

        // Устанавливаем слушатель нажатий на кнопку расчета следующего ТО
        calculateButton.setOnClickListener {
            // Здесь вы можете обработать нажатие на кнопку расчета следующего ТО и вывести результат на экран в виде текста или графика
            // Здесь мы используем простую логику расчета, но в реальном приложении вы можете использовать более сложную логику с учетом рекомендаций производителя автомобиля или общих правил технического обслуживания
            val currentMileage = mileageEditText.text.toString().toInt() // Получаем текущий пробег автомобиля из EditText
            val nextMileage = currentMileage + 10000 // Следующий пробег для ТО равен текущему пробегу плюс 10000 км
            val nextDate = "01.04.2023" // Следующая дата для ТО (здесь мы используем фиксированную дату, но в реальном приложении вы можете использовать функции для работы с датами и временем)
            val nextWork = "Замена тормозных колодок" // Следующий вид работы для ТО (здесь мы используем фиксированное значение, но в реальном приложении вы можете использовать массив или список с возможными видами работ)
            resultTextView.text = "Следующее ТО: $nextDate или $nextMileage км\nВид работы: $nextWork" // Выводим результат на экран в виде текста
            resultTextView.visibility = View.VISIBLE // Делаем вид с результатом видимым
        }
    }
}
