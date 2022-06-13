package tests;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ParamsTest {

    @ValueSource(strings = {"Утюги", "Пылесосы бытовые"})
    @ParameterizedTest
    void pleerTest1(String value){
        Selenide.open("https://www.pleer.ru");
        $(".header-block-phone").parent().shouldHave(text("8 (495) 775-04-75"));
        $("[tabindex='3']").click();
        $("[tabindex='3']").setValue(value).pressEnter();
        $(".address_bar .grey").shouldHave(text(value));
    }

    @CsvSource(value = {
        "Утюги, Уход за одеждой и обувью",
        "Пылесосы бытовые, Чистота и порядок"
    })
    @ParameterizedTest(name = "При поиске в плеер.ру по запросу {0} в результатах отображается текст {1}")
    void pleerTest2(String searchData, String expectedData){
        Selenide.open("https://www.pleer.ru");
        $(".header-block-phone").parent().shouldHave(text("8 (495) 775-04-75"));
        $("[tabindex='3']").click();
        $("[tabindex='3']").setValue(searchData).pressEnter();
        $(".catalog_tree_title").parent().shouldHave(text(expectedData));
    }

    @EnumSource(EnumsParams.class)
    @ParameterizedTest()
    void pleerTest3(EnumsParams enumsParams){
        Selenide.open("https://www.pleer.ru");
        $(".header-block-phone").parent().shouldHave(text("8 (495) 775-04-75"));
        $("[tabindex='3']").click();
        $("[tabindex='3']").setValue(enumsParams.technic).pressEnter();
        $(".catalog_tree_title").parent().shouldHave(text(enumsParams.technic));
    }

    static Stream<Arguments> pleerTestComplexVeryProvider(){
        return Stream.of(
                Arguments.of("Утюги", List.of("Уход")),
                Arguments.of("Пылесосы бытовые", List.of("Чистота"))
        );
    }

    @MethodSource(value = "pleerTestComplexVeryProvider")
    @ParameterizedTest(name = "При поиске в плеер.ру по запросу {0} в результатах отображается текст {1}")
    void pleerTestComplex(String searchData, List<String> expectedResult) {
        Selenide.open("https://www.pleer.ru");
        $(".header-block-phone").parent().shouldHave(text("8 (495) 775-04-75"));
        $("[tabindex='3']").click();
        $("[tabindex='3']").setValue(searchData).pressEnter();
        $$(".catalog_tree_title").shouldHave(CollectionCondition.texts(expectedResult));
    }
}
