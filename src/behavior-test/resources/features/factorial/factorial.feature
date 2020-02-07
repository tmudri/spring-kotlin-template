Feature: Create REST service which calculates and returns factorial number

  Scenario Outline:
    Given user needs to calculate factorial number
    When user calls endpoint with number <request_number>
    Then service returns calculated factorial number equal to <calculated_factorial_number>
    Examples:
      | request_number  | calculated_factorial_number |
      |               1 |                           1 |
      |               5 |                         120 |
      |               7 |                        5040 |