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
 * Implementa o estado de menu inicial do jogo Pong, exibindo opções como START, HELP, e EXIT. 
 * Também gerencia as interações do jogador para navegar e selecionar opções no menu.
 */

public class MenuState implements State {
    private String [] options = {"START", "HELP", "EXIT"}; 
    private Font font1; // Título
    private Font font2; // Fonte do menu
    private int choice = 1;
    private int x = 0, y = 0, movex = 2, movey = 2; 

    /**
     * Inicializa as fontes do menu e título.
     */
    @Override
    public void init() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (fontStream != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                font1 = customFont.deriveFont(Font.BOLD, 80);
                font2 = customFont.deriveFont(Font.PLAIN, 32);
            } else {
                throw new IOException("Fonte não encontrada no classpath!");
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            font1 = new Font("Dialog", Font.BOLD, 80);
            font2 = new Font("Dialog", Font.PLAIN, 32);
        }
    }

    /**
     * Atualiza os movimentos na tela.
     */
    @Override
    public void update() {
        x += movex; 
        y += movey;
        limits(); // Verifica os limites para alterar a direção do movimento
    }

    /**
     * Garante que a bola fique dentro dos limites da tela.
     */
    private void limits() {
        if (x + 15 > Game.width) movex = -1;
        if (y + 15 > Game.height) movey = -1;
        if (x < 0) movex = 1; 
        if (y < 0) movey = 1; 
    }

    /**
     * Renderiza o menu inicial na tela.
     */
    @Override
    public void render(Graphics g) {
        // Divisão proporcional de elementos ao tamanho da tela
        int titleY = Game.height / 3 + 40; 
        int menuStartY = Game.height / 2 + 40; 
        int spacing = 65; 

        // Background da tela
        g.setColor(new Color(13, 27, 42));
        g.fillRect(0, 0, Game.width, Game.height);

        // Título "PONG"
        g.setColor(new Color(224, 225, 221));
        g.setFont(font1);
        int titleX = Game.width / 2 - g.getFontMetrics(font1).stringWidth("PONG") / 2;
        g.drawString("PONG", titleX, titleY);

        // Opções do menu
        g.setFont(font2);
        for (int i = 0; i < options.length; i++) {
            g.setColor(new Color(224, 225, 221));
            if (i == choice) 
            	g.setColor(new Color(255, 183, 3));

            // Calcula posição de cada opção
            int optionX = Game.width / 2 - g.getFontMetrics(font2).stringWidth(options[i]) / 2;
            int optionY = menuStartY + i * spacing;
            g.drawString(options[i], optionX, optionY);
        }

        // Bola animada
        g.setColor(new Color(232, 93, 4));
        g.fillOval(x, y, 52, 52);
    }
    
    /**
     * Gerencia as teclas pressionadas no menu.
     */
    @Override
    public void KeyReleased(int cod) {
        if (cod == KeyEvent.VK_UP) { 
            choice--;
            if (choice < 0) choice = options.length - 1;
        }
        if (cod == KeyEvent.VK_DOWN) { 
            choice++;
            if (choice > options.length - 1) choice = 0;
        }
        if (cod == KeyEvent.VK_ENTER) { 
            select();
        }
    }

    /**
     * Seleciona a opção atual do menu.
     */
    private void select() {
        switch (choice) {
            case 0:
                StateManager.setState(StateManager.LEVEL1); // START
                break;
            case 1:
                StateManager.setState(StateManager.HELP); // HELP
                break;
            case 2:
                System.exit(0); // EXIT
                break;
            default:
                break;
        }
    }

    @Override
    public void KeyPressed(int cod) {}
}
