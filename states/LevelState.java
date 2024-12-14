package jogoPong.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import jogoPong.Game;
import jogoPong.input.KeyManager;

/*/
 * Representa o primeiro nível do jogo Pong, gerenciando a lógica, 
 * os movimentos dos jogadores e da bola, e a exibição dos elementos gráficos na tela.
 */

public class LevelState implements State {

    private Rectangle ball = new Rectangle(Game.width / 2 - 5, Game.height / 2 - 5, 10, 10); 
    //x, y, width, height
    private Rectangle p1 = new Rectangle(22, 100, 20, 150); //raquete player 1
    private Rectangle p2 = new Rectangle(763, 100, 20, 150); //raquete player 2 

    private double movex = 1.25, movey = 1.25; //VELOCIDADE 
    private int score1 = 0, score2 = 0;

    private Font fontScore;

    @Override
    public void init() {
        try {
            InputStream fontStream = getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf");
            if (fontStream != null) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                fontScore = customFont.deriveFont(Font.PLAIN, 24); 
            } else {
                throw new IOException("Fonte não encontrada no classpath!");
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            fontScore = new Font("Dialog", Font.PLAIN, 24); 
        }
    }

    /*
     * MÉTODO: movimentos da bola 
     */
    public void start() {
        //Centralize a bola na tela
        ball.x = (Game.width - 40) / 2; 
        ball.y = (Game.height - 40) / 2;

        //Direção aleatória
        Random r = new Random();
        movex = (r.nextInt(2) == 0) ? 2 : -2; 
        movey = (r.nextInt(2) == 0) ? 2 : -2;  
    }


    /*
     * MÉTODO: Atualiza a posição da bola e movimenta as raquetes
     */
    @Override
    public void update() {
        //Atualiza a posição da bola
        ball.x += movex; 
        ball.y += movey;
        limitsBall();

        //Movimentos do Jogador 1 (teclas W e S)
        if (KeyManager.w)
            p1.y -= 8;      
        if (KeyManager.s)
            p1.y += 8;

        //Movimentos do Jogador 2 (setas para cima e para baixo)
        if (KeyManager.up)
            p2.y -= 8;   
        if (KeyManager.down)
            p2.y += 8; 

        limitsPlayers();
    }

    /*
     * MÉTODO: Restringe os movimentos das raquetes para dentro da tela
     */
    private void limitsPlayers() {
        //Limites para o Jogador 1
        if (p1.y < 0) p1.y = 0;       
        if (p1.y > Game.height - p1.height)
            p1.y = Game.height - p1.height; 

        //Limites para o Jogador 2
        if (p2.y < 0) p2.y = 0;      
        if (p2.y > Game.height - p2.height)
            p2.y = Game.height - p2.height;   
    }

    /*
     * MÉTODO: Gerencia os limites e colisões da bola
     */
    private void limitsBall() {
        int ballSize = 40; 

        //Colisão direita + esquerda
        if (ball.x + ballSize > Game.width) { 
            score1++;
            start();
        }
        
        if (ball.x < 0) {
            score2++;
            start();
        }

        //Colisão inferior + superior
        if (ball.y + ballSize > Game.height) movey = -Math.abs(movey); 
        if (ball.y < 0) movey = Math.abs(movey); 

        //Colisão com as raquetes
        //jogador 1
        if (ball.x <= p1.x + p1.width && ball.y + ballSize >= p1.y && ball.y <= p1.y + p1.height) {
            ball.x = p1.x + p1.width; //evita ultrapassagem
            movex = Math.abs(movex); //move direcao contraria
        }
        
        //jogador 2
        if (ball.x + ballSize >= p2.x && ball.y + ballSize >= p2.y && ball.y <= p2.y + p2.height) {
            ball.x = p2.x - ballSize; 
            movex = -Math.abs(movex);
        }
    }

    /*
     * ESTILIZACAO DA TELA E ELEMENTOS
     */
    @Override
    public void render(Graphics g) {
    	
        //background tela
        g.setColor(new Color(13, 27, 42));
        g.fillRect(0, 0, Game.width, Game.height);

        //Bola 
        g.setColor(new Color(255, 183, 3));
        g.fillOval(ball.x, ball.y, 40, 40);

        //Score
        g.setFont(fontScore);
        g.setColor(new Color(224, 225, 221));
        
        //tamanhos placar
        g.drawString("Player 1:" + score1, Game.width / 4 - g.getFontMetrics().stringWidth("Player 1" + score1) / 2, 70);
        g.drawString("Player 2: " + score2, Game.width * 3 / 4 - g.getFontMetrics().stringWidth("Player 2: " + score2) / 2, 70);
      

        //Linha vertical pontilhada - centro da tela
        g.setColor(new Color(65, 90, 119)); 
        int dashHeight = 25; // Altura de cada linha
        int gapHeight = 25;   // Altura do intervalo entre linhas
        int lineThickness = 13; // Espessura da linha
        
        for (int y = 10; y < Game.height; y += dashHeight + gapHeight) {
        	 g.fillRect(Game.width / 2 - lineThickness / 2, y, lineThickness, dashHeight);
        }

        //Raquete do Jogador 1
        g.setColor(new Color(42, 157, 143)); //azul pastel
        g.fillRect(p1.x, p1.y, p1.width, p1.height);

        //Raquete do Jogador 2
        g.setColor(new Color(231, 74, 81)); //vermelho pastel
        g.fillRect(p2.x, p2.y, p2.width, p2.height);
    }

    @Override
    public void KeyPressed(int cod) {
    	
    }

    @Override
    public void KeyReleased(int cod) {
     
    }
}
