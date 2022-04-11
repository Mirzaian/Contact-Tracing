package com.example.contact_tracking.views

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.contact_tracking.R
import com.example.contact_tracking.databinding.AddpersonBinding
import com.example.contact_tracking.databinding.MainBinding
import com.example.contact_tracking.item.ItemPerson
import com.example.contact_tracking.logger.Logger
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import java.io.File
import java.io.FileOutputStream


class MainActivity() : AppCompatActivity(){

    // variables for binding
    private var binding: MainBinding? = null
    private val gui get() = binding!!

    // variable for logger
    private var logger: Logger = Logger(this.javaClass.simpleName)

    // variables Person
    private lateinit var btnOpenDialog: List<MaterialButton>
    private var btnPersonInfo = mutableListOf<MaterialTextView>()
    private val personInfo = mutableListOf<ItemPerson>()
    private var personAmount = 0

    // permissions to read and write external
    private val requiredPermissions = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )

    private fun requestPermissions() {
        if (!allPermissionsGranted()) {
            val requestCodePermissions = 101
            ActivityCompat.requestPermissions(this, requiredPermissions, requestCodePermissions)
            requestPermissions()
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        this.logger.info("MainActivity started")

        // locking app into landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)

        // binding inflate layout
        binding = MainBinding.inflate(layoutInflater)
        setContentView(gui.root)

        requestPermissions()

        // created a variable for all person btn & tv
        btnOpenDialog = listOf(gui.btnPerson1, gui.btnPerson2, gui.btnPerson3, gui.btnPerson4, gui.btnPerson5) as MutableList<MaterialButton>
        btnPersonInfo = listOf(gui.tvPerson1, gui.tvPerson2, gui.tvPerson3, gui.tvPerson4, gui.tvPerson5) as MutableList<MaterialTextView>

        exportData()

        // run function openDialog
        openDialog()
    }

    private fun showMessageBox(materialButton: MaterialButton, textView: MaterialTextView) {
        // inflate addperson layout
        val addPersonBinding = AddpersonBinding.inflate(LayoutInflater.from(layoutInflater.context))
        // alertDialogBuilder
        val messageBoxBuilder = MaterialAlertDialogBuilder(this@MainActivity).setView(addPersonBinding.LayoutAddPerson)

        // show dialog
        val messageBoxInstance = messageBoxBuilder.show()
        // set Listener
        addPersonBinding.btnClose.setOnClickListener() {
            // close dialog
            messageBoxInstance.dismiss()
        }

        // itemPerson data filled with first & lastname
        addPersonBinding.btnAddPerson.setOnClickListener {
            val personItem = ItemPerson(addPersonBinding.textInputFirstname.text.toString(), addPersonBinding.textInputLastname.text.toString())
            personInfo.add(personItem)
            val lastIndex = personInfo.lastIndex
            // count personAmout and enable export button
            personAmount++
            gui.btnConvert.isEnabled = true
            materialButton.setIconResource(R.drawable.ic_person_remove)
            logger.info("Person added")

        // btnAddPerson remove lastIndex
        materialButton.setOnClickListener {
            personInfo.removeAt(lastIndex)
            personAmount--
            if(personAmount==0) { gui.btnConvert.isEnabled = false }
            materialButton.setIconResource(R.drawable.ic_person_add)
            textView.visibility = View.GONE
            materialButton.setOnClickListener {
                showMessageBox(materialButton, textView)
            }
        }
            textView.text = getString(R.string.personInfo, addPersonBinding.textInputFirstname.text, addPersonBinding.textInputLastname.text)
            textView.visibility = View.VISIBLE
            messageBoxInstance.dismiss()
        }
    }

    // listener for btnOpenDialog to show the message box
    private fun openDialog() {
        for (i in btnOpenDialog.indices) {
            btnOpenDialog[i].setOnClickListener {
                showMessageBox(btnOpenDialog[i], btnPersonInfo[i])
            }
        }
    }

    private fun exportData() {

        gui.btnConvert.setOnClickListener {
            // variables for csv filename and personInfo categories
            val header = "Firstname, Lastname"
            val filename = "contact_tracking.csv"

            // fun to check sdk version for directory
            val dir = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString() + "/" + "blub")
            } else {
                File(Environment.getExternalStorageDirectory().toString() + "/" + "blub")
            }
            dir.mkdirs()

            // variables create fileOut Object
            val fileOut = File(dir, filename)
            val fileOutStream = FileOutputStream(fileOut)

            // variable for content -> first header then next line
            var content = header+"\n"

            // add firstname and lastname to content -> logger check
            for(item in personInfo) {
                logger.info("${item.firstname}, ${item.lastname}")
                content += "${item.firstname}, ${item.lastname}\n"
            }
            // logger output content
            logger.info(content)

            // write content into csv
            try {
                fileOutStream.write(content.toByteArray())
            } catch (e: Exception) {
                //BLABLABLA
            } finally {
                fileOutStream.close()
            }
            logger.info("contact_tracking.csv created")

        }
    }
}