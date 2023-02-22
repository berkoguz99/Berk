package com.example.takilmaca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.util.Objects


class Adapter: RecyclerView.Adapter<Adapter.ChatHolder>() {
    private val  VIEWTYPESEND=1
    private val  VIEWTYPERECEIVED=2
    class ChatHolder(itemView: View):RecyclerView.ViewHolder(itemView){


    }
    private val diffUtil= object : DiffUtil.ItemCallback<Chats>(){
        override fun areItemsTheSame(oldItem: Chats, newItem: Chats): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Chats, newItem: Chats): Boolean {
            return oldItem==newItem        }

    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var chats : List<Chats>
        get()= recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat = chats.get(position)
        if (chat.user==FirebaseAuth.getInstance().currentUser?.email.toString()){
                return VIEWTYPESEND
        }
        else{
            return VIEWTYPERECEIVED
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        if (viewType==VIEWTYPERECEIVED){
         val view = LayoutInflater.from(parent.context).inflate(R.layout.rec_rov,parent, false)
            return ChatHolder(view)}
        else{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rec_rov_right,parent, false)
            return ChatHolder(view)}

        }



    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val textView= holder.itemView.findViewById<TextView>(R.id.recTest)
        if(getItemViewType(position) ==VIEWTYPESEND){
        textView.text="${chats.get(position).text}"}
        else if(getItemViewType(position)==VIEWTYPERECEIVED){
            textView.text="${chats.get(position).user}: ${chats.get(position).text}"
        }
    }

    override fun getItemCount(): Int {
        return chats.size

    }


}
