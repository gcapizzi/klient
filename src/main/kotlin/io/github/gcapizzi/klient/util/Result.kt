package io.github.gcapizzi.klient.util

sealed class Result<out T> {
    abstract fun <V> map(f: (T) -> V): Result<V>
    abstract fun <V> andThen(f: (T) -> Result<V>): Result<V>
    abstract fun unwrap(): T

    class Ok<out T>(val value: T) : Result<T>() {
        override fun <V> map(f: (T) -> V): Result<V> = Ok(f(value))
        override fun <V> andThen(f: (T) -> Result<V>): Result<V> = f(value)
        override fun unwrap() = value
        override fun toString() = "Ok($value)"
        override fun equals(other: Any?): Boolean = other is Result.Ok<*> && other.value == value
    }

    class Error<out T>(val value: Throwable) : Result<T>() {
        override fun <V> map(f: (T) -> V): Result<V> = Error(value)
        override fun <V> andThen(f: (T) -> Result<V>): Result<V> = Error(value)
        override fun unwrap() = throw value
        override fun toString() = "Error($value)"
        override fun equals(other: Any?): Boolean = other is Result.Error<*> && other.value == value
    }

    companion object {
        fun <T> of(value: T?, errorValue: Throwable): Result<T> {
            if (value == null) {
                return Error(errorValue)
            } else {
                return Ok(value)
            }
        }
    }
}