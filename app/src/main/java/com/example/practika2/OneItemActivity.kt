package com.example.practika2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_one_item.*

class OneItemActivity : AppCompatActivity() {

    public lateinit var groupName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_item)

        var database = FirebaseDatabase.getInstance().reference
        val venderCode = intent.getStringExtra("vendercode")
        groupName = intent.getStringExtra("groupName").toString()

        if (venderCode == "0"){//добавление
            vendorcode.isEnabled = true
            EditItemBtn.isVisible = false
        }else{//просмотр/редактирование
            name.isEnabled = false
            specifications.isEnabled = false
            about.isEnabled = false
            button.text = "Удалить"
            vendorcode.isEnabled = false
            EditItemBtn.isVisible = true
            vendorcode.setText(venderCode)

            database = FirebaseDatabase.getInstance().getReference(groupName)
            database.child(vendorcode.getText().toString()).get().addOnSuccessListener {
                val n = it.child("name").value
                val s = it.child("specifications").value
                val a = it.child("about").value

                name.setText(n.toString())
                specifications.setText(s.toString())
                about.setText(a.toString())
            }
        }

        button.setOnClickListener {
            if (Check()) {
                if (button.text == "Удалить"){
                    database = FirebaseDatabase.getInstance().getReference(groupName)
                    database.child(vendorcode.getText().toString()).removeValue()
                    Toast.makeText(this,"Объект удалён", Toast.LENGTH_LONG).show()
                }else {
                    var Vendorcode = vendorcode.text.toString()
                    var Name = name.text.toString()
                    var Specifications = specifications.text.toString()
                    var About = about.text.toString()
                    database.child(groupName).child(Vendorcode.toString())
                        .setValue(Item(Vendorcode, Name, Specifications, About))
                    Toast.makeText(this, "Объект добавлен", Toast.LENGTH_LONG).show()

                }
                this.finish()
            }else{
                Toast.makeText(this, "Неверно заполнены поля", Toast.LENGTH_LONG).show()
            }
        }

        EditItemBtn.setOnClickListener {
            if (Check()) {
                if (name.isEnabled == true) {
                    name.isEnabled = false
                    specifications.isEnabled = false
                    about.isEnabled = false

                    database = FirebaseDatabase.getInstance().getReference(groupName)
                    val item = mapOf<String, String>(
                        "name" to name.text.toString(),
                        "specifications" to specifications.text.toString(),
                        "about" to about.text.toString(),
                    )
                    database.child(vendorcode.getText().toString()).updateChildren(item)
                    Toast.makeText(this, "Данные отредактированы", Toast.LENGTH_LONG).show()
                } else {
                    name.isEnabled = true
                    specifications.isEnabled = true
                    about.isEnabled = true
                    Toast.makeText(this, "Редактирование разрешено", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Неверно заполнены поля", Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun Check() : Boolean{
        return vendorcode.getText().length >= 15 && name.getText().toString() != "" && specifications.getText().toString() != "" && about.getText().toString() != ""
    }
}