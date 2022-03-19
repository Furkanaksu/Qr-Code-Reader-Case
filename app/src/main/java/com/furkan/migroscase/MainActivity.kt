package com.furkan.migroscase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Telephony.Mms.Part.CONTENT_ID
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import com.furkan.migroscase.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        db.collection("Transaction").document("7rb4Iveo5eTlWF1Xr0ae")
            .update(mapOf(
                "Status" to "waiting",
            ))

        binding.customer.setOnClickListener {
            val i = Intent(this,CustomerActivity::class.java)
            startActivity(i)
        }

        binding.cashier.setOnClickListener {
            val i = Intent(this,CashierActivity::class.java)
            startActivity(i)
        }

        val mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.CONTENT, "15")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "contentType")
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        val params1 = Bundle()
        params1.putString("deneme_deneme","true")
        mFirebaseAnalytics?.logEvent("deneme_event", params1)


        binding.sendCrash.setOnClickListener {
                throw RuntimeException("Deneme Migros Case Test Crash123") // Force a crash
        }
    }
}