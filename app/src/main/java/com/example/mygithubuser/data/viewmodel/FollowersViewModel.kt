package com.example.mygithubuser.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.data.model.ItemsItem
import com.example.mygithubuser.data.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    private val listFollowers = MutableLiveData<ArrayList<ItemsItem>>()

    private val _error = MutableLiveData<String>()

    fun setListFollowers(username: String) {
        RetrofitClient.getApiService()
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<ItemsItem>> {
                override fun onResponse(
                    call: Call<ArrayList<ItemsItem>>,
                    response: Response<ArrayList<ItemsItem>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    } else {
                        _error.postValue("Gagal memuat data followers")
                    }
                }

                override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                    _error.postValue("Terjadi kesalahan: ${t.message}")
                }
            })
    }

    fun getListFollowers(): LiveData<ArrayList<ItemsItem>> {
        return listFollowers
    }
}