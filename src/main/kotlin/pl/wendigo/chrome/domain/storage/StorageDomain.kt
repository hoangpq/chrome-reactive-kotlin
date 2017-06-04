package pl.wendigo.chrome.domain.storage

/**
 * StorageDomain represents remote debugger protocol domain.
 */
@pl.wendigo.chrome.Experimental class StorageDomain internal constructor(private val connectionRemote : pl.wendigo.chrome.DebuggerProtocol) {

	/**
	 * Clears storage for origin.
	 */
	 fun clearDataForOrigin(input : ClearDataForOriginRequest) : io.reactivex.Single<pl.wendigo.chrome.ResponseFrame> {
        return connectionRemote.runAndCaptureResponse("Storage.clearDataForOrigin", input, pl.wendigo.chrome.ResponseFrame::class.java).map {
            it.value()
        }
	}

  
    /**
     * Returns flowable capturing all Storage domains events.
     */
    fun events() : io.reactivex.Flowable<pl.wendigo.chrome.ProtocolEvent> {
        return connectionRemote.captureAllEvents().map { it.value() }.filter {
            it.protocolDomain() == "Storage"
        }
    }
}
/**
 * Represents requestFrame parameters that can be used with Storage.clearDataForOrigin method call.
 *
 * Clears storage for origin.
 */
data class ClearDataForOriginRequest (
    /**
     * Security origin.
     */
    val origin : String,

    /**
     * Comma separated origin names.
     */
    val storageTypes : String

)


