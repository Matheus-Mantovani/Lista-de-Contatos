package br.edu.ifsp.dmo.listadecontatos.view

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.dmo.listadecontatos.R
import br.edu.ifsp.dmo.listadecontatos.databinding.ActivityMainBinding
import br.edu.ifsp.dmo.listadecontatos.databinding.ContactItemLayoutBinding
import br.edu.ifsp.dmo.listadecontatos.databinding.NewContactDialogBinding
import br.edu.ifsp.dmo.listadecontatos.model.Contact
import br.edu.ifsp.dmo.listadecontatos.model.ContactDao

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private val TAG = "CONTACTS"
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListContactAdapter
    private val listDataSource = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.v(TAG, "Executando onCreate()")
        configClickListener()
        configListView()
    }

    override fun onStart() {
        Log.v(TAG, "Executando onStart()")
        super.onStart()
    }

    override fun onResume() {
        Log.v(TAG, "Executando onResume()")
        super.onResume()
    }

    override fun onPause() {
        Log.v(TAG, "Executando onPause()")
        super.onPause()
    }

    override fun onStop() {
        Log.v(TAG, "Executando onStop()")
        super.onStop()
    }

    override fun onRestart() {
        Log.v(TAG, "Executando onRestart()")
        super.onRestart()
    }

    override fun onDestroy() {
        Log.v(TAG, "Executando onDestroy()")
        Log.v(TAG, "Lista de contatos que será perdida")
        for(contact in ContactDao.findAll()) {
            Log.v(TAG, contact.toString())
        }
        super.onDestroy()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectContact = binding.listviewContacts.adapter.getItem(position) as Contact
        val uri = "tel:${selectContact.phone}"
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(uri)
        startActivity(intent)
    }

    private fun configListView() {
        listDataSource.addAll(ContactDao.findAll())
        adapter = ListContactAdapter(this, listDataSource)
        binding.listviewContacts.adapter = adapter
        binding.listviewContacts.onItemClickListener = this
    }

    private fun configClickListener() {
        binding.buttonNewContact.setOnClickListener {
            handleNewContactDialog()
        }
    }

    private fun updateListDataSource() {
        listDataSource.clear()
        listDataSource.addAll(ContactDao.findAll())

        adapter.notifyDataSetChanged()
    }

    private fun handleNewContactDialog() {

        val bindingDialog = NewContactDialogBinding.inflate(layoutInflater)

        val builderDialog = AlertDialog.Builder(this)
        builderDialog.setView(bindingDialog.root)
            .setTitle(R.string.new_contact)
            .setPositiveButton(
                R.string.btn_dialog_save,
                DialogInterface.OnClickListener { dialog, which ->
                    Log.v(TAG, "Salvar contato")
                    ContactDao.insert(
                        Contact(
                            bindingDialog.edittextName.text.toString(),
                            bindingDialog.edittextPhone.text.toString()
                        )
                    )
                    updateListDataSource()
                    dialog.dismiss()
                }
            )
            .setNegativeButton(
                R.string.btn_dialog_cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    Log.v(TAG, "Cancelar novo contato")
                    dialog.cancel()
                }
            )
        builderDialog.create().show()
    }
}