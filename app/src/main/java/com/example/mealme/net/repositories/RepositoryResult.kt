package com.example.mealme.net.repositories

/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

data class RepositoryResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): RepositoryResult<T> {
            return RepositoryResult(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String, data: T? = null): RepositoryResult<T> {
            return RepositoryResult(
                Status.ERROR,
                data,
                message
            )
        }

        fun <T> loading(data: T? = null): RepositoryResult<T> {
            return RepositoryResult(
                Status.LOADING,
                data,
                null
            )
        }
    }
}