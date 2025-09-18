/*
 * Proprietário: Antonio Vandré Pedrosa Furtunato Gomes.
 * 
 * Launcher do Game ABBlockRun.
 * 
 * Dependências: AntonioVandre.
 * 
 * Sugestões ou comunicar erros: "a.vandre.g@gmail.com".
 * 
 * Licença de uso: Atribuição-NãoComercial-CompartilhaIgual (CC BY-NC-SA).
 * 
 * Última atualização: 18-09-2025.
 */

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.lang.ProcessBuilder;

public class AVBlockRunLauncher
    {
    public static String VersaoLauncher = "08-02-2023";

    public static String URLAVBlockVersao = "https://github.com/antoniovandre2/AVBlockRun/raw/main/AVBlockRunVersao.txt";

    public static String ArquivoAVBlockVersao = "AVBlockRunVersao.txt";

    public static String URLAVBlockRun = "https://github.com/antoniovandre2/AVBlockRun/raw/main/AVBlockRun.jar";

    public static String ArquivoAVBlockRun = "AVBlockRun.jar";

    public static String MensagemErroAtualizar = "Erro ao atualizar o game AVBlockRun.";

    public static String MensagemErroExecutar = "Erro ao executar o game AVBlockRun.";

    public static void main(String[] args)
        {
        int FlagSucessoDownloadNet = 1;

        try
            {
            downloadUsingStream(URLAVBlockVersao, ArquivoAVBlockVersao + ".tmp");
            } catch (IOException e) {FlagSucessoDownloadNet = 0;}

        if (FlagSucessoDownloadNet == 1)
            {
            File file = new File(ArquivoAVBlockVersao);
            int FlagSucessoVersaoLocal = 1;
            String VersaoLocal = "";

            try
                {
                BufferedReader br = new BufferedReader(new FileReader(file));
                VersaoLocal = br.readLine();
                } catch (IOException e) {FlagSucessoVersaoLocal = 0;}

            File fileNet = new File(ArquivoAVBlockVersao + ".tmp");
            int FlagSucessoVersaoNet = 1;
            String VersaoNet = "";

            try
                {
                BufferedReader brNet = new BufferedReader(new FileReader(fileNet));
                VersaoNet = brNet.readLine();
                } catch (IOException e) {FlagSucessoVersaoNet = 0;}

            if ((FlagSucessoVersaoLocal == 1) && (FlagSucessoVersaoNet == 1))
                {
                if (! (VersaoNet.equals(VersaoLocal)))
                    try
                        {
                        downloadUsingStream(URLAVBlockRun, ArquivoAVBlockRun);
                        downloadUsingStream(URLAVBlockVersao, ArquivoAVBlockVersao);
                        } catch (IOException e) {}
                }
            else
                try
                    {
                    downloadUsingStream(URLAVBlockRun, ArquivoAVBlockRun);
                    downloadUsingStream(URLAVBlockVersao, ArquivoAVBlockVersao);
                    } catch (IOException e) {}
            }

        try
            {
            String ArgumentoEstatisticas = "";

            if (args.length != 0) ArgumentoEstatisticas = args[0];

            ProcessBuilder pb = new ProcessBuilder("java", "-jar", ArquivoAVBlockRun, ArgumentoEstatisticas);
            Process p = pb.start();
            } catch (IOException e) {System.out.println(MensagemErroExecutar);}
        }

    private static void downloadUsingStream(String urlStr, String file) throws IOException
        {
		try
			{
			URL url = new URI(urlStr).toURL();
			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			FileOutputStream fis = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int count=0;
			while((count = bis.read(buffer,0,1024)) != -1) fis.write(buffer, 0, count);
			fis.close();
			bis.close();
			} catch (URISyntaxException e) {}
        }
}
