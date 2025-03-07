package com.example.coinmvvm.data.remote.websocket

import com.example.coinmvvm.data.model.RequestTickerData
import com.example.coinmvvm.data.model.TickerData
import com.example.coinmvvm.util.Resource
import kotlinx.coroutines.flow.Flow

interface WebSocketService {
    suspend fun listenTickerSocket(requestTickerData: RequestTickerData): Flow<Resource<TickerData>>

    suspend fun stopTickerSocket()
}