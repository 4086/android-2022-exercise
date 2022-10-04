package com.example.internetaddex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.StringBuilder

class MyViewModel : ViewModel() {
    private val baseURL = "https://api.github.com/"
    private lateinit var api: RestApi

    val response = MutableLiveData<String>()
    private val mutableMap = mutableMapOf<String, String>()
    var name: String= ""

    init {
        retrofitInit()
        refreshData()
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                val repos = api.listRepos(name)
                response.value = StringBuilder().apply {
                    repos.forEach {
                        append(it.name)
                        append(" - ")
                        append(it.owner.login)
                        append("\n")
                    }
                }.toString()
                if (name in mutableMap) {}
                else { mutableMap[name] = response.value!! }
            } catch (e: Exception) {
                if (name in mutableMap) { response.value = mutableMap[name] }
                else { response.value = "Failed to connect to the server" }
            }
        }
    }

    private fun retrofitInit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        api = retrofit.create(RestApi::class.java)
    }
}
