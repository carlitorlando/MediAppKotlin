package com.example.mediapp

import android.content.DialogInterface
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    /*var medNames = arrayOf<String>("","")
    var medDosage = arrayOf<String>("","")
    var medDuration = arrayOf<String>("","")
    var medInterval = arrayOf<String>("","")
    var medBegin = arrayOf<String>("","")
    var medDosageStat = arrayOf<String>("","")*/

    var medNames : Array<String?> = arrayOfNulls<String>(10)
    var medDosage : Array<String?> = arrayOfNulls<String>(10)
    var medDuration : Array<String?> = arrayOfNulls<String>(10)
    var medInterval : Array<String?> = arrayOfNulls<String>(10)
    var medBegin : Array<String?> = arrayOfNulls<String>(10)
    var medDosageStat : Array<String?> = arrayOfNulls<String>(10)
    var i = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbtn_add.setOnClickListener {
            setAlertDialogueLogin()

        }

    }

    fun setAlertDialogueLogin() {



        val mDialogView = LayoutInflater.from(this).inflate(R.layout.activity_add, null)
        val mBuilder = android.app.AlertDialog.Builder(this)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()


        mDialogView.btn_add.setOnClickListener {
            val inputName = mDialogView.med_name.text.toString()
            val inputDosage = mDialogView.med_dosage.text.toString()
            val inputDuration = mDialogView.med_duration.text.toString()
            val inputInterval = mDialogView.med_interval.text.toString()
            val inputBegin = mDialogView.med_begintime.text.toString()
            medNames[i] = inputName
            medDosage[i] = inputDosage
            medDuration[i] = inputDuration
            medInterval[i] = inputInterval
            medBegin[i] = inputBegin
            i++
            Toast.makeText(this, "Added to list", Toast.LENGTH_SHORT).show()
            createList()

            mAlertDialog.dismiss()
        }

        mDialogView.btn_cancel.setOnClickListener {
            mAlertDialog.dismiss()
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
        }
    }

    fun createList(){
        val myListAdapter = MyListAdapter(this, medNames, medDosage, medBegin, medDosageStat)
        listView.adapter = myListAdapter


        listView.setOnItemClickListener() { adapterView, view, position, id ->
            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)

            //Display Toast for Debugging
            Toast.makeText(this, "Click on item at $itemAtPos its item id $itemIdAtPos", Toast.LENGTH_SHORT).show()

            //Create and Show Dialog Function
            createDialog(position)

            //Use Notification sound as Sound
            val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            // Play Sound
            val mediaPlayer = MediaPlayer.create(this, alarmSound)
            mediaPlayer?.start()
        }
    }

    private fun createDialog(pos : Int){
        AlertDialog.Builder(this)

            // 2. Chain together various setter methods to set the dialog characteristics
            .setTitle(medNames[pos])
            .setNeutralButton("Back",
                DialogInterface.OnClickListener { dialog, id ->
                    // User clicked OK button
                }
            )
            .show()
    }

}