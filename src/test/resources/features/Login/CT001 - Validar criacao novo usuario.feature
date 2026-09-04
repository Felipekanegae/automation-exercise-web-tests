Feature: Login

  @CT1
  Scenario: Validar login com sucesso

    Given que estou na pagina de login
    When informo usuario e senha validos
    Then o sistema realiza login com sucesso