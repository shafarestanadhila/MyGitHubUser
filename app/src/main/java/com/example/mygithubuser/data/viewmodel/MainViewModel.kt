package com.example.mygithubuser.data.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.local.SettingPreferences
import com.example.mygithubuser.data.local.dataStore
import com.example.mygithubuser.data.model.ItemsItem
import com.example.mygithubuser.data.model.UserResponse
import com.example.mygithubuser.data.retrofit.RetrofitClient
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {
        setSearchUsers("sidiq")
    }

    private val _listUsers =  MutableLiveData<ArrayList<ItemsItem>>()
    val listUsers : LiveData<ArrayList<ItemsItem>> = _listUsers

    fun setSearchUsers(query: String){
        RetrofitClient.getApiService()
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        _listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    val errorMessage = "Terjadi kesalahan: ${t.message}"
                    Toast.makeText(getApplication(), errorMessage, Toast.LENGTH_SHORT).show()
                }

        })
    }

    fun getSearchUsers(): LiveData<ArrayList<ItemsItem>>{
        return listUsers
    }
}