package com.kuzia.vehiclemaintenanceschedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Класс, который является адаптером для связывания данных с видами в RecyclerView
class MaintenanceAdapter(
    private val maintenanceList: ArrayList<Maintenance>, // Список ТО, которые будут отображаться в RecyclerView
    private val listener: OnItemClickListener // Слушатель нажатий на элементы списка
) : RecyclerView.Adapter<MaintenanceAdapter.MaintenanceViewHolder>() {

    // Интерфейс для обработки нажатий на элементы списка
    interface OnItemClickListener {
        fun onItemClick(position: Int) // Метод, который вызывается при нажатии на элемент списка
    }

    // Класс, который представляет собой хранитель видов для одного элемента списка
    class MaintenanceViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        // Виды для отображения иконки и названия ТО
        val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)
        val workTextView: TextView = itemView.findViewById(R.id.workTextView)

        init {
            // Устанавливаем слушатель нажатий на элемент списка
            itemView.setOnClickListener {
                // Получаем позицию элемента в списке
                val position = adapterPosition
                // Проверяем, что позиция действительна и слушатель не равен null
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    // Вызываем метод слушателя с передачей позиции элемента
                    listener.onItemClick(position)
                }
            }
        }
    }

    // Метод для создания хранителя видов для каждого элемента списка
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaintenanceViewHolder {
        // Создаем объект View из layout-файла для одного элемента списка
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.maintenance_item, parent, false)
        // Возвращаем новый объект хранителя видов с передачей созданного объекта View и слушателя нажатий
        return MaintenanceViewHolder(itemView, listener)
    }

    // Метод для связывания данных с видами для каждого элемента списка
    override fun onBindViewHolder(holder: MaintenanceViewHolder, position: Int) {
        // Получаем объект ТО из списка по позиции элемента
        val maintenance = maintenanceList[position]
        // Устанавливаем изображение и текст для видов с информацией о ТО
//        holder.iconImageView.setImageResource(maintenance.icon)
        holder.workTextView.text = maintenance.work
    }

    // Метод для получения количества элементов в списке
    override fun getItemCount(): Int {
        return maintenanceList.size
    }
}
