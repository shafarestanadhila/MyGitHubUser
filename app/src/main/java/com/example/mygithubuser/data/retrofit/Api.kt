package com.example.mygithubuser.data.retrofit

import com.example.mygithubuser.data.model.DetailUserResponse
import com.example.mygithubuser.data.model.ItemsItem
import com.example.mygithubuser.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ItemsItem>>
}