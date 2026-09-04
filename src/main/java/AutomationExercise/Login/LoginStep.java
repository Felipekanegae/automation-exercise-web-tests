package AutomationExercise.Login;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import massa.MassaExcel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginStep {

    private static final Logger log = LoggerFactory.getLogger(LoginStep.class);
    private LoginPage login;
    private WebDriver driver;
    private MassaExcel massa;
    private String ct;

    @Before
    public void iniciar(Scenario scenario) {

        String ct = scenario.getSourceTagNames()
                .stream()
                .filter(tag -> tag.startsWith("@CT"))
                .findFirst()
                .orElse("@CT1")
                .replace("@CT", "");

        WebDriverManager.chromedriver().setup();

        System.out.println("[TEST] Tags do cenário: " + scenario.getSourceTagNames());
        System.out.println("[TEST] CT usado: " + ct);

        driver = new ChromeDriver();
        driver.manage().window().maximize();

        massa = new MassaExcel();

        massa.carregarMassa(
                "src/test/resources/massa/Massa/automationExercise.xlsx",
                "automationExercise", ct);

        driver.get("https://automationexercise.com");

        login = new LoginPage(driver, massa);
    }

    @Given("que estou na pagina de login")
    public void que_estou_na_pagina_de_login() {
        login.acessarLogin();

    }

    @When("informo usuario e senha validos")
    public void informo_usuario_e_senha_validos() {
        login.realizarLogin();

    }

    @Then("o sistema realiza login com sucesso")
    public void o_sistema_realiza_login_com_sucesso() {
        login.validarLoginRealizado();

    }

    @Given("que realizei o login")
    public void que_realizei_o_login() {
        login.acessarLogin();

    }

    @When("eu realizo o logout")
    public void eu_realizo_o_logout() {
        login.realizarLogin();
        login.validarLoginRealizado();
    }

    @Then("o logout é realizado com sucesso")
    public void o_logout_é_realizado_com_sucesso() {
        login.realizarLogout();
        login.validarLogoutRealizado();

    }

    @When("eu coloco infomacoes invalidas")
    public void eu_coloco_infomacoes_invalidas() {
        login.realizarLogin();

    }

    @Then("o sistema informa que as informacoes estao incorretas")
    public void o_sistema_informa_que_as_informacoes_estao_incorretas() {
        login.mensagemEmailSenhaInvalido();

    }

    @Given("que estou na pagina de registrar")
    public void que_estou_na_pagina_de_registrar() {
        login.acessarLogin();

    }

    @When("eu coloco infomacoes ja registradas")
    public void eu_coloco_infomacoes_ja_registradas() {
        login.realizarNovoCadastro();

    }

    @Then("o sistema informa que as informacoes ja foram utilizadas")
    public void o_sistema_informa_que_as_informacoes_ja_foram_utilizadas() {
        login.mensagemEmailJaCadastrado();

    }


    @After
    public void finalizar() {
        if (driver != null) {
            driver.quit();

        }
    }

}