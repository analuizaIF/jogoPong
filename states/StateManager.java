package jogoPong.states;

//Importações necessárias
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StateManager implements KeyListener {
	
	public static final int numberStates = 3;  // Número total de estados do jogo
	public static State[]states = new State[numberStates];  // Array para armazenar os estados
	public static int currentState = 0;  // Estado atual do jogo
	
	// Constantes que representam os estados
	public static final int FPS = 0;
	public static final int MENU = 1;
	public static final int LEVEL1 = 2;
			
	// Define o estado atual
	public static void setState(int state) {
		currentState = state;
		states[currentState].init(); // Inicializa o estado
	}
	
	// Retorna o estado atual
	public static State getState() {
		return states[currentState];
		
	}
	
	 // Construtor: Inicializa os estados disponíveis
	public  StateManager() {
		states[0] = new FPSState(); // Estado para exibir FPS
		states[1] = new MenuState();  // Estado do Menu
		states[2] = new Level1State(); // Estado do Nível 1
	}
	
	// Atualiza o estado atual
	public void update () {
		states[currentState].update();
		
	}
	
	// Renderiza o estado atual
	public void render(Graphics g) {
		states[currentState].render(g);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		states[currentState].KeyPressed(e.getKeyCode()); // Passa o evento para o estado atual
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		states[currentState].KeyReleased(e.getKeyCode()); // Passa o evento para o estado atual
		
	}
	


}
