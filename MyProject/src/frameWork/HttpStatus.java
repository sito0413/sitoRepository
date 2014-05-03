package frameWork;

public class HttpStatus {
	public final static int NOT_SET_000 = 0;
	public final static int CONTINUE_100 = 100;
	public final static int SWITCHING_PROTOCOLS_101 = 101;
	public final static int PROCESSING_102 = 102;
	
	public final static int OK_200 = 200;
	public final static int CREATED_201 = 201;
	public final static int ACCEPTED_202 = 202;
	public final static int NON_AUTHORITATIVE_INFORMATION_203 = 203;
	public final static int NO_CONTENT_204 = 204;
	public final static int RESET_CONTENT_205 = 205;
	public final static int PARTIAL_CONTENT_206 = 206;
	public final static int MULTI_STATUS_207 = 207;
	
	public final static int MULTIPLE_CHOICES_300 = 300;
	public final static int MOVED_PERMANENTLY_301 = 301;
	public final static int MOVED_TEMPORARILY_302 = 302;
	public final static int FOUND_302 = 302;
	public final static int SEE_OTHER_303 = 303;
	public final static int NOT_MODIFIED_304 = 304;
	public final static int USE_PROXY_305 = 305;
	public final static int TEMPORARY_REDIRECT_307 = 307;
	
	public final static int BAD_REQUEST_400 = 400;
	public final static int UNAUTHORIZED_401 = 401;
	public final static int PAYMENT_REQUIRED_402 = 402;
	public final static int FORBIDDEN_403 = 403;
	public final static int NOT_FOUND_404 = 404;
	public final static int METHOD_NOT_ALLOWED_405 = 405;
	public final static int NOT_ACCEPTABLE_406 = 406;
	public final static int PROXY_AUTHENTICATION_REQUIRED_407 = 407;
	public final static int REQUEST_TIMEOUT_408 = 408;
	public final static int CONFLICT_409 = 409;
	public final static int GONE_410 = 410;
	public final static int LENGTH_REQUIRED_411 = 411;
	public final static int PRECONDITION_FAILED_412 = 412;
	public final static int REQUEST_ENTITY_TOO_LARGE_413 = 413;
	public final static int REQUEST_URI_TOO_LONG_414 = 414;
	public final static int UNSUPPORTED_MEDIA_TYPE_415 = 415;
	public final static int REQUESTED_RANGE_NOT_SATISFIABLE_416 = 416;
	public final static int EXPECTATION_FAILED_417 = 417;
	public final static int UNPROCESSABLE_ENTITY_422 = 422;
	public final static int LOCKED_423 = 423;
	public final static int FAILED_DEPENDENCY_424 = 424;
	
	public final static int INTERNAL_SERVER_ERROR_500 = 500;
	public final static int NOT_IMPLEMENTED_501 = 501;
	public final static int BAD_GATEWAY_502 = 502;
	public final static int SERVICE_UNAVAILABLE_503 = 503;
	public final static int GATEWAY_TIMEOUT_504 = 504;
	public final static int HTTP_VERSION_NOT_SUPPORTED_505 = 505;
	public final static int INSUFFICIENT_STORAGE_507 = 507;
	
	public static final int MAX_CODE = 507;
	
	public static String getMessage(final int code) {
		switch ( code ) {
			case CONTINUE_100 :
				return "Continue";
			case SWITCHING_PROTOCOLS_101 :
				return "Switching Protocols";
			case PROCESSING_102 :
				return "Processing";
			case OK_200 :
				return "OK";
			case CREATED_201 :
				return "Created";
			case ACCEPTED_202 :
				return "Accepted";
			case NON_AUTHORITATIVE_INFORMATION_203 :
				return "Non Authoritative Information";
			case NO_CONTENT_204 :
				return "No Content";
			case RESET_CONTENT_205 :
				return "Reset Content";
			case PARTIAL_CONTENT_206 :
				return "Partial Content";
			case MULTI_STATUS_207 :
				return "Multi-Status";
			case MULTIPLE_CHOICES_300 :
				return "Multiple Choices";
			case MOVED_PERMANENTLY_301 :
				return "Moved Permanently";
			case FOUND_302 :
				return "Found";
			case SEE_OTHER_303 :
				return "See Other";
			case NOT_MODIFIED_304 :
				return "Not Modified";
			case USE_PROXY_305 :
				return "Use Proxy";
			case TEMPORARY_REDIRECT_307 :
				return "Temporary Redirect";
			case BAD_REQUEST_400 :
				return "Bad Request";
			case UNAUTHORIZED_401 :
				return "Unauthorized";
			case PAYMENT_REQUIRED_402 :
				return "Payment Required";
			case FORBIDDEN_403 :
				return "Forbidden";
			case NOT_FOUND_404 :
				return "Not Found";
			case METHOD_NOT_ALLOWED_405 :
				return "Method Not Allowed";
			case NOT_ACCEPTABLE_406 :
				return "Not Acceptable";
			case PROXY_AUTHENTICATION_REQUIRED_407 :
				return "Proxy Authentication Required";
			case REQUEST_TIMEOUT_408 :
				return "Request Timeout";
			case CONFLICT_409 :
				return "Conflict";
			case GONE_410 :
				return "Gone";
			case LENGTH_REQUIRED_411 :
				return "Length Required";
			case PRECONDITION_FAILED_412 :
				return "Precondition Failed";
			case REQUEST_ENTITY_TOO_LARGE_413 :
				return "Request Entity Too Large";
			case REQUEST_URI_TOO_LONG_414 :
				return "Request-URI Too Long";
			case UNSUPPORTED_MEDIA_TYPE_415 :
				return "Unsupported Media Type";
			case REQUESTED_RANGE_NOT_SATISFIABLE_416 :
				return "Requested Range Not Satisfiable";
			case EXPECTATION_FAILED_417 :
				return "Expectation Failed";
			case UNPROCESSABLE_ENTITY_422 :
				return "Unprocessable Entity";
			case LOCKED_423 :
				return "Locked";
			case FAILED_DEPENDENCY_424 :
				return "Failed Dependency";
			case INTERNAL_SERVER_ERROR_500 :
				return "Server Error";
			case NOT_IMPLEMENTED_501 :
				return "Not Implemented";
			case BAD_GATEWAY_502 :
				return "Bad Gateway";
			case SERVICE_UNAVAILABLE_503 :
				return "Service Unavailable";
			case GATEWAY_TIMEOUT_504 :
				return "Gateway Timeout";
			case HTTP_VERSION_NOT_SUPPORTED_505 :
				return "HTTP Version Not Supported";
			case INSUFFICIENT_STORAGE_507 :
				return "Insufficient Storage";
			default :
				return Integer.toString(code);
		}
	}
}
