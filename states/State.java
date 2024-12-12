package jogoPong.states;

//Importação para gráficos
import java.awt.Graphics;

public interface State {
	void init(); // Método para inicializar o estado
	void update(); // Método para atualizar a lógica do estado
	void render(Graphics g); // Método para desenhar o estado
	void KeyPressed(int cod); // Método para capturar teclas pressionadas
	void KeyReleased(int cod); // Método para capturar teclas liberadas

}
