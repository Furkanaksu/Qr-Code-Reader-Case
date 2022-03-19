package com.furkan.migroscase

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import androidmads.library.qrgenearator.QRGEncoder

import com.google.zxing.WriterException

import androidmads.library.qrgenearator.QRGContents

import android.view.Display

import android.view.WindowManager

import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CustomerActivity : AppCompatActivity() {

    private var qrCodeIV: ImageView? = null
    var bitmap: Bitmap? = null
    var qrgEncoder: QRGEncoder? = null
    var paymentStatus: TextView? = null
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        qrCodeIV = findViewById(R.id.idIVQrcode)
        paymentStatus = findViewById(R.id.paymentStatus)

        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Event.ADD_PAYMENT_INFO,"true")
        bundle1.putString("DENEME","true")

        mFirebaseAnalytics?.logEvent(FirebaseAnalytics.Event.ADD_PAYMENT_INFO, bundle1)

        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("Transaction").document("7rb4Iveo5eTlWF1Xr0ae")

        docRef
            .update(mapOf(
                "Status" to "waiting",
            ))

        GlobalScope.launch {
            getData()
        }
        // below line is for getting
        // the windowmanager service.
        val manager = getSystemService(WINDOW_SERVICE) as WindowManager

        // initializing a variable for default display.
        val display = manager.defaultDisplay

        // creating a variable for point which
        // is to be displayed in QR Code.
        val point = Point()
        display.getSize(point)

        // getting width and
        // height of a point
        val width: Int = point.x
        val height: Int = point.y

        // generating dimension from width and height.
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder =
            QRGEncoder("Furkan Aksu", null, QRGContents.Type.TEXT, dimen)
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder!!.encodeAsBitmap()
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            qrCodeIV?.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString())
        }
    }


    suspend fun getData(){
        delay(1000)
        val db = FirebaseFirestore.getInstance()

        val docRef = db.collection("Transaction").document("7rb4Iveo5eTlWF1Xr0ae")



        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null)
                {
                    Log.d("denemeeee", document.data?.get(key = "Status").toString())

                    if (document.data?.get(key = "Status") == "true")
                    {
                        paymentStatus?.text = "Ödeme Başarılı"
                        paymentStatus?.setTextColor(ContextCompat.getColor(this, R.color.green))
                        GlobalScope.launch {
                            getData()
                        }
                    }
                    else if (document.data?.get(key = "Status") == "false")
                    {
                        paymentStatus?.text = "Ödeme Başarısız"
                        paymentStatus?.setTextColor(ContextCompat.getColor(this, R.color.red))
                        GlobalScope.launch {
                            getData()
                        }
                    }
                    else
                    {
                        paymentStatus?.text = "Ödeme Bekleniyor"
                        paymentStatus?.setTextColor(ContextCompat.getColor(this, R.color.yellow))
                        GlobalScope.launch {
                            getData()
                        }
                    }
                }else
                {
                    Log.d("Deneme", "hata")
                }
            }
            .addOnFailureListener {
                Log.d("denemee", it.toString())
            }
    }
}