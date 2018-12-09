import java.util.ArrayList;
/**
 * Beschreiben Sie hier die Klasse Transport.
 * 
 * @author Willner 
 * @version 1
 */
public class Transport
{
    private ArrayList<Lieferung> lieferungen;
    private int transportID;
    /**
     * Konstruktor für Objekte der Klasse Transport
     */
    public Transport(int transportID)
    {
        lieferungen = new ArrayList<Lieferung>();
        this.transportID = transportID;
    }
    
    /**
     * Konstruktor für Objekte der Klasse Transport
     */
    public Transport(int transportID, ArrayList<Lieferung> lieferungen)
    {
        this.lieferungen = lieferungen;
        this.transportID = transportID;
    }
    
    public int gibTransportID()
    {
        return transportID;
    }
    
    public void neueLieferung(Lieferung l)
    {
        lieferungen.add(l);
    }
    
    public void schreibeInfos()
    {
        System.out.println("Transport ID: " + transportID);
        for(Lieferung l: lieferungen){
            l.schreibeInfos();
        }
    }
    
    public Lieferung gibLieferung(int id)
    {
        for(Lieferung l: lieferungen){
            if(l.gibLieferungsID() == id)
                return l;
        }
        return null;
    }
    
    public ArrayList<Lieferung> gibLieferungen()
    {
        return lieferungen;
    }
    
    public void neueLieferungsListe(ArrayList<Lieferung> neu)
    {
        lieferungen = neu;
    }
}
