package jogoPong;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import jogoPong.display.Display;
import jogoPong.input.KeyManager;
import jogoPong.states.StateManager;

/*/
 * É a classe principal que gerencia o ciclo de vida do jogo, incluindo inicialização, 
 * atualização e renderização.Também cuida da criação do display gráfico, dos gerenciadores 
 * de estados e teclas, e da definição do estado inicial do jogo.
 */

public class Game implements Runnable { 
	public static final int width = 800, height = 600; 
	
	private Display display; 
	private Thread thread;  
	private boolean running = false; 
		
	private StateManager sm; 
	private KeyManager km; 
	

	public Game() {
		display = new Display("Pong Game", width, height);  
		//Cria o gerenciador de estados
		sm = new StateManager(); 
		//Cria o gerenciador de teclas
		km = new KeyManager();
		//teclado
		display.setKeyListener(sm);
		display.setKeyListener(km);  
		
		//Define o estado inicial como o menu
		StateManager.setState(StateManager.MENU);  
	}

	@Override
	public void run() { 
		init();  
		
		// Define o número de frames por segundo
		int FPS = 60; 
		double timePerTick = 1000000000/60; 
		double delta = 0; 
		long now;
		long lastTime = System.nanoTime(); 
		
		// Loop principal do jogo
		while(running) {
			now = System.nanoTime();  
			delta += (now - lastTime) / timePerTick; 
			lastTime = now;  
			
			if (delta >= 1) {
				//Atualiza a lógica do jogo
				update();
				//Desenha o jogo na tela
				render(); 
				delta--; 
			}
		}
		
		stop();	
	}
	
	private void init() {	
		
	}
	
	/*/
	 * atualiza
	 */
	private void update() { 
		if (StateManager.getState() == null) return; 
			sm.update();  
			km.update();  
	}
	
	//renderização dos gráficos na tela
	private void render() {
		BufferStrategy bs = display.getBufferStrategy(); 
		if (bs == null) { 
			display.createBufferStrategy(); 
			bs = display.getBufferStrategy();
		}
		
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, Game.width, Game.height); 
		
		if (StateManager.getState()!= null) {
			sm.render(g); 
		}
		
		g.dispose();
		bs.show(); 
					
	}

	/*/
	 * METODO: inicializa jogo
	 */
	public synchronized void start() {
		if (thread != null) return;  
		thread = new Thread (this); 
		thread.start(); 
		running = true; 
		
	}
	
	/*/
	 * METODO: finaliza/Para jogo
	 */
	public synchronized void stop() {
		if (thread == null) return; 
		try {
			thread.join(); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
