package uz.yuzka.a100kadmin.di

open class RemoteException(message: String, var remoteMessage: String? = null, code: Int? = null) :
    Exception(message)

class NetworkException(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)


class GlobalException(message: String, remoteMessage: String? = null, val statusCode: Int) :
    RemoteException(message, remoteMessage, code = statusCode)


class TokenWrongException(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

class TokenNotProvidedException(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

class TokenExpiredException(message: String = "Token ekirgan qaytadan login qiling") :
    RemoteException(message, message)

class NeedPassChangeException(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

class WrongVerificationCodeException(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

class UnknownErrorException(message: String, code: Int?, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

class NotEnoughException(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

class ServiceUnavailable(message: String, remoteMessage: String? = null) :
    RemoteException(message, remoteMessage)

