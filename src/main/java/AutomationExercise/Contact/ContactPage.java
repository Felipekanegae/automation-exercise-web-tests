package AutomationExercise.Contact;

import massa.MassaExcel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {

    private WebDriver driver;
    private MassaExcel massa;
    private String ct;

    public ContactPage(WebDriver driver, MassaExcel massa) {

        this.driver = driver;
        this.massa = massa;
        PageFactory.initElements(driver, this);

    }

    // ELEMENTOS

    @FindBy(name = "name")
    private WebElement cmpNomeContato;

    @FindBy(name = "email")
    private WebElement cmpEmailContato;

    @FindBy(name = "subject")
    private WebElement cmpSubjectContato;

    @FindBy(name = "message")
    private WebElement cmpMessageContato;


    @FindBy(xpath = "//*[text()=\" Contact us\"]")
    private WebElement btnContactUs;

    //===AÇÕES===
    public void enviarMensagemContato(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(btnContactUs));

        btnContactUs.click();
        preencherMensagemContato();
    }

    //===PREENCHIMENTO DE CAMPOS===

    private void preencherMensagemContato(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(cmpNomeContato));

        String nome = massa.getStringOf("NOME");
        String email = massa.getStringOf("EMAIL");
        String assunto = massa.getStringOf("SUBJECT");
        String mensagem = massa.getStringOf("MESSAGE");

        System.out.println("[TEST] O NOME DO TESTE: " + nome);
        System.out.println("[TEST] O EMAIL DO TESTE: " + email);
        System.out.println("[TEST] O ASSUNTO DO TESTE: " + assunto);
        System.out.println("[TEST] A MENSAGEM DO TESTE: " + mensagem);

        cmpNomeContato.sendKeys(nome);
        cmpEmailContato.sendKeys(email);
        cmpSubjectContato.sendKeys(assunto);
        cmpMessageContato.sendKeys(mensagem);

    }

}