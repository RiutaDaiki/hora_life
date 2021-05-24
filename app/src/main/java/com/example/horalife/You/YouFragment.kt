package com.example.horalife.You

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.horalife.R
import com.example.horalife.databinding.YouFragmentBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthAnonymousUpgradeException
import com.google.firebase.auth.FirebaseAuth

class YouFragment : Fragment() {
    lateinit var binding: YouFragmentBinding
    private val RC_SIGN_IN = 9001
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = YouFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())
        binding.button2.setOnClickListener(){
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        }


    return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN){
            val user = FirebaseAuth.getInstance().currentUser
            Log.d("debug", user.displayName)
            Toast.makeText(context, user.displayName + "さん", Toast.LENGTH_LONG).show()
        }
    }
}