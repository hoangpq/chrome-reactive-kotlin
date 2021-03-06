package pl.wendigo.chrome.domain.runtime

/**
 * Unique script identifier.
 */

typealias ScriptId = String

/**
 * Unique object identifier.
 */

typealias RemoteObjectId = String

/**
 * Primitive value which cannot be JSON-stringified.
 */
enum class UnserializableValue {
    @com.fasterxml.jackson.annotation.JsonProperty("Infinity") PLUS_INFINITY,
    @com.fasterxml.jackson.annotation.JsonProperty("NaN") NAN,
    @com.fasterxml.jackson.annotation.JsonProperty("-Infinity") MINUS_INFINITY,
    @com.fasterxml.jackson.annotation.JsonProperty("-0") ZERO;
}

/**
 * Mirror object referencing original JavaScript object.
 */

data class RemoteObject(
  /**
   * Object type.
   */
  val type : String,

  /**
   * Object subtype hint. Specified for <code>object</code> type values only.
   */
  val subtype : String? = null,

  /**
   * Object class (constructor) name. Specified for <code>object</code> type values only.
   */
  val className : String? = null,

  /**
   * Remote object value in case of primitive values or JSON values (if it was requested).
   */
  val value : Any? = null,

  /**
   * Primitive value which can not be JSON-stringified does not have <code>value</code>, but gets this property.
   */
  val unserializableValue : UnserializableValue? = null,

  /**
   * String representation of the object.
   */
  val description : String? = null,

  /**
   * Unique object identifier (for non-primitive values).
   */
  val objectId : RemoteObjectId? = null,

  /**
   * Preview containing abbreviated property values. Specified for <code>object</code> type values only.
   */
  @pl.wendigo.chrome.Experimental val preview : ObjectPreview? = null,

  /**
   *
   */
  @pl.wendigo.chrome.Experimental val customPreview : CustomPreview? = null
)

/**
 *
 */

data class CustomPreview(
  /**
   *
   */
  val header : String,

  /**
   *
   */
  val hasBody : Boolean,

  /**
   *
   */
  val formatterObjectId : RemoteObjectId,

  /**
   *
   */
  val bindRemoteObjectFunctionId : RemoteObjectId,

  /**
   *
   */
  val configObjectId : RemoteObjectId? = null
)

/**
 * Object containing abbreviated remote object value.
 */

data class ObjectPreview(
  /**
   * Object type.
   */
  val type : String,

  /**
   * Object subtype hint. Specified for <code>object</code> type values only.
   */
  val subtype : String? = null,

  /**
   * String representation of the object.
   */
  val description : String? = null,

  /**
   * True iff some of the properties or entries of the original object did not fit.
   */
  val overflow : Boolean,

  /**
   * List of the properties.
   */
  val properties : List<PropertyPreview>,

  /**
   * List of the entries. Specified for <code>map</code> and <code>set</code> subtype values only.
   */
  val entries : List<EntryPreview>? = null
)

/**
 *
 */

data class PropertyPreview(
  /**
   * Property name.
   */
  val name : String,

  /**
   * Object type. Accessor means that the property itself is an accessor property.
   */
  val type : String,

  /**
   * User-friendly property value string.
   */
  val value : String? = null,

  /**
   * Nested value preview.
   */
  val valuePreview : ObjectPreview? = null,

  /**
   * Object subtype hint. Specified for <code>object</code> type values only.
   */
  val subtype : String? = null
)

/**
 *
 */

data class EntryPreview(
  /**
   * Preview of the key. Specified for map-like collection entries.
   */
  val key : ObjectPreview? = null,

  /**
   * Preview of the value.
   */
  val value : ObjectPreview
)

/**
 * Object property descriptor.
 */

data class PropertyDescriptor(
  /**
   * Property name or symbol description.
   */
  val name : String,

  /**
   * The value associated with the property.
   */
  val value : RemoteObject? = null,

  /**
   * True if the value associated with the property may be changed (data descriptors only).
   */
  val writable : Boolean? = null,

  /**
   * A function which serves as a getter for the property, or <code>undefined</code> if there is no getter (accessor descriptors only).
   */
  val get : RemoteObject? = null,

  /**
   * A function which serves as a setter for the property, or <code>undefined</code> if there is no setter (accessor descriptors only).
   */
  val set : RemoteObject? = null,

  /**
   * True if the type of this property descriptor may be changed and if the property may be deleted from the corresponding object.
   */
  val configurable : Boolean,

  /**
   * True if this property shows up during enumeration of the properties on the corresponding object.
   */
  val enumerable : Boolean,

  /**
   * True if the result was thrown during the evaluation.
   */
  val wasThrown : Boolean? = null,

  /**
   * True if the property is owned for the object.
   */
  val isOwn : Boolean? = null,

  /**
   * Property symbol object, if the property is of the <code>symbol</code> type.
   */
  val symbol : RemoteObject? = null
)

/**
 * Object internal property descriptor. This property isn&apos;t normally visible in JavaScript code.
 */

data class InternalPropertyDescriptor(
  /**
   * Conventional property name.
   */
  val name : String,

  /**
   * The value associated with the property.
   */
  val value : RemoteObject? = null
)

/**
 * Represents function call argument. Either remote object id &lt;code&gt;objectId&lt;/code&gt;, primitive &lt;code&gt;value&lt;/code&gt;, unserializable primitive value or neither of (for undefined) them should be specified.
 */

data class CallArgument(
  /**
   * Primitive value or serializable javascript object.
   */
  val value : Any? = null,

  /**
   * Primitive value which can not be JSON-stringified.
   */
  val unserializableValue : UnserializableValue? = null,

  /**
   * Remote object handle.
   */
  val objectId : RemoteObjectId? = null
)

/**
 * Id of an execution context.
 */

typealias ExecutionContextId = Int

/**
 * Description of an isolated world.
 */

data class ExecutionContextDescription(
  /**
   * Unique id of the execution context. It can be used to specify in which execution context script evaluation should be performed.
   */
  val id : ExecutionContextId,

  /**
   * Execution context origin.
   */
  val origin : String,

  /**
   * Human readable name describing given context.
   */
  val name : String,

  /**
   * Embedder-specific auxiliary data.
   */
  val auxData : String? = null
)

/**
 * Detailed information about exception (or error) that was thrown during script compilation or execution.
 */

data class ExceptionDetails(
  /**
   * Exception id.
   */
  val exceptionId : Int,

  /**
   * Exception text, which should be used together with exception object when available.
   */
  val text : String,

  /**
   * Line number of the exception location (0-based).
   */
  val lineNumber : Int,

  /**
   * Column number of the exception location (0-based).
   */
  val columnNumber : Int,

  /**
   * Script ID of the exception location.
   */
  val scriptId : ScriptId? = null,

  /**
   * URL of the exception location, to be used when the script was not reported.
   */
  val url : String? = null,

  /**
   * JavaScript stack trace if available.
   */
  val stackTrace : StackTrace? = null,

  /**
   * Exception object if available.
   */
  val exception : RemoteObject? = null,

  /**
   * Identifier of the context where exception happened.
   */
  val executionContextId : ExecutionContextId? = null
)

/**
 * Number of milliseconds since epoch.
 */

typealias Timestamp = Double

/**
 * Stack entry for runtime errors and assertions.
 */

data class CallFrame(
  /**
   * JavaScript function name.
   */
  val functionName : String,

  /**
   * JavaScript script id.
   */
  val scriptId : ScriptId,

  /**
   * JavaScript script name or url.
   */
  val url : String,

  /**
   * JavaScript script line number (0-based).
   */
  val lineNumber : Int,

  /**
   * JavaScript script column number (0-based).
   */
  val columnNumber : Int
)

/**
 * Call frames for assertions or error messages.
 */

data class StackTrace(
  /**
   * String label of this stack trace. For async traces this may be a name of the function that initiated the async call.
   */
  val description : String? = null,

  /**
   * JavaScript function name.
   */
  val callFrames : List<CallFrame>,

  /**
   * Asynchronous JavaScript stack trace that preceded this stack, if available.
   */
  val parent : StackTrace? = null,

  /**
   * Creation frame of the Promise which produced the next synchronous trace when resolved, if available.
   */
  @pl.wendigo.chrome.Experimental val promiseCreationFrame : CallFrame? = null
)

/**
 *
 */

typealias AsyncTaskId = String

