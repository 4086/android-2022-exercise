package com.example.firebaseex

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseex.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.textUID.text = Firebase.auth.currentUser?.uid ?: "No User"

        binding.buttonSignout.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this, MainActivity::class.java))
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            binding.textFCMToken.text = if (it.isSuccessful) it.result else "Token Error!"

            // copy FCM token to clipboard
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("FCM Token", binding.textFCMToken.text)
            clipboard.setPrimaryClip(clip)

            // write to logcat
            Log.d(MyFirebaseMessagingService.TAG, "FCM token: ${binding.textFCMToken.text}")
        }
    }
}