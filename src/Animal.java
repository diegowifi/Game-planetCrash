/**
 * Animal
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Animal</code>
 *
 * @author Antonio Mejorado
 * @version 2.0 2015/1/15
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;

public class Animal {

    private int iX;     //posicion en x.       
    private int iY;     //posicion en y.
    private ImageIcon imiIcono;	//icono.

    /**
     * Animal
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public Animal(int iX, int iY ,Image imaImagen) {
        this.iX = iX;
        this.iY = iY;
        imiIcono = new ImageIcon(imaImagen);
    }

    /**
     * Animal
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono de imagen de un objeto igual
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * @param iY es la <code>posicion en y</code> del objeto.
     * @param imiIcono es la <code>imagen tipo icono</code> del objeto.
     * 
     */
    public Animal(int iX, int iY ,ImageIcon imiIcono) {
        this.iX = iX;
        this.iY = iY;
        this.imiIcono = imiIcono;
    }
    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int iX) {
        this.iX = iX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return iX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
            return iX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param iY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int iY) {
            this.iY = iY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return iY;
    }

    /**
     * setImageIcon
     * 
     * Metodo modificador usado para cambiar el icono del objeto
     * 
     * @param imiIcono es el <code>icono</code> del objeto.
     * 
     */
    public void setImageIcon(ImageIcon imiIcono) {
        this.imiIcono = imiIcono;
    }

    /**
     * getImageIcon
     * 
     * Metodo de acceso que regresa el icono del objeto 
     * 
     * @return imiIcono es el <code>icono</code> del objeto.
     * 
     */
    public ImageIcon getImageIcon() {
        return imiIcono;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imiIcono = new ImageIcon(imaImagen);
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imiIcono.getImage();
    }

    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho del icono.
     * 
     */
    public int getAncho() {
        return imiIcono.getIconWidth();
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto del icono.
     * 
     */
    public int getAlto() {
        return imiIcono.getIconHeight();
    }
    
    /**
     * paint
     * 
     * Metodo para pintar el animal
     * 
     * @param graGrafico    objeto de la clase <code>Graphics</code> para graficar
     * @param imoObserver  objeto de la clase <code>ImageObserver</code> es el 
     *    Applet donde se pintara
     * 
     */
    public void paint(Graphics graGrafico, ImageObserver imoObserver) {
        graGrafico.drawImage(getImagen(), getX(), getY(), imoObserver);
    }
    
    /*
     * intersecta
     *
     * Metodo que checa si un animal intersecta a otro
     *
     * @param objObjeto es un objeto de la clase <code>Object</code>
     * @return un boleano para saber si intersecta o no
     */
    public boolean intersecta(Object objObjeto) {
        if (objObjeto instanceof Animal) {
            Rectangle rctEsteObjeto = new Rectangle(this.getX(), this.getY(),
                    this.getAncho(), this.getAlto());
            Animal aniObjeto = (Animal) objObjeto;
            Rectangle rctObjetoParam = new Rectangle(aniObjeto.getX(),
                    aniObjeto.getY(), aniObjeto.getAncho(), aniObjeto.getAlto());
            return rctEsteObjeto.intersects(rctObjetoParam);
        } 
        else {
            return false;
        }
    }
}