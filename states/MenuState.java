package jogoPong.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import jogoPong.Game;

public class MenuState implements State {
    private String[] options = {"START", "HELP", "EXIT"}; // Opções do menu
    private Font font1; // Fonte para o título
    private Font font2; // Fonte para as opções
    private int choice = 0; // Indica a opção atualmente selecionada
    private int x = 0, y = 0, movex = 2, movey = 2; // Para movimentar um elemento visual (ex: bola)

    @Override
    public void init() {
        try {
            // Carrega a fonte personalizada do classpath
            InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (fontStream != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                font1 = customFont.deriveFont(Font.BOLD, 36);
                font2 = customFont.deriveFont(Font.PLAIN, 18);
            } else {
                throw new IOException("Fonte não encontrada no classpath!");
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font1 = new Font("Dialog", Font.BOLD, 36);
            font2 = new Font("Dialog", Font.PLAIN, 18);
        }
    }

    @Override
    public void update() {
        x += movex; // Atualiza a posição no eixo X
        y += movey; // Atualiza a posição no eixo Y
        limits(); // Verifica os limites para alterar a direção do movimento
    }

    private void limits() {
        if (x + 15 > 400) // Inverte a direção no eixo X
            movex = -1;
        if (y + 15 > 300) // Inverte a direção no eixo Y
            movey = -1;
        if (x < 0)
            movex = 1; // Garante que não ultrapasse os limites
        if (y < 0)
            movey = 1; // Garante que não ultrapasse os limites
    }

    @Override
    public void render(Graphics g) {
        // Fundo preto
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 300);

        // Título "PONG"
        g.setColor(Color.WHITE);
        g.setFont(font1);
        g.drawString("PONG", 
                     200 - g.getFontMetrics().stringWidth("PONG") / 2, 
                     75);

        // Opções do menu
        g.setFont(font2);
        for (int i = 0; i < options.length; i++) {
            g.setColor(Color.WHITE);
            if (i == choice)
                g.setColor(Color.GREEN);
            g.drawString(options[i], 
                         200 - g.getFontMetrics().stringWidth(options[i]) / 2, 
                         200 + g.getFontMetrics(font2).getHeight() * i);
        }

        // Bola amarela em movimento
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 15, 15);
    }

    @Override
    public void KeyPressed(int cod) {}

    @Override
    public void KeyReleased(int cod) {
        if (cod == KeyEvent.VK_UP) { // Move para cima no menu - SETA UP
            choice--;
            if (choice < 0)
                choice = options.length - 1;
        }
        if (cod == KeyEvent.VK_DOWN) { // Move para baixo no menu - SETA DOWN
            choice++;
            if (choice > options.length - 1)
                choice = 0;
        }
        if (cod == KeyEvent.VK_ENTER) { // Seleciona a opção atual
            select();
        }
    }

    private void select() {
        switch (choice) {
            case 0:
                StateManager.setState(StateManager.LEVEL1); // Inicia o jogo
                break;
            case 1:
                // Ajuda (não implementado)
                break;
            case 2:
                System.exit(0); // Sai do jogo
                break;
            default:
                break;
        }
    }
}
