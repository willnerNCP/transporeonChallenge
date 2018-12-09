import java.util.ArrayList;
import java.util.Arrays;

/**
 * Stellt einen Kunden dar.
 * 
 * @author Willner 
 * @version 1
 */
public class Kunde
{
    private String name;
    private ArrayList<Transport> transporte;
    
    /**
     * Konstruktor für Objekte der Klasse Kunde
     * @
     */
    public Kunde(String name)
    {
        this.name = name;
        this.transporte = new ArrayList<Transport>();
    }
    
    /**
     * Getter für den Namen des Kundens
     */
    public String gibName()
    {
        return name;
    }
    
    /**
     *  
     */
    public void neuerTransport(Transport t)
    {
        transporte.add(t);
    }
    
    public ArrayList<Transport> gibTransporte()
    {
        return transporte;
    }

    public void schreibeInfos()
    {
        System.out.println("Kunde: " + name);
        for(Transport t: transporte){
            t.schreibeInfos();
            System.out.println();
        }
    }
        
    public Transport gibTransport(int id)
    {
        for(Transport t: transporte){
            if(t.gibTransportID() == id)
                return t;
        }
        return null;
    }
}
