package com.billgaag.leboncoin.domain.usecases

interface BaseUseCase<I, O> {

    fun execute(input: I): O

    interface Inputless<O> : BaseUseCase<Unit, O> {

        fun execute(): O

        @Deprecated(
                message = "No input needed to execute this use case",
                replaceWith = ReplaceWith("execute()"),
                level = DeprecationLevel.ERROR
        )
        override fun execute(input: Unit): O = execute()

    }
}