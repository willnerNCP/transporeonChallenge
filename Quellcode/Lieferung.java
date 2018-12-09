
/**
 * Beschreiben Sie hier die Klasse Lieferung.
 * 
 * @author Willner 
 * @version 1
 */
public class Lieferung
{
    private Lieferstelle beladeStelle;
    private Lieferstelle entladeStelle;
    private int lieferungsID;
    /**
     * Konstruktor für Objekte der Klasse Lieferung
     */
    public Lieferung(Lieferstelle beladeStelle, Lieferstelle entladeStelle, int lieferungsID)
    {
        this.beladeStelle = beladeStelle;
        this.entladeStelle = entladeStelle;
        this.lieferungsID = lieferungsID;
    }
    
    public Lieferstelle gibBeladeStelle()
    {
        return beladeStelle;
    }
    
    public Lieferstelle gibEntladeStelle()
    {
        return entladeStelle;
    }
    
    public int gibLieferungsID()
    {
        return lieferungsID;
    }
    
    public void schreibeInfos()
    {
        System.out.println("Lieferungs ID: " + lieferungsID);
        System.out.println("Beladestelle:       " + beladeStelle.gibInfos());
        System.out.println("Entladestelle:      " + entladeStelle.gibInfos());
    }
}
