package com.company;

import com.company.factory.HeroFactory;
import com.company.factory.ISkill;
import com.company.factory.hero.Camille;
import com.company.factory.hero.Diana;
import com.company.factory.hero.Irelia;

import java.util.Scanner;

public class Main {

//    public static void main(String[] args) {
//        String name = Main.getPlayerInput();
//        switch (name) {
//            case "Diana":
//                Diana diana = new Diana();
//                diana.r();
//            case "Irelia":
//                Irelia irelia = new Irelia();
//                irelia.r();
//            case "Camille":
//                Camille camille = new Camille();
//                camille.r();
//        }
//    }
//    public static void main(String[] args) throws Exception {
//        String name = Main.getPlayerInput();
//        ISkill iSkill;
//        switch (name) {
//            case "Diana":
//                iSkill = new Diana();
//                break;
//            case "Irelia":
//                iSkill = new Irelia();
//                break;
//            case "Camille":
//                iSkill = new Camille();
//                break;
//            default:
//                throw new Exception();
//        }
//        iSkill.r();
//    }
    public static void main(String[] args) throws Exception {
        String name = Main.getPlayerInput();
        ISkill iSkill = HeroFactory.getHero(name);
        iSkill.r();
    }
    public static String getPlayerInput() {
        System.out.println("Enter a Hero Name: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
