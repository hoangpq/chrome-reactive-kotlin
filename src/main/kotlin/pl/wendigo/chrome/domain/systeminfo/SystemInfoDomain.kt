package pl.wendigo.chrome.domain.systeminfo

/**
 * The SystemInfo domain defines methods and events for querying low-level system information.
 */
class SystemInfoDomain internal constructor(private val connectionRemote : pl.wendigo.chrome.DebuggerProtocol) {
    /**
     * Returns information about the system.
     */
    fun getInfo() : io.reactivex.Single<GetInfoResponse> {
        return connectionRemote.runAndCaptureResponse("SystemInfo.getInfo", null, GetInfoResponse::class.java).map {
            it.value()
        }
    }

    /**
     * Returns flowable capturing all SystemInfo domains events.
     */
    fun events() : io.reactivex.Flowable<pl.wendigo.chrome.ProtocolEvent> {
        return connectionRemote.captureAllEvents().map { it.value() }.filter {
            it.protocolDomain() == "SystemInfo"
        }
    }
}

/**
 * Represents response frame for SystemInfo.getInfo method call.
 *
 * Returns information about the system.
 */
data class GetInfoResponse(
  /**
   * Information about the GPUs on the system.
   */
  val gpu : GPUInfo,

  /**
   * A platform-dependent description of the model of the machine. On Mac OS, this is, for example, 'MacBookPro'. Will be the empty string if not supported.
   */
  val modelName : String,

  /**
   * A platform-dependent description of the version of the machine. On Mac OS, this is, for example, '10.1'. Will be the empty string if not supported.
   */
  val modelVersion : String,

  /**
   * The command line string used to launch the browser. Will be the empty string if not supported.
   */
  val commandLine : String

)

