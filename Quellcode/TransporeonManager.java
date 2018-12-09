import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Beschreiben Sie hier die Klasse TransporeonManager.
 * 
 * @author Willner 
 * @version 1
 */
public class TransporeonManager
{
    private String workingDir;
    private ArrayList<Lieferstelle> lieferstellen;
    private ArrayList<Kunde> kunden;

    private Kunde testKunde;

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Konstruktor für Objekte der Klasse TransporeonManager
     */
    public TransporeonManager()
    {
        lieferstellen = new ArrayList<Lieferstelle>();
        kunden = new ArrayList<Kunde>();
        workingDir = System.getProperty("user.dir");
        try{auftragEinlesen("Kunde1");} 
        catch(IOException e){
            System.out.println("Error: Couldnt read Kunde1.txt");
            e.printStackTrace();
            System.exit(0);
        }
        datenAusgeben();
        start();
    }
    
    public static void main(String[] args)
    {
        new TransporeonManager();
    }

    public void start() 
    {
        while(true){
            System.out.print("Bitte wählen Sie eine Option: ");
            char c = Character.toLowerCase(scanner.next().charAt(0));
            if(c == 'x'){
                System.exit(0);
            }
            else if(c == 'l'){
                for(Transport t: testKunde.gibTransporte()){
                    for(Lieferung l: t.gibLieferungen())
                        System.out.println("Lieferung von ["+l.gibBeladeStelle().gibAdresse()+"] nach ["+l.gibEntladeStelle().gibAdresse()+"]: "+distanzLieferung(l)+" km");
                }
            }
            else if(c == 't'){
                for(Transport t: testKunde.gibTransporte()){
                    t.schreibeInfos();                    
                    System.out.println(gesamtDistanzTransport(t)+" km");
                    System.out.println();
                }
            }
            else if(c == 'g'){
                System.out.println(gesamtDistanz(testKunde)+" km");
            }
            else if(c == 'o'){
                for(Transport t: testKunde.gibTransporte()){
                    float alteDistanz = gesamtDistanzTransport(t);
                    optimiereTransport(t);
                    t.schreibeInfos();                    
                    System.out.println("Transport wurde optimiert von "+alteDistanz+" km zu "+ gesamtDistanzTransport(t));
                    System.out.println();
                }
            }
            else{
                System.out.println("Fehler: Unbekannter Befehl!");
            }
        }
    }

    public float gesamtDistanz(Kunde k)
    {
        float gesamt = 0;
        for(Transport t: k.gibTransporte()){
            gesamt += gesamtDistanzTransport(t);
        }
        return gesamt;
    }

    public float gesamtDistanzTransport(Kunde k, int transportID)
    {
        return gesamtDistanzTransport(k.gibTransport(transportID));
    }

    public float gesamtDistanzTransport(Transport transport)
    {
        float distanz = 0;
        Lieferstelle vorherigeEntladeStelle = null;
        for(Lieferung l: transport.gibLieferungen()){
            distanz += distanzLieferung(new Lieferung(vorherigeEntladeStelle, l.gibBeladeStelle(), -1));
            distanz += distanzLieferung(l);
            vorherigeEntladeStelle = l.gibEntladeStelle();
        }
        return distanz;
    }

    private float distanzLieferung(Kunde k, int transportID, int lieferungsID)
    {
        return distanzLieferung(k.gibTransport(transportID).gibLieferung(lieferungsID));
    }

    private float distanzLieferung(Lieferung lieferung)
    {
        Lieferstelle lA = lieferung.gibBeladeStelle();
        Lieferstelle lB = lieferung.gibEntladeStelle();
        if(lA == null || lB == null || lA.equals(lB))return 0;
        double latA = Math.toRadians(lA.gibLatitude());
        double latB = Math.toRadians(lB.gibLatitude());
        double lonA = Math.toRadians(lA.gibLongitude());
        double lonB = Math.toRadians(lB.gibLongitude());
        double xA = Math.cos(latA)*Math.sin(lonA);
        double yA = Math.cos(latA)*Math.cos(lonA);
        double zA = Math.sin(latA);
        double xB = Math.cos(latB)*Math.sin(lonB);
        double yB = Math.cos(latB)*Math.cos(lonB);
        double zB = Math.sin(latB);
        double luftlinie = 6371*Math.sqrt(Math.pow(xA-xB,2) + Math.pow(yA-yB,2) + Math.pow(zA-zB,2));
        luftlinie = Math.round(luftlinie*100)/100.0;
        return (float)luftlinie;
    }

    private Lieferstelle gibLieferstelle(String adresse)
    {
        for(Lieferstelle l: lieferstellen){
            if(l.gibAdresse().equals(adresse))
                return l;
        }
        return null;
    }

    private Kunde gibKunde(String name)
    {
        for(Kunde k: kunden){
            if(k.gibName().equals(name))
                return k;
        }
        return null;
    }

    private void datenAusgeben()
    {
        System.out.println("Informationen:");
        System.out.println("1. Die Anwendung geht davon aus, dass alle Eintraege in testdata zum selben Kunden gehoeren.");
        System.out.println("2. Die Anwendung geht davon aus, dass mit der Distanz zwischen Beladestelle der ersten");
        System.out.println("Lieferstelle und Entladestelle der letzten Lieferung nicht der direkte Weg zwischen beiden");
        System.out.println("gesucht ist, sondern die Strecke, wenn alle Lieferstellen im Transport nacheinander");
        System.out.println("besucht werden. [Aufgabe 2]");
        System.out.println();
        System.out.println("------------------EINGELESENE DATEN------------------");
        for(Kunde k: kunden){
            k.schreibeInfos();
        }
        System.out.println("------------------------------------------------------");
        System.out.println();
        System.out.println("[L] fuer die Distanz aller Einzellieferungen des Kundens");
        System.out.println("[T] fuer die Distanz aller Einzeltransporte des Kundens");
        System.out.println("[G] fuer die Gesamtdistanz aller Transporte des Kundens");
        System.out.println("[O] zum Optimieren aller Einzeltransporte des Kundens");
        System.out.println("[X] zum Beenden");
    }

    private void auftragEinlesen(String kundenName) throws IOException
    {        
        //Uberprüft ob es diesen Kunden schon gibt
        Kunde kunde;
        if((kunde = gibKunde(kundenName)) == null)
            kunden.add(kunde = new Kunde(kundenName));
        testKunde = kunde;
        InputStream input = new FileInputStream(new File(workingDir+"\\"+ kundenName +".txt"));
        Scanner sc = new Scanner(input);
        sc.useDelimiter(";");
        while(sc.hasNextLine()){
            int transportID = Integer.parseInt(sc.next());
            Transport transport;
            //Überprüft ob der Kunde schon diesen Transport hat
            if((transport = kunde.gibTransport(transportID)) == null)
                kunde.neuerTransport(transport = new Transport(transportID));

            int lieferungsID = Integer.parseInt(sc.next());

            String adresseVon = sc.next();
            float latVon = Float.parseFloat(sc.next());
            float lonVon = Float.parseFloat(sc.next());
            Lieferstelle von;
            //Überprüft ob es diese Lieferstelle schon gibt, um Redundanz zu vermeiden
            if((von = gibLieferstelle(adresseVon)) == null)
                lieferstellen.add(von = new Lieferstelle(adresseVon, latVon, lonVon));

            String adresseZu = sc.next();
            float latZu = Float.parseFloat(sc.next());
            float lonZu = Float.parseFloat(sc.next());
            Lieferstelle zu;
            if((zu = gibLieferstelle(adresseZu)) == null)
                lieferstellen.add(zu = new Lieferstelle(adresseZu, latZu, lonZu));

            Lieferung lieferung = new Lieferung(von, zu, lieferungsID);
            transport.neueLieferung(lieferung);
            sc.nextLine();
        }
    }
    
    private void optimiereTransport(Transport t) 
    {
        ArrayList<Lieferung> list = t.gibLieferungen();
        if (list.size() == 1) 
            return; 
        ArrayList<Lieferung>shortest = (ArrayList<Lieferung>)list.clone();
        float shortestDistance = gesamtDistanzTransport(t);
        int[] buffer = new int[list.size()];
        float distance;
        for(int i = 0; i < list.size(); i++){
            if(buffer[i] < i){
                if(i%2 == 0){
                    Collections.swap(list, 0, i);
                }
                else{
                    Collections.swap(list, buffer[i], i);
                }
                if((distance = gesamtDistanzTransport(t)) < shortestDistance){
                    shortest = (ArrayList<Lieferung>)list.clone();
                    shortestDistance = distance;
                }
                buffer[i]++;
                i = -1;
            }
            else{
                buffer[i] = 0;
            }
        }
        t.neueLieferungsListe(shortest);
    } 
}
