package com.example.mediapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class MyListAdapter (private val context: Activity, private val medNames: Array<String>, private val medDosage: Array<String>, private val medBegin: Array<String>, private val medDosageStat: Array<String>)
    :ArrayAdapter<String>(context, R.layout.activity_listview, medNames) {

    override fun getView(position: Int, View: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.activity_listview, null, true)

        val mednameslist = rowView.findViewById<TextView>(R.id.firstTitleText)
        val meddosagelist = rowView.findViewById<TextView>(R.id.text_dosage)
        val medbeginlist = rowView.findViewById<TextView>(R.id.text_time)
        val meddosagestatlist = rowView.findViewById<TextView>(R.id.text_dosagestat)

        mednameslist.text = medNames[position]
        meddosagelist.text = "How many times to be taken a day: ${medDosage[position]}"
        medbeginlist.text = "Begin Notifying by: ${medBegin[position]}"
        meddosagestatlist.text = "Doses taken today: ${medDosageStat[position]}/${medDosage[position]}"


        return rowView
    }
}