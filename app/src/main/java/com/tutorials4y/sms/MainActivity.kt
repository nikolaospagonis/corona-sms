package com.tutorials4y.sms

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : Activity() {

    private val MY_PERMISSIONS_REQUEST_SEND_SMS = 0
    var sendBtn: Button? = null
    var txtphoneNo: EditText? = null
    var txtMessage: EditText? = null
    var phoneNo: String? = null
    var message: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendBtn = findViewById<View>(R.id.btnSendSMS) as Button
        txtphoneNo = findViewById<View>(R.id.smsTo) as EditText
        txtMessage = findViewById<View>(R.id.Address) as EditText

        // Εδώ σώζουμε τα στοιχεία που βάλαμε στα πεδία εσωτερικά στην εφαρμογη. Ειναι για λιγα στοιχεια αν θέλω να βάλω περισσότερα sqlite
        val pref =
            applicationContext.getSharedPreferences("MyPref", 0) // 0 - for private mode

        val editor = pref.edit()
        firstName.setText(pref.getString("firstname", null)) // getting String
        lastName.setText(pref.getString("lastname", null)) // getting String
        Address.setText(pref.getString("Address", null)) // getting String
        //------------------------------------------------------------------------------------------


        //Βάζω τιμές στa radiobutton
        r1.setText("1. ΦΑΡΜΑΚΕΙΟ ΓΙΑΤΡΟΣ ")
        r2.setText("2. ΣΟΥΠΕΡ ΦΟΥΡΝΟΙ")
        r3.setText("3. ΤΡΑΠΕΖΑ")
        r4.setText("4. ΒΟΗΘΕΙΑ ΣΕ ΟΣΟΥΣ ΕΧΟΥΝ ΑΝΑΓΚΗ")
        r5.setText("5. ΓΑΜΟΙ ΒΑΠΤΙΣΕΙΣ ΚΗΔΕΙΕΣ ΔΙΑΖΕΥΓΜΕΝΟΙ ΝΑ ΔΟΥΝ ΠΑΙΔΙΑ")
        r6.setText("6. ΣΩΜΑΤΙΚΗ ΑΣΚΗΣΗ.")

        sendBtn!!.setOnClickListener {

            editor.putString("firstname", firstName.text.toString()); // Storing string
            editor.putString("lastname", lastName.text.toString()); // Storing string
            editor.putString("Address", Address.text.toString()); // Storing string


            editor.commit(); // commit changes


            if (r_group.getCheckedRadioButtonId() != -1) {

                if (r1.isChecked) {
                    message = "1 "
                } else if (r2.isChecked) {
                    message = "2 "
                } else if (r3.isChecked) {
                    message = "3 "
                } else if (r4.isChecked) {
                    message = "4 "
                } else if (r5.isChecked) {
                    message = "5 "
                } else if (r6.isChecked) {
                    message = "6 "
                }
                if (lastName.text.toString().trim().length > 0) {
                    if (firstName.text.toString().trim().length > 0) {
                        if (Address.text.toString().trim().length > 0) {
                            message += firstName.text.toString() + " " + lastName.text.toString() + " " + Address.text.toString()
                            /*Toast.makeText(
                                applicationContext,
                                "Message : " + message,
                                Toast.LENGTH_SHORT
                            ).show()*/

                            sendSMSMessage()  //if it is ready to work uncomment it


                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Message : " + "Πρόβλημα δεν έχετε συμπληρώσει την διεύθυνση",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Message : " + "Πρόβλημα δεν έχετε συμπληρώσει όνομα",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {

                    Toast.makeText(
                        applicationContext,
                        "Message : " + "Πρόβλημα δεν έχετε συμπληρώσει επώνυμο",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {

                Toast.makeText(
                    applicationContext,
                    "Message : " + "Πρόβλημα δεν συμπλήρωσες είδος άδειας",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

    }

    //-------------------------------------------------
    protected fun sendSMSMessage() =// phoneNo = txtphoneNo!!.text.toString()
        // message = txtMessage!!.text.toString()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    MY_PERMISSIONS_REQUEST_SEND_SMS
                )
            }
        } else { // granted you can send.

            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage("13033", null, message, null, null)


            Toast.makeText(
                applicationContext, "SMS $message στάλθηκε επιτυχώς.",
                Toast.LENGTH_LONG
            ).show()

            Handler().postDelayed({
                finishAffinity()
                //doSomethingHere()
            }, 5000)




        }
    //--------------------------------------------------------------------


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_SEND_SMS -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    val smsManager: SmsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage("13033", null, message, null, null)

                    Toast.makeText(
                        applicationContext, "SMS $message στάλθηκε επιτυχώς.",
                        Toast.LENGTH_LONG
                    ).show()


                } else
                    Toast.makeText(
                        applicationContext,
                        "Message : " + "Πρόβλημα στην αποστολή...",
                        Toast.LENGTH_SHORT
                    ).show()

            }
        }
    }


//------------------------------
}



