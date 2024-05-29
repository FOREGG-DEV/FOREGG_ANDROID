package com.foregg.data.base

object StatusCode {
    const val ERROR = "ERROR400"
    const val ERROR_404 = "ERROR404"
    const val NETWORK_ERROR = "NETWORK_ERROR"

    object AUTH {
        const val USER_NEED_JOIN = "USER4002"
        const val NOT_CORRECT_SHARE_CODE = "USER4004"
    }

    object RECORD {
        const val NO_EXIST_SCHEDULE = "RECORD4001"
    }

    object LEDGER {
        const val NO_EXIST_LEDGER = "LEDGER4001"
    }
}