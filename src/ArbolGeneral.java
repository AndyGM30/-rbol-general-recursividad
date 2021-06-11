/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andet
 */
public class ArbolGeneral {
    NodoGeneral raiz;
    
    public ArbolGeneral(){
        raiz = null;
    }
    
    public boolean insertar(char dato,String path){
        /*
        1. raiz es null
        2. path vacío
        3. buscar padre
        4. crear hijo (siempre q no exista otro hijo con el mismo nombre)
        5. enlazar padre con hijo
        */
        if(raiz == null){
            raiz = new NodoGeneral(dato);
            if(raiz == null) return false;
            return true;
        }
        if(path.isEmpty()) return false;
        
        NodoGeneral padre = buscarNodo(path);
        if(padre == null) return false;
        
        //Revisar si existe un hijo con la misma letra q deseo insertar
        NodoGeneral hijoYaExiste = buscarNodo(path + "/" + dato);
        //Si existe el hijo es decir es diferente de null entonces retorna falso
        if(hijoYaExiste != null) return false;
        
        NodoGeneral nuevo = new NodoGeneral(dato);
        return padre.enlazar(nuevo);
    }

    private NodoGeneral buscarNodo(String path) {
        path = path.substring(1);
        String vector[] = path.split("/");
        
        if(vector[0].charAt(0) == raiz.dato){
           //El vector solo contiene 1 letra es decir solo hay RAIZ como padre
           if(vector.length == 1) return raiz;
           NodoGeneral padre = raiz;
           for(int i = 1; i<vector.length; i++){
               padre = padre.obtenerHijo(vector[i].charAt(0));
               if(padre == null) return null;
           }
           return padre;
        }
        return null; //No coincidió celdilla 0 con raíz
    }
    
    private NodoGeneral buscarNodo(NodoGeneral nodoEncontrado, String path){
        //CASO BASE
        if(path.isEmpty()){
            return nodoEncontrado;
        }
        // /w/a/f/r   /a/f  f
        
        path = path.substring(1);
        String[] vector;
        if(path.length() == 1){
            vector = new String[1];
            vector[0] = path;
        }else{
            vector = path.split("/");
        }
        
        for(NodoHijo temp = nodoEncontrado.ini; temp != null; temp = temp.sig){
            if(temp.direccionHijo.dato == vector[0].charAt(0)){
                buscarNodo(temp.direccionHijo,path.substring(1));
            }
        }
        
        return null;
    }
    
    public boolean eliminar(String path){
        if(raiz == null) return false;
        
        NodoGeneral hijo = buscarNodo(path);
        if(hijo == null) return false;
        
        if(!hijo.esHoja()) return false;
        
        if(hijo == raiz){
            raiz = null;
            return true;
        }
        String pathPadre = obtenerPathPadre(path);
        NodoGeneral padre = buscarNodo(path);
        
        return padre.desenlazar(hijo);
    }

    private String obtenerPathPadre(String path) {
        int posicionANTESUltimaDiagonal = path.lastIndexOf("/")-1;
        return path.substring(0,posicionANTESUltimaDiagonal);
    }
    
}
