package com.bahaa.github.data.network.interfaces

import org.json.JSONObject

interface ErrorResponse {

    fun onAlreadyLoggedIn()
    fun onNoInternet()
    fun onNotAuthorized()
    fun onNotAllowedMethod()
    fun onApiNotFound()
    fun onBadRequest(`object`: JSONObject)
    fun onServerSideError()
    fun onForbidden()
    fun onMaintenance()
}
