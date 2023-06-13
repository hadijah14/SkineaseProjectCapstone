package com.example.skineast

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.skineast.halaman_detail.detail_tropis
import com.example.skineast.halaman_detail.fungsi_kulit
import com.example.skineast.halaman_detail.kesalahan_memakai_tabir
import com.example.skineast.halaman_detail.mengenal_struktur_kulit
import com.example.skineast.halaman_detail.struktur_kulit
import com.example.skineast.halaman_penyakit.jerawat
import com.example.skineast.halaman_penyakit.kurap
import org.w3c.dom.Text

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val itemTitles = arrayOf(
        "Cara merawat kulit di negara tropis",
        "Mengenal Struktur Kulit Manusia Beserta Fungsinya",
        "Struktur Kulit Manusia",
        "Fungsi Kulit Manusia",
        "8 Kesalahan memakai Tabir Surya yang jarang disadari",
    )

    private val itemDetails = arrayOf(
        "klik untuk melanjutkan",
        "klik untuk melanjutkan",
        "klik untuk melanjutkan",
        "klik untuk melanjutkan",
        "klik untuk melanjutkan"
    )

    private val itemImages = intArrayOf(
        R.drawable.cara_merawat_kulit,
        R.drawable.struktur_kulit,
        R.drawable.struktuur,
        R.drawable.kulit_tropis_1,
        R.drawable.delapan_kesalahan,
    )

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        var image: ImageView
        var textTitle: TextView
        var textDes: TextView

        init {
            image = itemView.findViewById(R.id.item_image)
            textTitle = itemView.findViewById(R.id.item_title)
            textDes = itemView.findViewById(R.id.item_detail)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycleview_model,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemTitles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = itemTitles[position]
        holder.textDes.text = itemDetails [position]
        holder.image.setImageResource(itemImages[position])

        holder.itemView.setOnClickListener { v:View ->
            when (position){
                0 -> {
                    // Kode untuk pindah ke halaman pertama
                    val intent = Intent(v.context, detail_tropis::class.java)
                    v.context.startActivity(intent)
            }
            };
            when (position){
                1 -> {
                    // Kode untuk pindah ke halaman pertama
                    val intent = Intent(v.context, mengenal_struktur_kulit::class.java)
                    v.context.startActivity(intent)
                }
            };
            when (position){
                2 -> {
                    // Kode untuk pindah ke halaman pertama
                    val intent = Intent(v.context, struktur_kulit::class.java)
                    v.context.startActivity(intent)
                }
            };
            when (position){
                3 -> {
                    // Kode untuk pindah ke halaman pertama
                    val intent = Intent(v.context, fungsi_kulit::class.java)
                    v.context.startActivity(intent)
                }
            };
            when (position){
                4 -> {
                    // Kode untuk pindah ke halaman pertama
                    val intent = Intent(v.context, kesalahan_memakai_tabir::class.java)
                    v.context.startActivity(intent)
                }
            };
        }
    }
}