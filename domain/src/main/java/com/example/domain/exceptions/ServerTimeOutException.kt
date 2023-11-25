package com.example.domain.exceptions

import java.io.IOException

class ServerTimeOutException( serverMessage:Exception):IOException(serverMessage)