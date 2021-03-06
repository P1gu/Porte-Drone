/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.emf.portedrone.ihm;

import ch.emf.portedrone.beans.Info;
import java.awt.image.BufferedImage;

/**
 *
 * @author ramosdasilm
 */
public interface IIhm {

    /**
     * permet de rendre visible ou masquer l'ihm.
     *
     * @param visible
     */
    void afficher(boolean visible);

    /**
     * permet d'afficher les info du drone.
     *
     * @param info
     */
    void afficherInfoDrone(Info info);

    /**
     * permte d'afficher la video du drone.
     *
     * @param img
     */
    void afficherCameraDrone(BufferedImage img);
}
