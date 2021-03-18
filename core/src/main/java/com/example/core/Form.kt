package com.example.core

class Form {

    private val mParts = arrayListOf<BodyPart>()

    val isValid: Boolean get() = validate.isValid
    val errorMessage: String get() = validate.message

    val validate: Validate
        get() {
            for (mPart in mParts) {
                val validate = mPart.validate
                if (!validate.isValid) return validate
            }
            return Validate.ok()
        }

    operator fun plusAssign(part: BodyPart) {
        mParts.add(part)
    }

    fun build(): Map<String, Any> {
        return mParts.fold(HashMap()) { acc, bodyPart ->
            acc.putAll(bodyPart.build())
            acc
        }
    }
}

class Validate(val isValid: Boolean, val message: String) {
    companion object {
        fun ok() = Validate(true, "")
        fun fail(message: String) = Validate(false, message)
    }
}

interface BodyPart {
    val validate: Validate get() = Validate.ok()

    fun build(): Map<String, Any>
}