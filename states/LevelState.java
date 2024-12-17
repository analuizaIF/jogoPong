package jogoPong.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.swing.JOptionPane;

import jogoPong.Game;
import jogoPong.input.KeyManager;

/*/
 * Representa o primeiro nível do jogo Pong, gerenciando a lógica, 
 * os movimentos dos jogadores e da bola, e a exibição dos elementos gráficos na tela.
 */

public class LevelState implements State {

    private Rectangle ball = new Rectangle(Game.width / 2 - 5, Game.height / 2 - 5, 10, 10); 
    //x, y, width, height
    private Rectangle raquete1 = new Rectangle(22, 100, 20, 150); //raquete player 1
    private Rectangle raquete2 = new Rectangle(763, 100, 20, 150); //raquete player 2 

    //Movimentos na tela
    private double movex = 2.3, movey = 2.3;
    private int score1 = 0, score2 = 0;
    private static final int SCORE_LIMIT = 11; //Limite de DEZ (10) pontos marcados (valor vetorizado)


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
        
        start();
    }

    /*
     * MÉTODO: movimentos da bola 
     */
    public void start() {
        //Centralize a bola na tela
        ball.x = (Game.width - 40) / 2; 
        ball.y = (Game.height - 40) / 2;

     // Velocidade inicial fixa, mas com direção aleatória
        movex = (new Random().nextBoolean()) ? 3.0 : -3.0;
        movey = (new Random().nextBoolean()) ? 2.5 : -2.5;

    }

    /*/
     * METODO: controle de velocidade da bola
     */
    private void increaseSpeed() {
    	//Maxima velocidade
    	double maxSpeed = 5.0;
    	//Aumenta velocidade em 10%
        if (Math.abs(movex) < maxSpeed) movex *= 1.1;
        if (Math.abs(movey) < maxSpeed) movey *= 1.1;
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
            raquete1.y -= 8;      
        if (KeyManager.s)
            raquete1.y += 8;

        //Movimentos do Jogador 2 (setas para cima e para baixo)
        if (KeyManager.up)
            raquete2.y -= 8;   
        if (KeyManager.down)
            raquete2.y += 8; 

        limitsPlayers();
    }

    /*
     * MÉTODO: Restringe os movimentos das raquetes para dentro da tela 
     */
    private void limitsPlayers() {
    	/*/
    	 * Há um limite entre o placar e entre bola e raquetes - tais elementos ultrapassam o placar de score
    	 */
    	//Limites para o Jogador 1 (RAQUETES)
        if (raquete1.y < 75) raquete1.y = 75; 
    
        //Novo limite superior
        if (raquete1.y > Game.height - raquete1.height)
        	//Limite inferior
            raquete1.y = Game.height - raquete1.height;

        //Limites para o Jogador 2 (RAQUETES)
        if (raquete2.y < 75) raquete2.y = 75; 
        
        if (raquete2.y > Game.height - raquete2.height)
            raquete2.y = Game.height - raquete2.height; 
    }

    /*/
     * METODO: verifica pontos marcados (SCORE)
     */
    private void checkScore(int player) {
        if (player == 1) {
            score1++;
            if (score1 >= SCORE_LIMIT) {
                endGame(1); //Jogador 1 venceu
                return;
            }
        } else if (player == 2) {
            score2++;
            if (score2 >= SCORE_LIMIT) {
                endGame(2); //Jogador 2 venceu
                return;
            }
        }
      //Reinicia a posição da bola
        start(); 
    }

    /*
     * MÉTODO: Gerencia os limites e colisões da bola
     */
    private void limitsBall() {
        int ballSize = 40;
        
        //Colisão direita + esquerda (MARCADORES DE PONTOS)
        if (ball.x + ballSize > Game.width) {
            checkScore(1); 
            return;
        }

        if (ball.x < 0) {
            checkScore(2); 
            return;
        }

        //Colisão inferior + superior (BOLA)
        if (ball.y + ballSize > Game.height) {
            ball.y = Game.height - ballSize;
            movey = -Math.abs(movey); 
        }

        //Novo limite superior
        if (ball.y < 60) { 
            ball.y = 60;
            movey = Math.abs(movey); 
        }
        
        //Colisão com as raquetes 
        if (ball.x <= raquete1.x + raquete1.width && ball.y + ballSize >= raquete1.y && ball.y <= raquete1.y + raquete1.height) {
            ball.x = raquete1.x + raquete1.width;
            movex = Math.abs(movex); 
          
            //Aumenta a velocidade da bola
            increaseSpeed();
        }

        if (ball.x + ballSize >= raquete2.x && ball.y + ballSize >= raquete2.y && ball.y <= raquete2.y + raquete2.height) {
            ball.x = raquete2.x - ballSize;
            movex = -Math.abs(movex); 
            increaseSpeed(); 
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
        
        //Placar
        g.drawString("Player 1:" + score1, Game.width / 4 - g.getFontMetrics().stringWidth("Player 1" + score1) / 2, 75);
        g.drawString("Player 2: " + score2, Game.width * 3 / 4 - g.getFontMetrics().stringWidth("Player 2: " + score2) / 2, 75);
      

        //Linha vertical pontilhada - centro da tela
        g.setColor(new Color(65, 90, 119)); 
      //Altura de cada linha
        int dashHeight = 25; 
      //Altura do intervalo entre linhas
        int gapHeight = 25;   
      //Espessura da linha
        int lineThickness = 13; 
        
        for (int y = 10; y < Game.height; y += dashHeight + gapHeight) {
        	 g.fillRect(Game.width / 2 - lineThickness / 2, y, lineThickness, dashHeight);
        }

        //Raquete do Jogador 1
        g.setColor(new Color(42, 157, 143)); //azul pastel
        g.fillRect(raquete1.x, raquete1.y, raquete1.width, raquete1.height);

        //Raquete do Jogador 2
        g.setColor(new Color(231, 74, 81)); //vermelho pastel
        g.fillRect(raquete2.x, raquete2.y, raquete2.width, raquete2.height);
       
    }
   
    /*/
     * METODO: fim de jogo + caixa de texto (GANHADOR)
     */
	private void endGame(int winner) {
		JOptionPane.showMessageDialog(null, "Player " + winner + " venceu o jogo!", "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
		
	    //Reseta dos placares
	    score1 = 0;
	    score2 = 0;

	    //Voltar ao menu principal
	    start();
	    StateManager.setState(StateManager.MENU); 
	}

    
    @Override
    public void KeyPressed(int cod) {
    	
    }

    @Override
    public void KeyReleased(int cod) {
     
    }
}
