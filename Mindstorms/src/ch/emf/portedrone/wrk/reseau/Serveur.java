package ch.emf.portedrone.wrk.reseau;

import ch.emf.portedrone.beans.mindstorms.DeplacementMindstorms;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe serveur de l'application.
 * @author PeclatJ
 */
public class Serveur extends Thread {

    /**
     * Constructeur de la classe, initialise le serveur.
     * @param ecouteur L'écouteur du serveur.
     */
    public Serveur(IEcouteurServeur ecouteur) {
        super();
        this.ecouteur = ecouteur;
        connexion = false;
        try {
            serveur = new ServerSocket(PORT);
        } catch (IOException ex) {
            System.out.println("Impossible d'initialiser le serveur.");
            exit = true;
        }
    }

    @Override
    public void run() {
        while (!exit) {
            try {
                client = serveur.accept();
                System.out.println("Client accepté");
                try {
                    out = new ObjectOutputStream(client.getOutputStream());
                    in = new ObjectInputStream(client.getInputStream());
                    connexion = true;
                    while (connexion) {
                        try {
                            switch (in.readInt()) {
                                case 0:
                                    ecouteur.reseauDeplacementMindstorms((DeplacementMindstorms) in.readObject());
                                    break;
                                default:
                                    System.out.println("Le message a ete mal envoye.");
                            }
                            System.out.println("l'attente est finie");
                        } catch (IOException ex) {
                            System.out.println("Le flux entrant TCP a été interrompu.");
                            connexion = false;
                        } catch (ClassNotFoundException ex) {
                            System.out.println("Le message n'est pas coherant.");
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("Impossible de récuperer le flux sortant.");
                    connexion = false;
                }
            } catch (IOException ex) {
                System.out.println("Impossible d'accepter des clients.");
                exit = true;
                connexion = false;
            }
        }
    }

    /**
     * Indique au serveur de fermer les connexion.
     */
    public void exit() {
        exit = true;
        try {
            if(serveur != null) {
                serveur.close();
                in.close();
            }
        } catch (IOException ex) {
            System.out.println("Impossible de fermer le serveur.");
        }
    }

    /**
     * Permet d'envoyer un objet au client.
     * @param type L'id de l'objet à envoyer.
     * @param objet L'objet à envoyer.
     */
    public void ecrireObjet(int type, Object objet) {
        if (!exit && connexion) {
            try {
                out.writeInt(type);
                out.writeObject(objet);
                out.flush();
                System.out.println("Est écrit");
            } catch (IOException ex) {
                connexion = false;
            }
        }
    }
    
    private static final int PORT = 4000;
    
    private IEcouteurServeur ecouteur;
    private ServerSocket serveur;
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean exit;
    private boolean connexion;
}
