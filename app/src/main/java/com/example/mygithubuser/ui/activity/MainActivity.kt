package com.example.mygithubuser.ui.activity

import android.content.Intent
import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.R
import com.example.mygithubuser.data.local.SettingPreferences
import com.example.mygithubuser.data.local.dataStore
import com.example.mygithubuser.data.model.ItemsItem
import com.example.mygithubuser.data.viewmodel.MainViewModel
import com.example.mygithubuser.data.viewmodel.SettingViewModel
import com.example.mygithubuser.data.viewmodel.ViewModelFactory
import com.example.mygithubuser.databinding.ActivityMainBinding
import com.example.mygithubuser.ui.adapter.UserAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu1 -> {
                    val intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ItemsItem) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatarUrl)
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(MainViewModel::class.java)

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchUser(query)
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        viewModel.getSearchUsers().observe(this) { listUsers ->
            showLoading(false)
            adapter.setList(listUsers)
            if (listUsers != null) {
                if (listUsers.isNotEmpty()) {
                    hideEmptyUsersMessage()
                } else {
                    showEmptyUsersMessage()
                }
            } else {
                showError(getString(R.string.error))
            }
        }
    }

    private fun searchUser(query: String?){
        binding.apply {
            if (query.isNullOrBlank()) {
                return
            }
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showEmptyUsersMessage() {
        binding.tvEmptyUsers.visibility = View.VISIBLE
        binding.tvEmptyUsers.text
    }

    private fun hideEmptyUsersMessage() {
        binding.tvEmptyUsers.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
    }
}