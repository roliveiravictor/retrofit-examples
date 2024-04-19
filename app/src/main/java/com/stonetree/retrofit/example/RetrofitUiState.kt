package com.stonetree.retrofit.example

sealed interface RetrofitUiState {

    data object Loading :RetrofitUiState
    data object  Loaded :RetrofitUiState
}