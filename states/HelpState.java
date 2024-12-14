package jogoPong.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import jogoPong.Game;

/**
 * Implementa o estado de HELP do jogo Pong, exibindo informações sobre como jogar.
 */

public class HelpState implements State {
    private Font font1; //Título
    private Font font2; //Fonte do texto

    @Override
    public void init() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (fontStream != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                font1 = customFont.deriveFont(Font.BOLD, 46);
                font2 = customFont.deriveFont(Font.PLAIN, 19);
            } else {
                throw new IOException("Fonte não encontrada no classpath!");
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font1 = new Font("Dialog", Font.BOLD, 46);
            font2 = new Font("Dialog", Font.PLAIN, 19);
        }
    }

    /*/
     * ESTILIZACAO DA PAGINA COM INFORMATIVOS
     */
    @Override
    public void render(Graphics g) {
        // Background da tela
        g.setColor(new Color(13, 27, 42));
        g.fillRect(0, 0, Game.width, Game.height);

        // Título "HELP"
        g.setColor(new Color(255, 183, 3));
        g.setFont(font1);
        g.drawString("INFORMATIVOS", Game.width / 2 - g.getFontMetrics().stringWidth("INFORMATIVOS") / 2, 130);

        /*/
         * MATRIZ: Texto formatado
         */
        g.setFont(font2);
        // Posição inicial do texto + espaçamento entre linhas
        int startY = 210, spacing = 60; 
        // Colunas da matriz 
        int col1X = 100, col2X = 300, col3X = 490; 
        
        String[][] instructions = {
            {"", "Teclas", "Teclas"},
            {"Player 1:", "W", "S"},
            {"Player 2:", "UP", "DOWN"}
        };

        /*/
         * Coordenadas para linhas horizontais
         */
        
        //Posicoes X
        int lineStartX = 100, lineEndX = 680; //respectivamente, incio e fim
        //Posicoes Y
        int lineAboveY = startY - 40, lineBelowY = startY + (instructions.length * spacing) + 10; //respectivamente, cima e embaixo

        //Desenha a linha superior
        g.setColor(new Color(224, 225, 221));
        g.drawLine(lineStartX, lineAboveY, lineEndX, lineAboveY);

        //Desenha a matriz
        for (int i = 0; i < instructions.length; i++) {
            int rowY = startY + (i * spacing);
            g.drawString(instructions[i][0], col1X, rowY); // Coluna 1 (teclas)
            g.drawString(instructions[i][1], col2X, rowY); // Coluna 2 (player 1)
            g.drawString(instructions[i][2], col3X, rowY); // Coluna 3 (player 2)
        }

        //Desenha a linha inferior
        g.drawLine(lineStartX, lineBelowY, lineEndX, lineBelowY);

        //Instrução final
        int instructionY = lineBelowY + 50; // Abaixo da linha inferior
        int lineSpacing = 60;

        g.drawString(
            "Aperte ENTER para retornar ao menu.", 
            Game.width / 2 - g.getFontMetrics().stringWidth("Aperte ENTER para retornar ao menu.") / 2, 
            instructionY
        );

        g.drawString(
            "Divirta-se jogando Pong!", 
            Game.width / 2 - g.getFontMetrics().stringWidth("Divirta-se jogando Pong!") / 2, 
            instructionY + lineSpacing
        );
    }

    // Volta para o menu inicial
    @Override
    public void KeyReleased(int cod) {
        if (cod == KeyEvent.VK_ENTER) {
            StateManager.setState(StateManager.MENU);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void KeyPressed(int cod) {
    }
}
