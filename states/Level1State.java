package jogoPong.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import jogoPong.Game;
import jogoPong.input.KeyManager;

public class Level1State implements State {
	
	private Rectangle ball = new Rectangle(Game.width/2 - 5, Game.height/2 - 5, 10, 10);  // Bola
	private Rectangle p1 = new Rectangle(0, 0, 10, 50); // Jogador 1
	private Rectangle p2 = new Rectangle(Game.width - 10, 0, 10, 50);  // Jogador 2
	
	private int  movex = 1, movey = 1; // Movimento da bola
	private int score1 = 0, score2 = 0; // Pontuações

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
	public void start() {
		ball.x = Game.width/2 - 5; // Reseta a posição da bola
		ball.x = Game.height/2 - 5;
		
		Random r = new Random();
		movex =(r.nextInt(2) == 0) ? 2: -2;  // Define direção aleatória
		movey =(r.nextInt(2) == 0) ? 2: -2;  // Define direção aleatória
	}
	

	@Override
	public void update() {
		ball.x += movex; // Atualiza a posição X da bola
		ball.y += movey; // Atualiza a posição Y da bola
		limitsBall(); // Verifica colisões da bola com os limites
		
		if (KeyManager.w)
			p1.y -=8;      // Move jogador 1 para cima
		if (KeyManager.s)
			p1.y +=8;       // Move jogador 1 para baixo
		if (KeyManager.up)
			p2.y -=8;      // Move jogador 2 para cima
		if (KeyManager.down)
			p2.y +=8;     // Move jogador 2 para baixo
	
		
		limitsPlayers(); // Garante que os jogadores fiquem na tela

	}

	private void limitsPlayers() {
		if (p1.y <0)
			p1.y=0;       // Limite superior para jogador 1
		if (p1.y > Game.height - p1.height)
			p1.y = Game.height - p1.height; // Limite inferior para jogador 1
		
		if (p2.y <0)
			p2.y=0;      // Limite superior para jogador 2
		if (p2.y > Game.height - p1.height)
			p2.y = Game.height - p1.height;   // Limite inferior para jogador 2
		
	}


	private void limitsBall() {
		if (ball.x+15 > Game.width) {   // Colisão direita
			score1++;
			start();
		}
		if (ball.y+15 > Game.height) 
			movey = -2; // Colisão inferior
		
		if(ball.x < 0) { 
			score2++;   // Colisão esquerda
			start();
		}
		if (ball.y <0)
			movey = 2; // Colisão superior
		
		if (p1.intersects(ball)|| p2.intersects(ball))
			movex *= -1; // Colisão com jogadores
	}
	

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.width,Game.height ); // Fundo preto
		g.setColor(Color.YELLOW);
		g.fillOval(ball.x, ball.y , ball.width, ball.height);// Desenha a bola
		
		Font fonte = new Font ("Dialog",Font.HANGING_BASELINE, 12);
		g.setFont(fonte);
		g.drawString("Player 1:"+ score1, Game.width* 1/4 - g.getFontMetrics().stringWidth("Player 1:"+ score1)/2, g.getFontMetrics(fonte).getHeight()); // Pontuação do jogador 1
		g.drawString("Player 2:"+ score2, Game.width* 3/4 - g.getFontMetrics().stringWidth("Player 2:"+ score2)/2, g.getFontMetrics(fonte).getHeight()); // Pontuação do jogador 2
		g.setColor(Color.PINK);
		g.fillRect(Game.width/2 - 3,0, 6, Game.height);
		
		g.setColor(Color.BLUE);
		g.fillRect(p1.x, p1.y, p1.width, p1.height);  // Jogador 1
		g.setColor(Color.RED);
		g.fillRect(p2.x, p2.y, p2.width, p2.height);  // Jogador 2

	}

	@Override
	public void KeyPressed(int cod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void KeyReleased(int cod) {
		// TODO Auto-generated method stub

	}

}
