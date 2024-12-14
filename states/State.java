package jogoPong.states;

import java.awt.Graphics;

/*/
 * é uma abstração para os diferentes estados do jogo (Menu, Level1, etc.), 
 * garantindo que todos os estados implementem os mesmos métodos essenciais. 
 * Isso promove consistência no código e facilita o gerenciamento de estados pelo StateManager.
 */

public interface State {
	//Método para inicializar o estado
	void init();
	//Método para atualizar a lógica do estado
	void update(); 
	//Método para desenhar o estado
	void render(Graphics g);
	//Método para capturar teclas pressionadas
	void KeyPressed(int cod);
	//Método para capturar teclas liberadas
	void KeyReleased(int cod); 

}
