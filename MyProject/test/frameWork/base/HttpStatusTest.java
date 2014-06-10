package frameWork.base;

import static org.junit.Assert.*;

import org.junit.Test;

public class HttpStatusTest {
	
	@Test
	public void test() {
		assertTrue(HttpStatus.getMessage(HttpStatus.NOT_SET_000).equals("0"));
		assertFalse(HttpStatus.getMessage(HttpStatus.CONTINUE_100).equals("100"));
		assertFalse(HttpStatus.getMessage(HttpStatus.SWITCHING_PROTOCOLS_101).equals("101"));
		assertFalse(HttpStatus.getMessage(HttpStatus.PROCESSING_102).equals("102"));
		
		assertFalse(HttpStatus.getMessage(HttpStatus.OK_200).equals("200"));
		assertFalse(HttpStatus.getMessage(HttpStatus.CREATED_201).equals("201"));
		assertFalse(HttpStatus.getMessage(HttpStatus.ACCEPTED_202).equals("202"));
		assertFalse(HttpStatus.getMessage(HttpStatus.NON_AUTHORITATIVE_INFORMATION_203).equals("203"));
		assertFalse(HttpStatus.getMessage(HttpStatus.NO_CONTENT_204).equals("204"));
		assertFalse(HttpStatus.getMessage(HttpStatus.RESET_CONTENT_205).equals("205"));
		assertFalse(HttpStatus.getMessage(HttpStatus.PARTIAL_CONTENT_206).equals("206"));
		assertFalse(HttpStatus.getMessage(HttpStatus.MULTI_STATUS_207).equals("207"));
		
		assertFalse(HttpStatus.getMessage(HttpStatus.MULTIPLE_CHOICES_300).equals("300"));
		assertFalse(HttpStatus.getMessage(HttpStatus.MOVED_PERMANENTLY_301).equals("301"));
		assertFalse(HttpStatus.getMessage(HttpStatus.MOVED_TEMPORARILY_302).equals("302"));
		assertFalse(HttpStatus.getMessage(HttpStatus.FOUND_302).equals("302"));
		assertFalse(HttpStatus.getMessage(HttpStatus.SEE_OTHER_303).equals("303"));
		assertFalse(HttpStatus.getMessage(HttpStatus.NOT_MODIFIED_304).equals("304"));
		assertFalse(HttpStatus.getMessage(HttpStatus.USE_PROXY_305).equals("305"));
		assertFalse(HttpStatus.getMessage(HttpStatus.TEMPORARY_REDIRECT_307).equals("307"));
		
		assertFalse(HttpStatus.getMessage(HttpStatus.BAD_REQUEST_400).equals("400"));
		assertFalse(HttpStatus.getMessage(HttpStatus.UNAUTHORIZED_401).equals("401"));
		assertFalse(HttpStatus.getMessage(HttpStatus.PAYMENT_REQUIRED_402).equals("402"));
		assertFalse(HttpStatus.getMessage(HttpStatus.FORBIDDEN_403).equals("403"));
		assertFalse(HttpStatus.getMessage(HttpStatus.NOT_FOUND_404).equals("404"));
		assertFalse(HttpStatus.getMessage(HttpStatus.METHOD_NOT_ALLOWED_405).equals("405"));
		assertFalse(HttpStatus.getMessage(HttpStatus.NOT_ACCEPTABLE_406).equals("406"));
		assertFalse(HttpStatus.getMessage(HttpStatus.PROXY_AUTHENTICATION_REQUIRED_407).equals("407"));
		assertFalse(HttpStatus.getMessage(HttpStatus.REQUEST_TIMEOUT_408).equals("408"));
		assertFalse(HttpStatus.getMessage(HttpStatus.CONFLICT_409).equals("409"));
		assertFalse(HttpStatus.getMessage(HttpStatus.GONE_410).equals("410"));
		assertFalse(HttpStatus.getMessage(HttpStatus.LENGTH_REQUIRED_411).equals("411"));
		assertFalse(HttpStatus.getMessage(HttpStatus.PRECONDITION_FAILED_412).equals("412"));
		assertFalse(HttpStatus.getMessage(HttpStatus.REQUEST_ENTITY_TOO_LARGE_413).equals("413"));
		assertFalse(HttpStatus.getMessage(HttpStatus.REQUEST_URI_TOO_LONG_414).equals("414"));
		assertFalse(HttpStatus.getMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE_415).equals("415"));
		assertFalse(HttpStatus.getMessage(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE_416).equals("416"));
		assertFalse(HttpStatus.getMessage(HttpStatus.EXPECTATION_FAILED_417).equals("417"));
		assertFalse(HttpStatus.getMessage(HttpStatus.UNPROCESSABLE_ENTITY_422).equals("422"));
		assertFalse(HttpStatus.getMessage(HttpStatus.LOCKED_423).equals("423"));
		assertFalse(HttpStatus.getMessage(HttpStatus.FAILED_DEPENDENCY_424).equals("424"));
		
		assertFalse(HttpStatus.getMessage(HttpStatus.INTERNAL_SERVER_ERROR_500).equals("500"));
		assertFalse(HttpStatus.getMessage(HttpStatus.NOT_IMPLEMENTED_501).equals("501"));
		assertFalse(HttpStatus.getMessage(HttpStatus.BAD_GATEWAY_502).equals("502"));
		assertFalse(HttpStatus.getMessage(HttpStatus.SERVICE_UNAVAILABLE_503).equals("503"));
		assertFalse(HttpStatus.getMessage(HttpStatus.GATEWAY_TIMEOUT_504).equals("504"));
		assertFalse(HttpStatus.getMessage(HttpStatus.HTTP_VERSION_NOT_SUPPORTED_505).equals("505"));
		assertFalse(HttpStatus.getMessage(HttpStatus.INSUFFICIENT_STORAGE_507).equals("507"));
		assertTrue(HttpStatus.getMessage(508).equals("508"));
	}
}
