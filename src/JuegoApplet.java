/**
 * JuegoApplet
 *
 * Anima un elefante y con las flechas se puede mover
 *
 * @author Antonio Mejorado
 * @version 2.0 2015/1/15
 */
 
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class JuegoApplet extends Applet implements Runnable, KeyListener,
                                    MouseListener, MouseMotionListener {
    // Se declaran las variables y objetos
    // direccion en la que se mueve el elefante
    // 1-arriba,2-abajo,3-izquierda y 4-derecha
    private int iDireccion;
    private int iVelocidad;
    private int iVidas;
    private boolean bClick;  // para mover con el mouse
    private int iPosX; // guarda la x del mouse
    private int iPosY; // guarda la y del mouse
    private AudioClip aucSonidoElefante;     // Objeto AudioClip sonido Elefante
    private Animal aniDumbo;         // Objeto de la clase Elefante
    private Animal aniDumbo2;        // Objeto de la clase Elefante
    private Animal aniVida;         // Objeto de las vidas
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
	
    /** 
     * init
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos
     * a usarse en el <code>Applet</code> y se definen funcionalidades.
     * 
     */
    public void init() {
        // hago el applet de un tamaño 500,500
        setSize(900,500);
        
        iVidas=3;
        
        iVelocidad=1;
        
        // inicializo la boleana del mouse en falso
        bClick = false;
        
        //iniializo x y y del mouse
        iPosX = 0;
        iPosY = 0;
        
        // posicion en 4 para que el elefante se mueva a la derecha
    	//iDireccion = 4;
        
        // se posicion el elefante en alguna parte al azar del cuadrante 
        // superior izquierdo
	int iPosX = (int) (Math.random() *(getWidth() / 4));    
        int iPosY = (int) (Math.random() *(getHeight() / 4));    
	URL urlImagenElefante = this.getClass().getResource("planeta.png");
        URL urlImagenAsteroide = this.getClass().getResource("asteroide.png");
        URL urlImagenVida = this.getClass().getResource("vida.png");
        
        // se crea el objeto elefante 
	aniDumbo = new Animal(iPosX,iPosY,
                Toolkit.getDefaultToolkit().getImage(urlImagenElefante));
	iPosX = (int) (Math.random() *(3*getWidth() / 4));    
        iPosY = (int) (Math.random() *(3*getHeight() / 4));    

        // se crea el objeto elefante 2
	aniDumbo2 = new Animal(iPosX,iPosY,
                Toolkit.getDefaultToolkit().getImage(urlImagenAsteroide));
	//creo el sonido del elefante
	URL urlSonidoElefante = this.getClass().getResource("explosion.wav");
        aucSonidoElefante = getAudioClip (urlSonidoElefante);
        
        //se crean las vidas
        aniVida = new Animal(iPosX,iPosY,
                Toolkit.getDefaultToolkit().getImage(urlImagenVida));
	iPosX = (int) (Math.random() *(3*getWidth() / 4));    
        iPosY = (int) (Math.random() *(3*getHeight() / 4));

        // se define el background en color amarillo
	setBackground (Color.black);
        /* se le añade la opcion al applet de ser escuchado por los eventos
           del teclado  */
	addKeyListener(this);
	addMouseListener(this);
        addMouseMotionListener(this);
    }
	
    /** 
     * start
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo
     * para la animacion este metodo es llamado despues del init o 
     * cuando el usuario visita otra pagina y luego regresa a la pagina
     * en donde esta este <code>Applet</code>
     * 
     */
    public void start () {
        // Declaras un hilo
        Thread th = new Thread (this);
        // Empieza el hilo
        th.start ();
    }
	
    /** 
     * run
     * 
     * Metodo sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, que contendrá las instrucciones
     * de nuestro juego.
     * 
     */
    public void run () {
        /* mientras dure el juego, se actualizan posiciones de jugadores
           se checa si hubo colisiones para desaparecer jugadores o corregir
           movimientos y se vuelve a pintar todo
        */ 
        while (iVidas > 0) {
            actualiza();
            checaColision();
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError) {
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
	}
    }
	
    /** 
     * actualiza
     * 
     * Metodo que actualiza la posicion del objeto elefante 
     * 
     */
    public void actualiza(){
        //actualizo al 2 dependiendo de donde anda el1
        if (aniDumbo.getX() < aniDumbo2.getX()) {
            aniDumbo2.setX(aniDumbo2.getX() - iVelocidad);
        } 
        else {
            aniDumbo2.setX(aniDumbo2.getX() + iVelocidad);            
        }
        if (aniDumbo.getY() < aniDumbo2.getY()) {
            aniDumbo2.setY(aniDumbo2.getY() - iVelocidad);
        } 
        else {
            aniDumbo2.setY(aniDumbo2.getY() + iVelocidad);            
        }
        //Dependiendo de la iDireccion del elefante es hacia donde se mueve.
        switch(iDireccion) {
            case 1: { //se mueve hacia arriba
                aniDumbo.setY(aniDumbo.getY() - iVelocidad);
                break;    
            }
            case 2: { //se mueve hacia abajo
                aniDumbo.setY(aniDumbo.getY() + iVelocidad);
                break;    
            }
            case 3: { //se mueve hacia izquierda
                aniDumbo.setX(aniDumbo.getX() - iVelocidad);
                break;    
            }
            case 4: { //se mueve hacia derecha
                aniDumbo.setX(aniDumbo.getX() + iVelocidad);
                break;    	
            }
        }
        
        // Si se uso el mouse actualizo la posicion de dumbo
        if (bClick) {
            aniDumbo.setX(iPosX);
            aniDumbo.setY(iPosY);
            bClick = false;
        }
    }
	
    /**
     * checaColision
     * 
     * Metodo usado para checar la colision del objeto elefante
     * con las orillas del <code>Applet</code>.
     * 
     */
    public void checaColision(){
        //Colision del elefante con el Applet dependiendo a donde se mueve.
        switch(iDireccion){
            case 1: { // si se mueve hacia arriba 
                if(aniDumbo.getY() < 0) { // y esta pasando el limite
                    iDireccion = 2;     // se cambia la direccion para abajo
                    //aucSonidoElefante.play();   // llora el elefante
                }
                break;    	
            }     
            case 2: { // si se mueve hacia abajo
                // y se esta saliendo del applet
                if(aniDumbo.getY() + aniDumbo.getAlto() > getHeight()) {
                    iDireccion = 1;     // se cambia la direccion para arriba
                    //aucSonidoElefante.play();   // llora el elefante
                }
                break;    	
            } 
            case 3: { // si se mueve hacia izquierda 
                if(aniDumbo.getX() < 0) { // y se sale del applet
                    iDireccion = 4;       // se cambia la direccion a la derecha
                    //aucSonidoElefante.play();     // llora el elefante
                }
                break;    	
            }    
            case 4: { // si se mueve hacia derecha 
                // si se esta saliendo del applet
                if(aniDumbo.getX() + aniDumbo.getAncho() > getWidth()) { 
                    iDireccion = 3;       // se cambia direccion a la izquierda
                    //aucSonidoElefante.play();     // llora el elefante
                }
                break;    	
            }			
        }
        
        //checo la colision entre ambos elefantes
        if (aniDumbo.intersecta(aniDumbo2)) {
            aucSonidoElefante.play();
            aniDumbo2.setX(0);
            aniDumbo2.setY(0);
            iVelocidad++;
            iVidas--;
        }
    }
	
    /**
     * update
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor y 
     * define cuando usar ahora el paint
     * 
     * @param graGrafico es el <code>objeto grafico</code> usado para dibujar.
     * 
     */
    public void update (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }

        // Actualiza la imagen de fondo.
        graGraficaApplet.setColor (getBackground ());
        graGraficaApplet.fillRect (0, 0, this.getSize().width, 
                this.getSize().height);

        // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paint(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
    }

    /**
     * keyPressed
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al dejar presionada
     * alguna tecla.
     * 
     * @param keyEvent es el <code>KeyEvent</code> que se genera en al presionar.
     * 
     */
    public void keyPressed(KeyEvent keyEvent) {
        // si presiono flecha para arriba
        if(keyEvent.getKeyCode() == KeyEvent.VK_UP) {    
                iDireccion = 1;  // cambio la dirección arriba
        }
        // si presiono flecha para abajo
        else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {    
                iDireccion = 2;   // cambio la direccion para abajo
        }
        // si presiono flecha a la izquierda
        else if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {    
                iDireccion = 3;   // cambio la direccion a la izquierda
        }
        // si presiono flecha a la derecha
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT){    
                iDireccion = 4;   // cambio la direccion a la derecha
        }
    }
    
    /**
     * keyTyped
     * 
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una 
     * tecla que no es de accion.
     * 
     * @param keyEvent es el <code>KeyEvent</code> que se genera en al presionar.
     * 
     */
    public void keyTyped(KeyEvent keyEvent){
    	// no hay codigo pero se debe escribir el metodo
    }
    
    /**
     * keyReleased
     * Metodo sobrescrito de la interface <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla.
     * 
     * @param keyEvent es el <code>KeyEvent</code> que se genera en al soltar.
     * 
     */
    public void keyReleased(KeyEvent keyEvent){
    	// no hay codigo pero se debe escribir el metodo
    }
    
    /**
     * paint
     * 
     * Metodo sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada,
     * ademas que cuando la imagen es cargada te despliega una advertencia.
     * 
     * @param graDibujo es el objeto de <code>Graphics</code> usado para dibujar.
     * 
     */
    public void paint(Graphics graDibujo) {
        // si la imagen ya se cargo
        if (aniDumbo != null && aniDumbo2 != null) {
                //Dibuja la imagen de dumbo en el Applet
                aniDumbo.paint(graDibujo, this);
                //Dibuja la imagen de dumbo en el Applet
                aniDumbo2.paint(graDibujo, this);
        } // sino se ha cargado se dibuja un mensaje 
        else {
                //Da un mensaje mientras se carga el dibujo	
                graDibujo.drawString("No se cargo la imagen..", 20, 20);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mseEvent) {
        // prendo boleana del clic y actualizo posiciones
        bClick = true;
        iPosX = mseEvent.getX();
        iPosY = mseEvent.getY();
    }

    @Override
    public void mousePressed(MouseEvent mseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mseEvent) {
        bClick = true;
        iPosX = mseEvent.getX();
        iPosY = mseEvent.getY();
    }

    @Override
    public void mouseMoved(MouseEvent mseEvent) {
        bClick = true;
        iPosX = mseEvent.getX();
        iPosY = mseEvent.getY();
    }
}