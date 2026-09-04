package AutomationExercise.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import testData.ExcelTestData;


import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private ExcelTestData testData;

    public LoginPage(WebDriver driver, ExcelTestData massa) {

        this.driver = driver;
        this.testData = massa;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);

    }

    // ELEMENTOS
    @FindBy(xpath = "//*[text()=\" Signup / Login\"]")
    private WebElement menuLogin;

    @FindBy(xpath = "/html/body/section/div/div/div[1]/div/form/input[2]")
    private WebElement cmpEmailLogin;

    @FindBy(name = "password")
    private WebElement cmpPasswordLogin;

    @FindBy(name = "name")
    private WebElement cmpNomeNovoLogin;

    @FindBy(xpath = "/html/body/section/div/div/div[3]/div/form/input[3]")
    private WebElement cmpEmailNovoLogin;

    @FindBy(name = "name")
    private WebElement cmpNomeContato;

    @FindBy(name = "email")
    private WebElement cmpEmailContato;

    @FindBy(name = "subject")
    private WebElement cmpSubjectContato;

    @FindBy(name = "message")
    private WebElement cmpMessageContato;

    @FindBy(xpath = "/html/body/section/div/div/div[1]/div/form/button")
    private WebElement bntLogin;

    @FindBy(xpath = "/html/body/header/div/div/div/div[2]/div/ul/li[4]/a")
    private WebElement btnLogout;

    @FindBy(xpath = "/html/body/section/div/div/div[3]/div/form/button")
    private WebElement btnRegitrar;

    @FindBy(xpath = "//*[text()=\" Contact us\"]")
    private WebElement btnContactUs;

    @FindBy(xpath = "/html/body/header/div/div/div/div[2]/div/ul/li[10]/a")
    private WebElement lblLogged;

    @FindBy(xpath = "/html/body/section/div/div/div[1]/div/form/p")
    private WebElement msgEmailInvalido;

    @FindBy(xpath = "/html/body/section/div/div/div[3]/div/form/p")
    private WebElement msgEmailCadastrado;


    //===ACOES===

    public void acessarLogin() {
        wait.until(ExpectedConditions.visibilityOf(menuLogin));
        menuLogin.click();

    }

    public void realizarLogin() {
        wait.until(ExpectedConditions.visibilityOf(cmpEmailLogin));

        String email = testData.getStringOf("EMAIL");
        String senha = testData.getStringOf("SENHA");
        System.out.println("[TEST] O EMAIL E SENHA DO TESTES: " + email);
        System.out.println("[TEST] A SENHA DO TESTES: " + senha);

        cmpEmailLogin.sendKeys(email);
        cmpPasswordLogin.sendKeys(senha);
        bntLogin.click();

    }

    public void realizarNovoCadastro() {
        wait.until(ExpectedConditions.visibilityOf(cmpNomeNovoLogin));

        String email = testData.getStringOf("EMAIL");
        String nome = testData.getStringOf("NOME");
        System.out.println("[TEST] O EMAIL É: " + email);
        System.out.println("[TEST] O NOME É: " + nome);

        cmpNomeNovoLogin.sendKeys(testData.getStringOf("NOME"));
        cmpEmailNovoLogin.sendKeys(email);

        btnRegitrar.click();

    }

    public void validarLoginRealizado(){
        wait.until(ExpectedConditions.visibilityOf(lblLogged));
        wait.until(ExpectedConditions.visibilityOf(btnLogout));

        System.out.println("[TEST] O LOGIN FOI REALIZADO COM SUCESSO!!!");

    }

    public void realizarLogout(){
        wait.until(ExpectedConditions.visibilityOf(btnLogout));
        btnLogout.click();

    }

    public void validarLogoutRealizado(){
        wait.until(ExpectedConditions.visibilityOf(cmpEmailLogin));
        wait.until(ExpectedConditions.visibilityOf(cmpPasswordLogin));

        System.out.println("[TEST] LOGOUT FOI REALIZADO COM SUCESSO!!!");
    }


    //===MENSAGENS==

    public void mensagemEmailSenhaInvalido(){
        wait.until(ExpectedConditions.visibilityOf(msgEmailInvalido));
        String msg = msgEmailInvalido.getText();

        System.out.println("[TEST] " + msg);

    }
    public void mensagemEmailJaCadastrado(){
        wait.until(ExpectedConditions.visibilityOf(msgEmailCadastrado));
        String msg = msgEmailCadastrado.getText();

        Assert.assertTrue(msg.contains("Email Address already exist!"));
        System.out.println("[TEST] " + msg);

    }

}