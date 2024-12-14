package jogoPong.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*/
 * Gerencia as entradas do teclado, rastreando quais teclas estão pressionadas 
 * e liberadas, e mapeando essas informações para comandos específicos do jogo.
 */

public class KeyManager implements KeyListener {
	private boolean [] keys = new boolean [256];  
	public static boolean w, s, up, down;  
	
	/*/
	 * METODO: teclas definidas
	 */
	public void update(){
		w = keys[KeyEvent.VK_W];  
		s =keys[KeyEvent.VK_S];  
		up = keys[KeyEvent.VK_UP];  
		down = keys[KeyEvent.VK_DOWN]; 
	}
	
	/*/
	 * METODO: configuracoes de teclas pressionadas
	 */
	@Override
	public void keyPressed(KeyEvent k) {
		if(k.getKeyCode() < 0 || k.getKeyCode() > 255) 
			return;
		keys[k.getKeyCode()] = true; 
	}

	/*/
	 * METODO: configuracoes de teclas livres
	 */
	@Override
	public void keyReleased(KeyEvent k) {
		if(k.getKeyCode() < 0 || k.getKeyCode() > 255)  
			return;
		keys[k.getKeyCode()] = false;  
		 
	}

	@Override
	public void keyTyped(KeyEvent k) {
		
	}

}
