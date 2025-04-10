/*
 * Proprietário: Antonio Vandré Pedrosa Furtunato Gomes.
 * 
 * Game ABBlockRun.
 * 
 * Dependências: AntonioVandre >= 20231101.
 * 
 * Sugestões ou comunicar erros: "a.vandre.g@gmail.com".
 * 
 * Licença de uso: Atribuição-NãoComercial-CompartilhaIgual (CC BY-NC-SA).
 * 
 * Última atualização: 10-04-2025.
 */

import java.awt.*;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.lang.Thread;

import java.lang.Math;
import java.io.*;

import AntonioVandre.*;

public class AVBlockRun extends JComponent
    {
    public static String ArquivoAVBlockVersao = "AVBlockRunVersao.txt";

    // Variáveis do game.
    public int TamanhoPlanoX = 600;
    public int TamanhoPlanoY = 600;
    public int MinTamanhoPlanoX = 300;
    public int MinTamanhoPlanoY = 300;
    public Color CorBloco = Color.BLACK;
    public Color CorAlvo = Color.RED;
    public int TamanhoBloco = 20;
    public int MaxVelocidadeBloco = 20;
    public int TamanhoEspacoLabelStatus = 150;
    public int TamanhoFonteLabelStatus = 12;
    public int TonalidadeAproximacao = 1; // Um valor entre 0 e 1, inclusive.

    public int VelocidadeBlocoX = 0; // Velocidade inicial x do bloco.
    public int VelocidadeBlocoY = 0; // Velocidade inicial y do bloco.

    // Variáveis de funcionamento interno.

    public long ValorInteiroLong;
    public int MargemX = (int) (TamanhoPlanoX / 50);
    public int MargemY = (int) (TamanhoPlanoY / 50);
    public int CorrecaoX = 10;
    public int CorrecaoY = 0;
    public int xa;
    public int ya;
    public int Sair = 0;
    public long Pontuacao = 0;
    public int FlagArquivo = 0;
    public String DirecaoStr;
    public long TempoEntrada = System.currentTimeMillis();
    public int JogoPausado = 0;

    public int xb = (int) ((TamanhoPlanoX + TamanhoBloco) / 2 - CorrecaoX);
    public int yb = (int) ((TamanhoPlanoY + TamanhoBloco) / 2 - CorrecaoY);
    public int xt = xb;
    public int yt = yb;

    public static String MensagemErroAntonioVandreLib = "Requer AntonioVandre >= 20231101.";

    public class GradientLabel extends JLabel
        {
        private Color CorInicial;
        private Color CorFinal;

        public GradientLabel(String Texto)
            {
            super(Texto);

            CorInicial = new Color(200, 0, 0);
            CorFinal = Color.BLACK;
            this.setForeground(Color.WHITE);
            }

        public GradientLabel(String Texto, Color CorInicial, Color CorFinal)
            {
            super(Texto);
            this.CorInicial = CorInicial;
            this.CorFinal = CorFinal;
            this.setForeground(Color.WHITE);
            }

        public GradientLabel(String Texto, Color CorInicial, Color CorFinal, Color CorForeground)
            {
            super(Texto);
            this.CorInicial = CorInicial;
            this.CorFinal = CorFinal;
            this.setForeground(CorForeground);
            }

        public void paint(Graphics g)
            {
            int width = getWidth();
            int height = getHeight();

            GradientPaint paint = new GradientPaint(0, 0, CorInicial, width, height, CorFinal, true);
            Graphics2D g2d = (Graphics2D) g;
            Paint oldPaint = g2d.getPaint();
            g2d.setPaint(paint);
            g2d.fillRect(0, 0, width, height);
            g2d.setPaint(oldPaint);
            super.paint(g);
            }
        }

    private static class Line
        {
        final int x1; 
        final int y1;
        final int x2;
        final int y2;   
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color)
            {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
            }               
        }

    private final LinkedList<Line> lines = new LinkedList<Line>();
    private final LinkedList<Line> linesA = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4, Color color, int n)
        {
        lines.add(new Line(x1,x2,x3,x4, color));
        if (n == Integer.MAX_VALUE) repaint();
        }

    public void addLineA(int x1, int x2, int x3, int x4, Color color, int n)
        {
        linesA.add(new Line(x1,x2,x3,x4, color));
        if (n == Integer.MAX_VALUE) repaint();
        }

    public void clearLines()
        {lines.clear();}

    public void clearLinesA()
        {linesA.clear();}

    protected void paintComponent(Graphics g)
        {
        for (Line line : lines)
            {
            g.setColor(line.color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
            }

        for (Line line : linesA)
            {
            g.setColor(line.color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
            }
        }

    public static void main (String[] args) {AVBlockRun jogoc = new AVBlockRun(); if (args.length == 0) jogoc.jogo(""); else jogoc.jogo(args[0]);}

    public void jogo (String ArquivoEstatisticas)
        {
        String Versao = "Versão desconhecida.";

        try
            {
            ValorInteiroLong = Long.parseLong(String.valueOf(AntonioVandre.Versao));
            }
        catch (NumberFormatException e)
            {
            System.out.println(MensagemErroAntonioVandreLib);
            return;
            }

        if (AntonioVandre.Versao < 20231101)
            {
            System.out.println(MensagemErroAntonioVandreLib);
            return;
            }

        File file = new File(ArquivoAVBlockVersao);

        try
            {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Versao = br.readLine();
            } catch (IOException e) {}

        if (! ArquivoEstatisticas.equals(""))
            {
            FlagArquivo = 1;
            Pontuacao = LerEstatisticas (ArquivoEstatisticas);
            }

        if (Pontuacao < 0)
            Pontuacao = 0;

        JFrame FrameJogo = new JFrame("AVBlockRun - " + Versao);
        FrameJogo.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FrameJogo.setIconImage(new ImageIcon(getClass().getResource("AVBlockRun - Logo - 200p.png")).getImage());
		FrameJogo.setMinimumSize(new Dimension(MinTamanhoPlanoX, MinTamanhoPlanoY + TamanhoEspacoLabelStatus));
        FrameJogo.setPreferredSize(new Dimension(TamanhoPlanoX, TamanhoPlanoY + TamanhoEspacoLabelStatus));
        AVBlockRun comp = new AVBlockRun();
        comp.setPreferredSize(new Dimension(TamanhoPlanoX, TamanhoPlanoY));
        FrameJogo.getContentPane().add(comp, BorderLayout.PAGE_START);
        GradientLabel LabelStatus = new GradientLabel("Pontuação: " + String.valueOf(Pontuacao), new Color(200, 0, 0), Color.BLACK, Color.WHITE);
        LabelStatus.setBorder(new EmptyBorder(5, 5, 5, 5));
        LabelStatus.setFont(new Font("DialogInput", Font.BOLD | Font.ITALIC, TamanhoFonteLabelStatus));
        FrameJogo.add(LabelStatus);

        DesenharAlvo(comp);

        FrameJogo.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke)
                {
                TempoEntrada = System.currentTimeMillis();

                int keyCode = ke.getKeyCode();

                if (keyCode == KeyEvent.VK_ESCAPE)
                    {Sair = 1;}

                if (JogoPausado == 0)
                    {
                    if (keyCode == KeyEvent.VK_RIGHT)
                        if (VelocidadeBlocoX < MaxVelocidadeBloco)
                            VelocidadeBlocoX++;

                    if (keyCode == KeyEvent.VK_LEFT)
                        if (VelocidadeBlocoX > (-1) * MaxVelocidadeBloco)
                            VelocidadeBlocoX--;

                    if (keyCode == KeyEvent.VK_DOWN)
                        if (VelocidadeBlocoY < MaxVelocidadeBloco)
                            VelocidadeBlocoY++;

                    if (keyCode == KeyEvent.VK_UP)
                        if (VelocidadeBlocoY > (-1) * MaxVelocidadeBloco)
                            VelocidadeBlocoY--;
                    }

                if (keyCode == KeyEvent.VK_SPACE)
                    {
                    if (JogoPausado == 0)
                        JogoPausado = 1;
                    else
                        JogoPausado = 0;
                    }
                }

            public void keyReleased(KeyEvent ke){}
            public void keyTyped(KeyEvent ke){}
            });

        FrameJogo.pack();
        FrameJogo.setVisible(true);

        while(Sair == 0)
            {
            if (JogoPausado == 0) if (System.currentTimeMillis() > TempoEntrada + 60000)
                {
                VelocidadeBlocoX = 0;
                VelocidadeBlocoY = 0;
                }

            int FlagRedimensionarOver = 0;

            int width = FrameJogo.getWidth();
            int height = FrameJogo.getHeight();

            if (FlagRedimensionarOver == 0)
                if ((width != TamanhoPlanoX) || (height != TamanhoPlanoY + TamanhoEspacoLabelStatus))
                    {
                    TamanhoPlanoX = width;
                    TamanhoPlanoY = height - TamanhoEspacoLabelStatus;

                    FrameJogo.setPreferredSize(new Dimension(TamanhoPlanoX, TamanhoPlanoY + TamanhoEspacoLabelStatus));
                    comp.setPreferredSize(new Dimension(TamanhoPlanoX, TamanhoPlanoY));
                    FrameJogo.pack();

                    xb = (int) ((TamanhoPlanoX + TamanhoBloco) / 2 - CorrecaoX);
                    yb = (int) ((TamanhoPlanoY + TamanhoBloco) / 2 - CorrecaoY);
                    xt = xb;
                    yt = yb;

                    DesenharAlvo(comp);
                    DesenharBloco(comp);

                    VelocidadeBlocoX = 0;
                    VelocidadeBlocoY = 0;

                    JogoPausado = 0;
                    }

            if (JogoPausado == 0)
                {
                xb += VelocidadeBlocoX;
                yb += VelocidadeBlocoY;

                do
                    {
                    if (xt <= MargemX)
                        VelocidadeBlocoX = Math.abs(VelocidadeBlocoX);

                    if (xt + TamanhoBloco >= TamanhoPlanoX - MargemX - CorrecaoX)
                        VelocidadeBlocoX = - Math.abs(VelocidadeBlocoX);

                    if (yt <= MargemY)
                        VelocidadeBlocoY = Math.abs(VelocidadeBlocoY);

                    if (yt  + TamanhoBloco >= TamanhoPlanoY - MargemY - CorrecaoY)
                        VelocidadeBlocoY = - Math.abs(VelocidadeBlocoY);

                    if ((xt + TamanhoBloco >= xa) && (xt <= xa + TamanhoBloco) && (yt + TamanhoBloco >= ya) && (yt <= ya + TamanhoBloco))
                        {
                        Pontuacao++;

                        if (FlagArquivo == 1)
                            EscreverEstatisticas(ArquivoEstatisticas, Pontuacao);

                        System.out.println("Pontuação: " + String.valueOf(Pontuacao) + ".\n");

                        LabelStatus.setText("<html>Pontuação: " + String.valueOf(Pontuacao) + ".<br>" + DirecaoStr + "<br><br>Setas para mover.<br>Barra de espaços para pausar.<br>ESC para sair.</html>");

                        DesenharAlvo(comp);
                        }

                    DirecaoStr = "";

                    if (xa > xt + TamanhoBloco)
                        DirecaoStr = DirecaoStr + "Vá para a direita. ";

                    if (xa + TamanhoBloco < xt)
                        DirecaoStr = DirecaoStr + "Vá para a esquerda. ";

                    if (ya > yt + TamanhoBloco)
                        DirecaoStr = DirecaoStr + "Vá para baixo.";

                    if (ya + TamanhoBloco < yt)
                        DirecaoStr = DirecaoStr + "Vá para cima.";

                    LabelStatus.setText("<html>Pontuação: " + String.valueOf(Pontuacao) + ".<br>" + DirecaoStr + "<br><br>Setas para mover.<br>Barra de espaços para pausar.<br>ESC para sair.</html>");

                    DesenharBloco(comp);

                    if (xt < xb) xt++;
                    if (xt > xb) xt--;
                    if (yt < yb) yt++;
                    if (yt > yb) yt--;

					double d = Math.sqrt((xt - xa) * (xt - xa) + (yt - ya) * (yt - ya)) / Math.max(TamanhoPlanoX, TamanhoPlanoY);

					FrameJogo.getContentPane().setBackground(new Color((int) (256 * TonalidadeAproximacao - d * 256 * TonalidadeAproximacao / 4 - 1), (int) (256 * TonalidadeAproximacao / 4 - 1 + d * 128 * TonalidadeAproximacao), (int) (256 * TonalidadeAproximacao / 4 - 1 + d * 128 * TonalidadeAproximacao)));

                    try {
                        Thread.sleep((int) (100 / Math.max(Math.abs(VelocidadeBlocoX) + 1, Math.abs(VelocidadeBlocoY) + 1)));
                        } catch(InterruptedException e) {}

                    } while (((xb != xt) || (yb != yt)) && (JogoPausado == 0));
                }
                else
                    LabelStatus.setText("<html>Pontuação: " + String.valueOf(Pontuacao) + ".<br><br>Jogo pausado.<br>Barra de espaços para continuar.</html>");
            }

            System.exit(0);
        }

    public void DesenharBloco(AVBlockRun comp)
        {
        comp.clearLines();

        comp.addLine(xt, yt, xt + TamanhoBloco, yt, CorBloco, 0);
        comp.addLine(xt + TamanhoBloco, yt, xt + TamanhoBloco, yt + TamanhoBloco, CorBloco, 0);
        comp.addLine(xt, yt, xt, yt + TamanhoBloco, CorBloco, 0);
        comp.addLine(xt, yt + TamanhoBloco, xt + TamanhoBloco, yt + TamanhoBloco, CorBloco, 0);

        comp.addLine(xt + (int) (TamanhoBloco / 2), yt + (int) (TamanhoBloco / 2), xa + (int) (TamanhoBloco / 2), ya + (int) (TamanhoBloco / 2), CorBloco, Integer.MAX_VALUE);
        }

    public void DesenharAlvo(AVBlockRun comp)
        {
        comp.clearLinesA();

        do
            {
            xa = (int) (Math.random() * (TamanhoPlanoX - MargemX));
            ya = (int) (Math.random() * (TamanhoPlanoY - MargemY));

            if (xa == 0) xa = MargemX;
            if (ya == 0) ya = MargemY;
            if (xa >= TamanhoPlanoX - TamanhoBloco - MargemX - CorrecaoX) xa = TamanhoPlanoX - TamanhoBloco - MargemX - CorrecaoX - 1;
            if (ya >= TamanhoPlanoY - TamanhoBloco - MargemY - CorrecaoY) ya = TamanhoPlanoY - TamanhoBloco - MargemY - CorrecaoY - 1;
            } while ((xt + TamanhoBloco >= xa) && (xt <= xa + TamanhoBloco) && (yt + TamanhoBloco >= ya) && (yt <= ya + TamanhoBloco));

        comp.addLineA(xa, ya, xa + TamanhoBloco, ya, CorAlvo, 0);
        comp.addLineA(xa + TamanhoBloco, ya, xa + TamanhoBloco, ya + TamanhoBloco, CorAlvo, 0);
        comp.addLineA(xa, ya, xa, ya + TamanhoBloco, CorAlvo, 0);
        comp.addLineA(xa, ya + TamanhoBloco, xa + TamanhoBloco, ya + TamanhoBloco, CorAlvo, Integer.MAX_VALUE);
        }

    public long LerEstatisticas(String ArquivoEstatisticasArg)
        {
        File file = new File(ArquivoEstatisticasArg);

        try
            {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String PontuacaoStr = "";
            PontuacaoStr = br.readLine();
            if (AntonioVandre.NumeroNaturalLong(PontuacaoStr))
                return Long.valueOf(PontuacaoStr);
            else
                return -1;
            } catch (IOException e) {return -1;}
        }

    public void EscreverEstatisticas (String ArquivoEstatisticasArg, long PontuacaoArg)
        {
        try
            {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ArquivoEstatisticasArg));
            writer.write(String.valueOf(PontuacaoArg));
            writer.close();
            }
        catch (IOException e) {}
        }
    }
