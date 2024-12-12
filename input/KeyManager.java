package jogoPong.input;

//Importação de eventos do teclado
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	private boolean[] keys = new boolean [256];  // Array para armazenar o estado de cada tecla
	public static boolean w, s, up, down;  // Variáveis para as teclas específicas do jogo
	
	// Atualiza o estado das teclas relevantes para o jogo
	public void update(){
		w = keys[KeyEvent.VK_W];  // Verifica se a tecla "W" está pressionada
		s =keys[KeyEvent.VK_S];  // Verifica se a tecla "S" está pressionada
		up = keys[KeyEvent.VK_UP];  // Verifica se a tecla "Seta para Cima" está pressionada
		down = keys[KeyEvent.VK_DOWN]; // Verifica se a tecla "Seta para Baixo" está pressionada
	}
	
	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() < 0 || k.getKeyCode() > 255)  // Evita erros para teclas inválidas
			return;
		keys[k.getKeyCode()] = true;  // Marca a tecla como pressionada
		
	}

	@Override
	public void keyReleased(KeyEvent k) {
		if(k.getKeyCode() < 0 || k.getKeyCode() > 255)   // Evita erros para teclas inválidas
			return;
		keys[k.getKeyCode()] = false;  // Marca a tecla como liberada
		 
	}

	@Override
	public void keyTyped(KeyEvent k) {
		 // Esse método não esta sendo usado no jogo
		
	}

}
