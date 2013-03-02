package mygame;

import chair.input.XboxInputListener;
import chair.input.*;
import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.light.*;
import com.jme3.math.Vector3f;
import mygame.GameMenuHUD;
/**
 * test
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication
{

    public static Level l;
    static boolean startLevel = false;
    static boolean startCredits = false;
    private boolean hudTextNotSet = true;
    static OfficeChair[] chairArray = new OfficeChair[4];
    
    private BitmapText[] hudTexts;
    
    InputListener il;
    static Soundtrack s;

    public static void main(String[] args) {
        Main app = new Main();
        app.setPauseOnLostFocus(false);
        AppSettings settings = new AppSettings(true);
        settings.putBoolean("DisableJoysticks", false);
        app.setSettings(settings);
        app.start();
    }
    
    public static void startLevel(){
        startLevel = true;
    }
    
    public static void loadCredits(){
        
    }
    
    public static void setPlayers(OfficeChair[] officeChairs){
        for (int i = 0; officeChairs[i] != null; i++){
            chairArray[i] = officeChairs[i];
        }
    }
    
    
    public Nifty nifty;
    public static int gameState = 0;
    public void loadMainMenu() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.fromXml("Interface/XML/mainmenu.xml", "start");
        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
        // disable the fly cam
        flyCam.setEnabled(false);
    }
    

    @Override
    public void simpleInitApp() {



        
        setDisplayStatView(false);
        setDisplayFps(false);
        
       // GameCredits.creds = new GameCredits(audioRenderer, assetManager, inputManager, guiViewPort);
        
        loadMainMenu();

        il = new XboxInputListener(inputManager);
        
        s = new Soundtrack(assetManager, rootNode, audioRenderer);
        s.initAudio();
        s.playSong(gameState);
        
        
        
    }
    
    
    
    private Vector3f camMoveCredits = new Vector3f(0,2,0);
    
    
    @Override
    public void simpleUpdate(float tpf)
    {
        if(startLevel){
            
            //Set the HUD texts (run once).
            for (int i = 0; i < chairArray.length && hudTextNotSet; i++){
                hudTexts[i] = new BitmapText(guiFont, false);
                hudTexts[i].setSize(guiFont.getCharSet().getRenderedSize());      // font size
                hudTexts[i].setColor(ColorRGBA.Blue);                             // font color
                hudTexts[i].setText("Player " + i + " Helf: " + chairArray[i].getHealth());             // the text
                switch (i){
                    case 0:
                        hudTexts[i].setLocalTranslation(10, hudTexts[i].getLineHeight(), 0); // position
                        break;
                    case 1:
                        hudTexts[i].setLocalTranslation(500, hudTexts[i].getLineHeight(), 0); // position
                        break;
                    case 4:
                        hudTexts[i].setLocalTranslation(500, hudTexts[i].getLineHeight(), 0); // position
                        break;
                    case 3:
                        hudTexts[i].setLocalTranslation(500, hudTexts[i].getLineHeight(), 0); // position
                        break;
                }
                guiNode.attachChild(hudTexts[i]);
            }
            
            //Update HUD stats.
            for (int i = 0; i < chairArray.length; i++){
                hudTexts[i].setText("Player " + i + " Helf: " + chairArray[i].getHealth());
            }
            l = new Level(rootNode, assetManager, inputManager);
            startLevel = false;
            cam.setLocation(new Vector3f(0, 45, -40));
            cam.lookAt(new Vector3f(0, 0, 5), new Vector3f(0, 1, 0));
            AmbientLight light = new AmbientLight();
            light.setColor(ColorRGBA.White.mult(1.7f));
            rootNode.addLight(light);
        }
        
        
        if(startCredits){
            flyCam.setEnabled(true);
            
            
        }
        
        if(l != null){
            l.update(tpf);
        }

        s.playNewSong(gameState);
        //InputController controller = il.getInputControllers()[0];
        //controller.getLeftAxisDirection();
        //System.out.println("LS Angle = " + controller.getLeftAxisDirection() + " LS Power =" + controller.getLeftAxisPower());
        //System.out.println("RS Angle = " + controller.getRightAxisDirection() + " RS Power =" + controller.getRightAxisPower());
    }
    
    public static void changeAudio() {
        s.playSong(gameState);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

}
