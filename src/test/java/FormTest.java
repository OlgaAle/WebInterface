import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FormTest {

    @Test
    void ifFormIsCorrectShouldReturnSuccess() {
        {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[data-test-id=name] input").setValue("Иван Иванов");
            form.$("[data-test-id=phone] input").setValue("+79876543210");
            form.$("[data-test-id=agreement]").click();
            form.$("button").click();
            $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        }
    }

    @Test
    void ifNameLatinShouldFail() {
        {
            open("http://localhost:9999");
            SelenideElement form = $("form");
            form.$("[data-test-id=name] input").setValue("Ivan Ivanov");
            form.$("[data-test-id=phone] input").setValue("+79876543210");
            form.$("[data-test-id=agreement]").click();
            form.$("button").click();
            form.$("[class=input__sub]").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        }
    }
}
