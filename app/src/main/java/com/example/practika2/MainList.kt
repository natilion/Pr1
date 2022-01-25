package com.example.practika2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.practika2.MainList
import kotlinx.android.synthetic.main.activity_main_list.*

class MainList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        val items = listOf("Мониторы", "Клавиатуры", "Компьютерные мыши", "Системные блоки", "Коммутаторы", "Роутеры",
            "Провода", "Серверы", "Принтеры", "Сканеры", "Камеры")
        mainList.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        mainList.setOnItemClickListener{parent, view, position, id ->
            var nameGroup: String = id.toString()
            val Intent = Intent(this, MainActivity::class.java)
            Intent.putExtra("nameGroup", nameGroup)
            startActivity(Intent)
        }
    }
}