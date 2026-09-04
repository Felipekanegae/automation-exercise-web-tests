Feature: Login


  @CT4
  Scenario: Validar email ja registrado

    Given que estou na pagina de registrar
    When eu coloco infomacoes ja registradas
    Then o sistema informa que as informacoes ja foram utilizadas
