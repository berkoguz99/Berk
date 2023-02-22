package com.example.takilmaca

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ChatFragment : Fragment() {
    private lateinit var firestore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView
    private  var chats = ArrayList<Chats>()
    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        adapter=Adapter()
        recyclerView= view.findViewById(R.id.recyclerView)
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())

        firestore = Firebase.firestore
        auth = Firebase.auth


        view.findViewById<Button>(R.id.gonder).setOnClickListener {

          auth.currentUser?.let {
              val chatText= view.findViewById<EditText>(R.id.chat).text.toString()
              val user= it.email
              val date=FieldValue.serverTimestamp()
              val datamap = HashMap<String , Any>()
              datamap.put("text",chatText)
              datamap.put("user",user!!)
              datamap.put("date",date)

              firestore.collection("Chats").add(datamap).addOnSuccessListener {
                    view.findViewById<EditText>(R.id.chat).text.clear()

              }.addOnFailureListener { exception->
                  Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_SHORT).show()

              }
          }

        }
        firestore.collection("Chats").orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { value, error ->

            if(error!=null){
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()

            }
            else{
                if(value!=null){

                    if(value.isEmpty){
                        Toast.makeText(requireContext(),"Mesaj Yok",Toast.LENGTH_LONG).show()
                    }else{

                        val documents= value.documents
                        chats.clear()
                        for (document in documents){
                            val text = document.get("text") as String
                            val user = document.get("user") as String
                            val chat = Chats(user,text)
                            chats.add(chat)
                            adapter.chats=chats
                        }
                    }
                    adapter.notifyDataSetChanged()
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }


    }
