package com.example.takilmaca

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private  lateinit var auth: FirebaseAuth
    lateinit var navController: NavController


    override fun onViewCreated(view: View ,savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        navController = Navigation.findNavController(view)
        auth = Firebase.auth
        val currentUser=auth.currentUser
        if (currentUser!=null){
            navController.navigate(R.id.action_loginFragment_to_chatFragment)

        }
       view.findViewById<Button>(R.id.signup).setOnClickListener {
            auth.createUserWithEmailAndPassword(view.findViewById<EditText>(R.id.email).text.toString(),view.findViewById<TextView>(R.id.sifre).text.toString()).addOnSuccessListener {
                //kullanıcı oluşturuldu
                navController.navigate(R.id.action_loginFragment_to_chatFragment)
            }.addOnFailureListener { exception ->
                //hata durumu
               Toast.makeText(requireContext(),exception.localizedMessage, Toast.LENGTH_LONG ).show()
            }
        }
        view.findViewById<Button>(R.id.login).setOnClickListener {
            auth.signInWithEmailAndPassword(view.findViewById<EditText>(R.id.email).text.toString(),view.findViewById<TextView>(R.id.sifre).text.toString()).addOnSuccessListener {
                navController.navigate(R.id.action_loginFragment_to_chatFragment)
            }.addOnFailureListener { exception->
                Toast.makeText(requireContext(),exception.localizedMessage, Toast.LENGTH_LONG ).show()


            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    }
