
/**
 * Beschreiben Sie hier die Klasse Lieferstelle.
 * 
 * @author Willner 
 * @version 1
 */
public class Lieferstelle
{
    private String adresse;
    private float longitude, latitude;
    /**
     * Konstruktor für Objekte der Klasse Lieferstelle
     */
    public Lieferstelle(String adresse, float latitude, float longitude)
    {
        this.adresse = adresse;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    
    public String gibAdresse()
    {
        return adresse;
    }
    
    public float gibLongitude()
    {
        return longitude;
    }
    
    public float gibLatitude()
    {
        return latitude;
    }
    
    public String gibInfos()
    {
        return "Adresse: "+adresse+ "   Latitude: "+latitude + "   Longitude: "+ longitude;
    }
}
