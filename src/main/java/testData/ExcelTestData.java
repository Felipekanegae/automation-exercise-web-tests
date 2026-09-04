package testData;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelTestData {

    private final Map<String, String> dados = new HashMap<>();

    public void carregarMassa(String caminhoArquivo, String nomeAba, String ct) {

        try (InputStream input = new FileInputStream(caminhoArquivo);
             Workbook workbook = WorkbookFactory.create(input)) {

            Sheet sheet = workbook.getSheet(nomeAba);

            if (sheet == null) {
                throw new RuntimeException("Aba não encontrada: " + nomeAba);
            }

            Row cabecalho = sheet.getRow(0);

            if (cabecalho == null) {
                throw new RuntimeException("Cabeçalho não encontrado na planilha.");
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row linha = sheet.getRow(i);

                if (linha == null) {
                    continue;
                }

                String valorCt = getValorCelula(linha.getCell(0));

                if (valorCt.equals(ct)) {

                    for (int coluna = 0; coluna < cabecalho.getLastCellNum(); coluna++) {

                        String nomeColuna = getValorCelula(cabecalho.getCell(coluna));
                        String valorCelula = getValorCelula(linha.getCell(coluna));

                        dados.put(nomeColuna, valorCelula);
                    }

                    return;
                }
            }

            throw new RuntimeException("CT não encontrado na planilha: " + ct);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler a massa Excel: " + e.getMessage(), e);
        }
    }

    public String getStringOf(String coluna) {

        String valor = dados.get(coluna);

        if (valor == null) {
            throw new RuntimeException("Coluna não encontrada na massa: " + coluna);
        }

        return valor;
    }

    private String getValorCelula(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell).trim();
    }
}