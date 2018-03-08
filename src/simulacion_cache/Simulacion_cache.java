package simulacion_cache;
import javax.swing.JOptionPane;
/**
 *
* @author Alexy Cruz Jordi Mairena Juan O'Hara Oscar Diaz
 */
public class Simulacion_cache {

    public static void main(String[] args) {
        // TODO code application logic here
        String input = (String)JOptionPane.showInputDialog(
                null, "Ingrese N (direcciones)\nPor defecto N = 4096", "Simulación de cache", JOptionPane.PLAIN_MESSAGE, null, null, "4096");

        if (input != null) {
            try {
                int direcciones;
                direcciones = (input.isEmpty()) ? 4096 : Integer.parseInt(input);
                if (direcciones < 5 || direcciones > 4096) {
                    JOptionPane.showMessageDialog(null, "Error: número inválido", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    simulacion simulacion = new simulacion(direcciones);
                    simulacion.correr();
                    JOptionPane.showMessageDialog(null, simulacion.resultados, "Resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(null, "Error: número inválido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
