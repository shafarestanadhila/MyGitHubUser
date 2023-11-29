package com.example.mygithubuser.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.data.local.FavoriteUser
import com.example.mygithubuser.data.model.ItemsItem
import com.example.mygithubuser.data.viewmodel.FavoriteViewModel
import com.example.mygithubuser.databinding.ActivityFavoriteBinding
import com.example.mygithubuser.ui.adapter.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@FavoriteActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            showLoading(false)
            if (it!=null){
                val list = mapList(it)
                adapter.setList(list)
                if (it.isEmpty()) {
                    showEmptyUsersMessage()
                }
            } else {
                showError(getString(R.string.error))
            }
        }

    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showEmptyUsersMessage() {
        binding.tvEmptyFavorite.visibility = View.VISIBLE
        binding.tvEmptyFavorite.text
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this@FavoriteActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<ItemsItem> {
        val listUsers = ArrayList<ItemsItem>()
        for (user in users) {
            val userMapped = ItemsItem(
                user.login,
                user.avatarUrl,
                user.id
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}