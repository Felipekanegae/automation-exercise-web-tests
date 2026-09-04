Feature: Login


  @CT1
  Scenario: Validar contatar suporte

    Given que estou na pagina de contatar o suporte
    When eu preencho as informacoes
    Then a mensagem e enviada com sucesso
