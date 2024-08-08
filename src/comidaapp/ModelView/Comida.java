
package comidaapp.ModelView;

/**
 *
 * @author Alejandro Padilla
 */
public  class Comida {
    
    private String alimento;
    private String TipAlimento;
    private String cantidadAlimento;

    public Comida(String alimento, String TipAlimento, String cantidadAlimento) {
        this.alimento = alimento;
        this.TipAlimento = TipAlimento;
        this.cantidadAlimento = cantidadAlimento;
    }
    
    public Comida(){
    }

    public String getAlimento() {
        return alimento;
    }

    public String getTipAlimento() {
        return TipAlimento;
    }

    public String getCantidadAlimento() {
        return cantidadAlimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public void setTipAlimento(String TipAlimento) {
        this.TipAlimento = TipAlimento;
    }

    public void setCantidadAlimento(String cantidadAlimento) {
        this.cantidadAlimento = cantidadAlimento;
    }
    
    

}
