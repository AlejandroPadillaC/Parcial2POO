
package comidaapp;

import View.Inventario;
import View.MenuMain;

public class ComidaApp {
    
    public static Inventario tablaGlobal = new Inventario();
    
    public static void main(String[] args) {
        
        MenuMain menu = new MenuMain();
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);
    }
}
