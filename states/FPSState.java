package jogoPong.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import jogoPong.Game;

/*/
 * Representa um estado do jogo que exibe os "Ticks por Segundo" (TPS), 
 * uma métrica usada para medir o desempenho
 * e a responsividade do loop principal do jogo.
 */

public class FPSState implements State {
	private long now, lastTime = System.nanoTime();
	private double timer = 0;
	private int tick = 0;
	private int t;

	@Override
	public void init() {
		
	}

	/*/
	 * METODO: definindo tempo
	 */
	@Override
	public void update() {
		now = System.nanoTime();
		timer += now - lastTime;
		lastTime = now;
		tick++;

	}

	/*/
	 * ESTILIZACOES
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.width, Game.height);
		
		if (timer >= 1000000000) {
			t= tick;
			tick = 0;
			timer = 0;
		}
		
		g.setColor(Color.WHITE);
		Font font = new Font ("Serif", Font.PLAIN, 12);
		g.setFont(font);
		
		String text = "TPS:" + t;
		g.drawString(text, g.getFontMetrics().stringWidth(text), g.getFontMetrics(font).getHeight());
	}

	@Override
	public void KeyPressed(int cod) {
		
	}

	@Override
	public void KeyReleased(int cod) {
		
	}
}
