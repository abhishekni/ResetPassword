package com.example.firminiq_test.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firminiq_test.R
import com.example.firminiq_test.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mViewModel: MainActivityViewModel
    private var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        submit.setOnClickListener {
            putValidation()
        }
        mViewModel.email.observe(this, Observer {text ->
            email.setText(text)
        })
        mViewModel.password.observe(this, Observer {text ->
            password.setText(text)
        })
        mViewModel.confirm_password.observe(this, Observer {text ->
            confirm_password.setText(text)
        })
    }

    private fun putValidation() {
        setDataToViewModel()
        flag = false
        if (email.text.isEmpty()) {
            flag = true
            email.error = getString(R.string.email_field)
        }
        else if(!emailValidation(email.text.toString().trim())){
            flag = true
            email.error = getString(R.string.email_format)
        }
        else if (password.text.isEmpty()) {
            flag = true
            password.error = getString(R.string.password_field)
        }
        else if(confirm_password.text.isEmpty()){
            flag = true
            confirm_password.error = getString(R.string.confirm_password_field)
        }
        else if (!password.text.toString().trim().equals(confirm_password.text.toString().trim())){
            flag = true
            confirm_password.setError(getString(R.string.diff_password))
        }
        if (!flag) {
            email.hideKeyBoard()
            showAlertDialog()
        }
    }

    private fun setDataToViewModel() {
        mViewModel.setEmail(email.text.toString())
        mViewModel.setPassword(password.text.toString())
        mViewModel.setConfirmPassword(confirm_password.text.toString())
    }

    private fun EditText.hideKeyBoard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }

    private fun emailValidation(email : String): Boolean{
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.welcome))
        builder.setMessage(getString(R.string.message))
        builder.setPositiveButton(getString(R.string.ok)){ dialogInterface, which ->
            email.setText("")
            password.setText("")
            confirm_password.setText("")
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}