package com.kuzia.vehiclemaintenanceschedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Класс, который является адаптером для связывания данных с видами в RecyclerView
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    // Список автомобилей, которые будут отображаться в RecyclerView
    private ArrayList<Car> carList;

    // Интерфейс для обработки нажатий на элементы списка
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Слушатель нажатий на элементы списка
    private OnItemClickListener listener;

    // Конструктор класса с параметрами для инициализации списка автомобилей и слушателя нажатий
    public CarAdapter(ArrayList<Car> carList, OnItemClickListener listener) {
        this.carList = carList;
        this.listener = listener;
    }

    // Класс, который представляет собой хранитель видов для одного элемента списка
    public static class CarViewHolder extends RecyclerView.ViewHolder {

        // Виды для отображения марки, модели и пробега автомобиля
        public TextView brandTextView;
        public TextView modelTextView;
        public TextView mileageTextView;

        // Конструктор класса с параметром для инициализации видов
        public CarViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            brandTextView = itemView.findViewById(R.id.brandTextView);
            modelTextView = itemView.findViewById(R.id.modelTextView);
            mileageTextView = itemView.findViewById(R.id.mileageTextView);

            // Устанавливаем слушатель нажатий на элемент списка
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Получаем позицию элемента в списке
                    int position = getAdapterPosition();
                    // Проверяем, что позиция действительна и слушатель не равен null
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        // Вызываем метод слушателя с передачей позиции элемента
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    // Метод для создания хранителя видов для каждого элемента списка
    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаем объект View из layout-файла для одного элемента списка
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        // Возвращаем новый объект хранителя видов с передачей созданного объекта View и слушателя нажатий
        return new CarViewHolder(itemView, listener);
    }

    // Метод для связывания данных с видами для каждого элемента списка
    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        // Получаем объект автомобиля из списка по позиции элемента
        Car car = carList.get(position);
        // Устанавливаем текст для видов с информацией об автомобиле
        holder.brandTextView.setText(car.getBrand());
        holder.modelTextView.setText(car.getModel());
        holder.mileageTextView.setText(car.getMileage() + " км");
    }

    // Метод для получения количества элементов в списке
    @Override
    public int getItemCount() {
        return carList.size();
    }
}
