Feature: Joke API

  Scenario: Get a random joke
    Given the Joke API is running
    When I request a random joke with user "Richard"
    Then the response status should be 200
    And the response should contain the joke "What did the Dorito farmer say to the other Dorito farmer? Cool Ranch!"
    And the joke should be saved in the database for user "Richard"

  Scenario: Username validation failed
    Given the Joke API is running
    When I request a random joke with user "A"
    Then the response status should be 400
    And the error message should be "Value must have at least 3 characters"