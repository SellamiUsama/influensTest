package com.example.influensapp.Data.Repo
enum class Status{
    LOADING,
    SUCCESS,
    ERROR
}
class Network(val status: Status ,val message: String) {
    companion object{
        val LOADING : Network
        val SUCCESS : Network
        val ERROR : Network
        val NO_MORE_SHOWS : Network

        init {
            LOADING = Network(Status.LOADING , "Waiting")
            SUCCESS = Network(Status.SUCCESS , "Success")
            ERROR = Network(Status.ERROR , "Oups! error, something went wrong")
            NO_MORE_SHOWS = Network(Status.ERROR , "Oups! you have reached the end of the list")
        }
    }

}