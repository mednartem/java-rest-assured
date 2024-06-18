@rest
Feature: Rest Api

  @rest1
  Scenario: Validation api
    Given I get candidates via api
#    Given I get one candidate with id: 6
#    Given I create candidate


#  Review recording and files what we created in class - rest.feature, RestStepDefs.java, rest_data.yml, TestDataManager.java changes. See files attached here.
#  Recreate same setup on your computer for a position and run it successfully
#  Implement Create and Verify Candidate - same as we did for a position.
#  For now verification that created candidate id exists is enough (you call GET by id after POST for it).
#  Use getTimestamp() to ensure candidate email is unique each time you run the test.
  @rest2
  Scenario: Validation other api
    Given I create and validate candidate
