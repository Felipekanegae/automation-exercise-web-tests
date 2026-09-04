Feature: Login


  @CT3
  Scenario: Validar falha login

    Given que estou na pagina de login
    When eu coloco infomacoes invalidas
    Then o sistema informa que as informacoes estao incorretas
