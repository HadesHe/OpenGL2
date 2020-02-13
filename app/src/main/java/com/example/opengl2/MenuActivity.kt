package com.example.opengl2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hezhanghe on 2020-02-13.
 * github: https://github.com/HadesHe
 */
class MenuActivity : AppCompatActivity() {

    private val rvCotent by lazy {
        findViewById<RecyclerView>(R.id.rv)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        rvCotent.layoutManager = LinearLayoutManager(MenuActivity@ this)
        rvCotent.adapter = ContentAdapter(fakeData())

    }

    private fun fakeData(): ArrayList<IntentClass> {
        return arrayListOf(
            IntentClass("Triangle", Intent(MenuActivity@ this, MainActivity::class.java)),
            IntentClass("Cube", Intent(MenuActivity@ this, CubeActivity::class.java)),
            IntentClass("Pentagon", Intent(MenuActivity@ this, PentagonActivity::class.java)),
            IntentClass("Thexagon", Intent(MenuActivity@ this, HexagonActivity::class.java))
        )
    }


    inner class ContentAdapter(val datas: ArrayList<IntentClass>) :
        RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ContentAdapter.ViewHolder {
            var view =
                LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
            var holder = ViewHolder(view)
            view.setOnClickListener {
                view.context.startActivity(datas[holder.adapterPosition].intent)
            }
            return holder
        }

        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(holder: ContentAdapter.ViewHolder, position: Int) {
            holder.tvTitle.text = datas[position].desc

        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvTitle: TextView

            init {
                tvTitle = itemView.findViewById<TextView>(R.id.title)
            }
        }

    }

}

data class IntentClass(val desc: String, val intent: Intent)
