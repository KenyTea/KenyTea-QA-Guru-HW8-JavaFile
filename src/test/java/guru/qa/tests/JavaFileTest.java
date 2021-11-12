package guru.qa.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.text.AbstractDocument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaFileTest {

    private String UPLOADURL = "https://demoqa.com/upload-download";
    private String FILENAME = "Pictures.PNG";
    private SelenideElement uploadFile = $("#uploadFile");
    private SelenideElement uploadedFilePath = $("#uploadedFilePath");

    @Test
    @DisplayName("Загрузка файла по относительному пути")
    public void uploadPicturesAndCheckPicturesName() {
        open(UPLOADURL); // Открываем Браузер
        uploadFile.uploadFromClasspath(FILENAME); // Загружаем рисунов из resources
        uploadedFilePath.shouldHave(Condition.text(FILENAME)); // Проверяем наличие загруженного рисунка
    }


    private String urlRew = "https://github.com/qa-guru/allure-notifications/blob/master/README.md";
    private SelenideElement exemlpeRew = $("#raw-url");

    @Test
    @DisplayName("Скачивание текстового файла и проверка его содержимого")
    public void downloadFileAndCheckWhatInside() throws IOException {
        open(urlRew);
        File raw = exemlpeRew.download(); // Присвиваем в переменную типа File скаченную переменную типа SelenideElement
        String fileContent = IOUtils.toString(new FileReader(raw)); // Читаем полученную переменную типа File утилитарным класс IOUtils, который помогает быстрее обрабатывать поток данных и присваиваем в переменную типа String
        assertTrue(fileContent.contains("<h3>Languages:</h3>")); // Проверяем наличие текста в файле
    }


    private String pdfUrl = "http://www.24copy.ru/skachat-prajjs.html";
    private SelenideElement pdfElement = $(byText("Общий прайс-лист на полиграфию.pdf"));

    @Test
    @DisplayName("Скачивание PDF файла")
    public void downloadPdfFile() throws IOException {
        open(pdfUrl); // Открываем URL
        File pdf = pdfElement.download(); // Присвиваем в переменную типа File скаченную переменную типа SelenideElement
        PDF parPdf = new PDF(pdf); // Создаём объект от класса PDF и скармливаем ему File
        Assertions.assertEquals(23, parPdf.numberOfPages);// Проверяем на кол-во страниц
    }


    private SelenideElement xlsElement = $(byText("Общий прайс-лист на полиграфию.xls"));

    @Test
    @DisplayName("Скачивание XLS файла")
    public void downloadXlsFile() throws FileNotFoundException {
        open(pdfUrl);
        File xls = xlsElement.download();
        XLS parsXls = new XLS(xls);

        boolean chekXls = parsXls.excel
                .getSheetAt(1)
                .getRow(2)
                .getCell(0)
                .getStringCellValue()
                .contains("TIFF ");

        assertTrue(chekXls);
    }


}
