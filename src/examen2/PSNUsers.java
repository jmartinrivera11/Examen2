
package examen2;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PSNUsers {
    
    private RandomAccessFile raf;
    private HashTable usuarios;
    
    public PSNUsers() throws IOException {
        raf = new RandomAccessFile("psn.ply", "rw");
        this.usuarios = new HashTable();
        reloadHashTable();
    }
    
    private void reloadHashTable() throws IOException {
        usuarios = new HashTable();
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            long posicion = raf.getFilePointer();
            String username = raf.readUTF();
            int points = raf.readInt();
            int trofeos = raf.readInt();
            boolean activo = raf.readBoolean();
            usuarios.add(username, posicion);
        }
    }
    
    public boolean buscarUsername(String username) {
        return usuarios.search(username) != -1;
    }
    
    public boolean addUser(String username) throws IOException {
        if (buscarUsername(username)) {
            return false;
        }
        
        raf.seek(raf.length());
        long posicion = raf.getFilePointer();
        raf.writeUTF(username);
        raf.writeInt(0);
        raf.writeInt(0);
        raf.writeBoolean(true);
        usuarios.add(username, posicion);
        return true;
    }
    
    public void deactivateUser(String username) throws IOException {
        long posicion = usuarios.search(username);
        if (posicion != -1) {
            raf.seek(posicion);
            raf.readUTF();
            raf.readInt();
            raf.readInt();
            raf.writeBoolean(false);
        }
    }
    
    public void addTrophieTo(String username, String trophyGame, String trophyName, 
            Trophy type) throws IOException {
        long posicion = usuarios.search(username);
        if (posicion != -1) {
            raf.seek(posicion);
            raf.readUTF();
            int points = raf.readInt();
            int trofeos = raf.readInt();
            boolean activo = raf.readBoolean();
            
            if (!activo) {
                System.out.println("El usuario se encuentra desactivado");
                return;
            }
            
            points += type.points;
            trofeos++;
            raf.seek(posicion + username.length() + 2);
            raf.writeInt(points);
            raf.writeInt(trofeos);
            
            try (RandomAccessFile trofeosFile = new RandomAccessFile("Trofeos.ply", "rw")) {
                trofeosFile.seek(trofeosFile.length());
                trofeosFile.writeUTF(username);
                trofeosFile.writeUTF(type.name());
                trofeosFile.writeUTF(trophyGame);
                trofeosFile.writeUTF(trophyName);
                SimpleDateFormat fechaFormateada = new SimpleDateFormat("dd/MM/yyyy");
                trofeosFile.writeUTF(fechaFormateada.format(new Date()));
            }
        }
    }
    
    public String playerInfo(String username) throws IOException {
        long posicion = usuarios.search(username);
        StringBuilder infoTxt = new StringBuilder();
        
        if (posicion != -1) {
            raf.seek(posicion);
            String nombre = raf.readUTF();
            infoTxt.append("Usuario: ").append(nombre).append("\n");
            int points = raf.readInt();
            infoTxt.append("Puntos: ").append(points).append("\n");
            int trofeos = raf.readInt();
            boolean estaActivo = raf.readBoolean();
            infoTxt.append("Activo: ").append(estaActivo ? "Si" : "No").append("\n");
            infoTxt.append("Cantidad de trofeos: ").append(trofeos).append("\n");
            infoTxt.append("\nTrofeos:\n");
            
            try (RandomAccessFile trofeosFile = new RandomAccessFile("Trofeos.ply", "rw")) {
                trofeosFile.seek(0);
                while (trofeosFile.getFilePointer() < trofeosFile.length()) {
                    if (trofeosFile.readUTF().equals(username)) {
                        String tipo = trofeosFile.readUTF();
                        String juego = trofeosFile.readUTF();
                        String descripcion = trofeosFile.readUTF();
                        String fecha = trofeosFile.readUTF();
                        infoTxt.append(fecha).append(" - ").append(tipo).append(" - ").append(juego)
                            .append(" - ").append(descripcion).append("\n");
                    } else {
                        trofeosFile.readUTF();
                        trofeosFile.readUTF();
                        trofeosFile.readUTF();
                        trofeosFile.readUTF();
                    }
                }
            }
        } else {
            return "El usuario no fue encontrado";
        }
        return infoTxt.toString();
    }
}
