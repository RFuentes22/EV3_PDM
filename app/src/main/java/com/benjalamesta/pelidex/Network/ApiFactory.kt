package com.benjalamesta.pelidex.Network

object ApiFactory {

    val TOKEN_API = "8b0b11f6"
    private val authInterceptor = Interceptor {chain->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apikey",TOKEN_API)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val ombdClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(ombdClient)
        .baseUrl("http://www.omdbapi.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val ombdApi : OmbdApi = retrofit().create(OmbdApi::class.java)

}