package com.kuzia.vehiclemaintenanceschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kuzia.vehiclemaintenanceschedule.ui.theme.VehicleMaintenanceScheduleTheme

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    // Список автомобилей, которые будут отображаться в RecyclerView
    private val carList = ArrayList<Car>()

    // Адаптер для связывания данных с видами в RecyclerView
    private lateinit var carAdapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим виды по идентификаторам
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        // Заполняем список автомобилей тестовыми данными (в реальном приложении вы можете получать данные из базы данных или другого источника)
        carList.add(Car(1,"Renault", "Duster", 10000, "01.01.2023", "Замена масла"))
        carList.add(Car(2,"Toyota", "Corolla", 20000, "01.02.2023", "Замена фильтров"))
        carList.add(Car(3, "Volkswagen", "Polo", 30000, "01.03.2023", "Замена свечей"))

        // Создаем адаптер и передаем ему список автомобилей и слушатель нажатий на элементы списка
        carAdapter = CarAdapter(carList, object : CarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Здесь вы можете обработать нажатие на элемент списка и перейти к детальной активности с помощью интента
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                // Передаем интенту данные об выбранном автомобиле
                intent.putExtra("car", carList[position])
                startActivity(intent)
            }
        })

        // Устанавливаем адаптер и менеджер размещения для RecyclerView
        recyclerView.adapter = carAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Устанавливаем слушатель нажатий на плавающую кнопку
        fab.setOnClickListener {
            // Здесь вы можете обработать нажатие на плавающую кнопку и перейти к функциональной активности с помощью интента
            val intent = Intent(this@MainActivity, FunctionActivity::class.java)
            startActivity(intent)
        }
    }
}
