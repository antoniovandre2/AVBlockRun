// Projeto Mathematical Ramblings (mathematicalramblings.blogspot.com).

// Biblioteca Java pertencente a Antonio Vandré Pedrosa Furtunato Gomes (bit.ly/antoniovandre_legadoontologico).

// Dependências: JDK19.

// Licença de uso: Atribuição-NãoComercial-CompartilhaIgual (CC BY-NC-SA).

// Última atualização: 01-11-2023.

// Início escopo desenvolvido por Antonio Vandré Pedrosa Furtunato Gomes.

package AntonioVandre;

public class AntonioVandre
    {
    public static long Versao = 20231101;

    public static String MensagemErro = "Erro do Java (não meu) ou entrada inválida.";

    public static String MensagemErroContagemParametros = "Número incorreto de parâmetros.";

    public static String MensagemErroNaoNumero = "Um argumento não é número.";

    public static double MaximoValorReal = Double.MAX_VALUE;

    public static String MensagemErroNumeroGrandeDemais = "Um dos números de módulo grande demais para ser trabalhado no Java.";

    public static String StringDivisorLinhas = "\n_____\n";

    // Verdade se as strings são iguais.

    public static boolean CompararStrings(String s1, String s2)
        {
        int t1 = s1.length();
        int t2 = s2.length();

        if (t1 != t2) return false;

        for(int i = 0; i < t1; i++)
            if (! (s1.charAt(i) == s2.charAt(i))) return false;

        return true;
        }

    // Verdade se a segunda string está presente na primeira.

    public static boolean StringPresente(String s1, String s2)
        {
        int t1 = s1.length();
        int t2 = s2.length();

        for(int i = 0; i <= t1 - t2; i++)
            if (s1.charAt(i) == s2.charAt(0))
                {
                int contador = 0;

                for (int j = 0; j < t2; j++)
                    if (s1.charAt(i + j) == s2.charAt(j)) contador++;

                if (contador == t2) return true;
                }

        return false;
        }

    // Verdade se a string é um número real.

    public static boolean NumeroReal(String strNum)
        {
        if (strNum == null) {return false;}

        try
            {
            double d = Double.parseDouble(strNum);
            }
        catch (NumberFormatException nfe) {return false;}

        return true;
        }

    // Verdade se a string é um número real positivo.

    public static boolean NumeroRealPositivo(String strNum)
        {
        if (! AntonioVandre.NumeroReal(strNum)) {return false;}

        if (Double.parseDouble(strNum) <= 0) {return false;}

        return true;
        }

    // Verdade se a string é um número real não negativo.

    public static boolean NumeroRealNaoNegativo(String strNum)
        {
        if (! AntonioVandre.NumeroReal(strNum)) {return false;}

        if (Double.parseDouble(strNum) < 0) {return false;}

        return true;
        }

    // Verdade se a string é um número real negativo.

    public static boolean NumeroRealNegativo(String strNum)
        {
        if (! AntonioVandre.NumeroReal(strNum)) {return false;}

        if (Double.parseDouble(strNum) >= 0) {return false;}

        return true;
        }

    // Verdade se a string é um número real não positivo.

    public static boolean NumeroRealNaoPositivo(String strNum)
        {
        if (! AntonioVandre.NumeroReal(strNum)) {return false;}

        if (Double.parseDouble(strNum) > 0) {return false;}

        return true;
        }

    // Verdade se a string é um número inteiro.

    public static boolean NumeroInteiro(String strNum)
        {
        if (strNum == null) {return false;}

        try
            {
            int d = Integer.parseInt(strNum);
            }
        catch (NumberFormatException nfe) {return false;}

        return true;
        }

    // Verdade se a string é um número inteiro negativo.

    public static boolean NumeroInteiroNegativo(String strNum)
        {
        if (! AntonioVandre.NumeroInteiro(strNum)) {return false;}

        if (Integer.parseInt(strNum) >= 0) {return false;}

        return true;
        }

    // Verdade se a string é um número natural.

    public static boolean NumeroNatural(String strNum)
        {
        if (! AntonioVandre.NumeroInteiro(strNum)) {return false;}

        if (Integer.parseInt(strNum) < 0) {return false;}

        return true;
        }

    // Verdade se a string é um número natural positivo.

    public static boolean NumeroNaturalPositivo(String strNum)
        {
        if (! AntonioVandre.NumeroNatural(strNum)) {return false;}

        if (Integer.parseInt(strNum) == 0) {return false;}

        return true;
        }

    // Verdade se a string é um número inteiro long.

    public static boolean NumeroInteiroLong(String strNum)
        {
        if (strNum == null) {return false;}

        try
            {
            long d = Long.parseLong(strNum);
            }
        catch (NumberFormatException nfe) {return false;}

        return true;
        }

    // Verdade se a string é um número inteiro long negativo.

    public static boolean NumeroInteiroLongNegativo(String strNum)
        {
        if (! AntonioVandre.NumeroInteiroLong(strNum)) {return false;}

        if (Long.parseLong(strNum) >= 0) {return false;}

        return true;
        }

    // Verdade se a string é um número natural long.

    public static boolean NumeroNaturalLong(String strNum)
        {
        if (! AntonioVandre.NumeroInteiroLong(strNum)) {return false;}

        if (Long.parseLong(strNum) < 0) {return false;}

        return true;
        }

    // Verdade se a string é um número natural long positivo.

    public static boolean NumeroNaturalPositivoLong(String strNum)
        {
        if (! AntonioVandre.NumeroNaturalLong(strNum)) {return false;}

        if (Long.parseLong(strNum) == 0) {return false;}

        return true;
        }

    // Retorna apenas os números de uma string.

    public static String SoNumeros(String str)
        {
        int t = str.length();
        String r = "";

        for(int i = 0; i < t; i++)
            if ((str.charAt(i) == '0') || (str.charAt(i) == '1') || (str.charAt(i) == '2') || (str.charAt(i) == '3') || (str.charAt(i) == '4') || (str.charAt(i) == '5') || (str.charAt(i) == '6') || (str.charAt(i) == '7') || (str.charAt(i) == '8') || (str.charAt(i) == '9'))
                r = r + str.charAt(i);

        return r;
        }

    // Retorna apenas as letras de uma string.

    public static String SoLetras(String str)
        {
        int t = str.length();
        String r = "";

        for(int i = 0; i < t; i++)
            if ((str.charAt(i) == 'a') || (str.charAt(i) == 'b') || (str.charAt(i) == 'c') || (str.charAt(i) == 'd') || (str.charAt(i) == 'e') || (str.charAt(i) == 'f') || (str.charAt(i) == 'g') || (str.charAt(i) == 'h') || (str.charAt(i) == 'i') || (str.charAt(i) == 'j') || (str.charAt(i) == 'k') || (str.charAt(i) == 'l') || (str.charAt(i) == 'm') || (str.charAt(i) == 'n') || (str.charAt(i) == 'o') || (str.charAt(i) == 'p') || (str.charAt(i) == 'q') || (str.charAt(i) == 'r') || (str.charAt(i) == 's') || (str.charAt(i) == 't') || (str.charAt(i) == 'u') || (str.charAt(i) == 'v') || (str.charAt(i) == 'w') || (str.charAt(i) == 'x') || (str.charAt(i) == 'y') || (str.charAt(i) == 'z') || (str.charAt(i) == 'A') || (str.charAt(i) == 'B') || (str.charAt(i) == 'C') || (str.charAt(i) == 'D') || (str.charAt(i) == 'E') || (str.charAt(i) == 'F') || (str.charAt(i) == 'G') || (str.charAt(i) == 'H') || (str.charAt(i) == 'I') || (str.charAt(i) == 'J') || (str.charAt(i) == 'K') || (str.charAt(i) == 'L') || (str.charAt(i) == 'M') || (str.charAt(i) == 'N') || (str.charAt(i) == 'O') || (str.charAt(i) == 'P') || (str.charAt(i) == 'Q') || (str.charAt(i) == 'R') || (str.charAt(i) == 'S') || (str.charAt(i) == 'T') || (str.charAt(i) == 'U') || (str.charAt(i) == 'V') || (str.charAt(i) == 'W') || (str.charAt(i) == 'X') || (str.charAt(i) == 'Y') || (str.charAt(i) == 'Z'))
                r = r + str.charAt(i);

        return r;
        }

    // Fim escopo desenvolvido por Antonio Vandré Pedrosa Furtunato Gomes.

    // Início escopo desenvolvido por terceiros.

    // Fim escopo desenvolvido por terceiros.
}