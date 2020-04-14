Feature: Ensure that every http request and response have correlation ID in headers for tracing purposes.

  Scenario: Create correlation ID if it doesn't exist in request headers and add it to that request and related response headers
    Given correlation ID does not exists in http request header
    When request is processed
    Then new correlation ID is created and set to request and response X-Correlation-ID headers

  Scenario Outline: Use existing correlation ID from request headers and add it to the response headers
    Given correlation ID <cid> exists in http request header <header_key>
    When request is processed
    Then correlation ID <cid> is set to request and response headers header <header_key>
    Examples:
    | cid                                    | header_key         |
    | "d6f99b55-6f6f-412b-9851-85b7e9eec4f5" | "X-Correlation-ID" |