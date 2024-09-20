
package examen2;

public class Entry {
    
    String username;
    long posicion;
    Entry siguiente;
    
    public Entry(String username, long posicion) {
        this.username = username;
        this.posicion = posicion;
        this.siguiente = null;
    }
    
    public String getUsername() {
        return username;
    }
    
    public long getPosicion() {
        return posicion;
    }
    
    public Entry getSiguiente() {
        return siguiente;
    }
    
    public void setSiguiente(Entry siguiente) {
        this.siguiente = siguiente;
    }
}
