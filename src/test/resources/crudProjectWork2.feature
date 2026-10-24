Feature: Project Management with Token Authentication
  Scenario: CRUD Project ProjectWork2c
    Given tengo acceso a Todo.ly con el usuario "ucbtask03@ucbtask03.com" y password "task03"
    # Create
    When send POST request "api/projects.json" with
    """
    {
      "Content":"ProjectWork2c"
    }
    """
    Then code should be 200
    And the "Content" should be "ProjectWork2c"
    And save "Id" in "ID"

    # Read
    When send GET request "api/projects/ID.json" with
    """
    """
    Then code should be 200
    And the "Content" should be "ProjectWork2c"

    # Update
   When send PUT request "api/projects/ID.json" with
    """
    {
      "Content":"ProjectWork2Updatec"
   }
    """
   Then code should be 200
   And the "Content" should be "ProjectWork2Updatec"

    # Delete
   When send DELETE request "api/projects/ID.json" with
   """
   """
   Then code should be 200
   And the "Content" should be "ProjectWork2Updatec"