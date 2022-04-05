package com.example.contact_tracking.views

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.contact_tracking.databinding.AddpersonBinding
import com.example.contact_tracking.databinding.MainBinding
import com.example.contact_tracking.item.ItemPerson
import com.example.contact_tracking.logger.Logger
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity() : AppCompatActivity(){

    // variables for binding and Logger
    private var binding: MainBinding? = null
    private val gui get() = binding!!

    // variable for logger
    private var logger: Logger = Logger(this.javaClass.simpleName)

    // variables Person
    private lateinit var btnOpenDialog: List<MaterialButton>
    private val personInfo = mutableListOf<ItemPerson>()

    override fun onCreate(savedInstanceState: Bundle?) {
        this.logger.info("MainActivity started")

        // locking app into landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        // binding inflate layout
        binding = MainBinding.inflate(layoutInflater)
        setContentView(gui.root)

        // created a variable for all person btn
        btnOpenDialog = listOf(gui.btnPerson1, gui.btnPerson2, gui.btnPerson3, gui.btnPerson4, gui.btnPerson5)

        // run function openDialog
        openDialog()
    }

    private fun showMessageBox(materialButton: MaterialButton) {
        // inflate addperson layout
        var addPersonBinding = AddpersonBinding.inflate(LayoutInflater.from(layoutInflater.context))

        // alertDialogBuilder
        val messageBoxBuilder =
            MaterialAlertDialogBuilder(this@MainActivity).setView(addPersonBinding.LayoutAddPerson)

        // show dialog
        val messageBoxInstance = messageBoxBuilder.show()
        // set Listener
        addPersonBinding.btnClose.setOnClickListener() {
            // close dialog
            messageBoxInstance.dismiss()
            //ItemPerso data filled with first & lastname
            addPersonBinding.btnAddPerson.setOnClickListener {
                val personItem = ItemPerson(addPersonBinding.textInputFirstname.toString(), addPersonBinding.textInputLastname.toString())
                personInfo.add(personItem)
            }

        }
    }

    private fun openDialog() {
        for (i in btnOpenDialog.indices) {
            btnOpenDialog[i].setOnClickListener {
                showMessageBox(btnOpenDialog[i])
            }
        }
    }
}