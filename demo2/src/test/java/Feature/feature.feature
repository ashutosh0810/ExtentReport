Feature: Test the demo website
  Scenario: Test the google.com search engine
    Given User launch the url
    Then User enter the text and capture screeenshot post entering the text
    Then User click search button
    Then user close the browser
