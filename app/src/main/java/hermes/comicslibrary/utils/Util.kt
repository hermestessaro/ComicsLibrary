package hermes.comicslibrary.utils

import java.math.BigInteger
import java.security.MessageDigest

fun getHash(timestamp: String, privateKey: String, publicKey: String): String {
    val hash = timestamp + privateKey + publicKey
    val messageDigest = MessageDigest.getInstance("MD5")
    return BigInteger(1, messageDigest.digest(hash.toByteArray())).toString(16).padStart(32, '0')
}