package com.example.firminiq_test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val confirm_password = MutableLiveData<String>()


    fun setEmail(emails: String){
        email.postValue(emails)
    }
    fun setPassword(passwd: String){
        password.postValue(passwd)
    }
    fun setConfirmPassword(confirmpswd: String){
        confirm_password.postValue(confirmpswd)
    }

}