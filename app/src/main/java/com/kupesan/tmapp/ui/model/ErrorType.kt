package com.kupesan.tmapp.ui.model

sealed class ErrorType {
    object None : ErrorType()
    object Generic : ErrorType()
    object Offline : ErrorType()
}
