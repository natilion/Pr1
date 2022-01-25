package com.example.practika2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {

    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerview : RecyclerView
    private lateinit var itemArrayList : ArrayList<Item>
    public lateinit var groupName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        groupName = intent.getStringExtra("nameGroup").toString()
        GroupName(groupName)

        AddItemBtn.setOnClickListener {
            val IntentActivity = Intent(this, OneItemActivity::class.java)
            IntentActivity.putExtra("vendercode", "0")
            IntentActivity.putExtra("groupName", groupName)
            startActivity(IntentActivity)
        }

        itemRecyclerview = findViewById(R.id.itemList)
        itemRecyclerview.layoutManager = LinearLayoutManager(this)
        itemRecyclerview.setHasFixedSize(true)
        itemArrayList = arrayListOf<Item>()
    }

    override fun onResume() {
        itemArrayList.clear()
        getUserData()
        super.onResume()
    }

    override fun onItemClick(position: Int) {
        val clickedItem = itemArrayList[position]
        val IntentActivity = Intent(this, OneItemActivity::class.java)
        IntentActivity.putExtra("vendercode", clickedItem.vendorcode)
        IntentActivity.putExtra("groupName", groupName)
        startActivity(IntentActivity)
    }

    fun GroupName(group:String){
        when(group){
            "0" -> groupName = "Monitors"
            "1" -> groupName = "Keyboards"
            "2" -> groupName = "Computer mouse"
            "3" -> groupName = "System blocks"
            "4" -> groupName = "Switches"
            "5" -> groupName = "Routers"
            "6" -> groupName = "Wires"
            "7" -> groupName = "Servers"
            "8" -> groupName = "Printers"
            "9" -> groupName = "Scanners"
            "10" -> groupName = "Cameras"
        }
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference(groupName)
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (itemSnapshot in snapshot.children){
                        val item = itemSnapshot.getValue(Item::class.java)
                        itemArrayList.add(item!!)
                    }
                    itemRecyclerview.adapter = MyAdapter(itemArrayList, this@MainActivity)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}