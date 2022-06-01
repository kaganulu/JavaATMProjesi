/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sınıflar;

import java.util.Scanner;

/**
 *
 * @author kaganulu
 */
public class Sınıflar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       /*   FileUtilities fileUtil = new FileUtilities();
        String[] kelimeler = "Lorem ipsum dolor sit amet".split(" ");
       fileUtil.writeFile("asd.txt", kelimeler);*/
        
        FileUtilities fileUtils = new FileUtilities();
        MenuUtilities menu = new MenuUtilities(fileUtils);
        menu.menuProcess();


       
    }

}
