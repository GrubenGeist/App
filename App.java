package App;

import java.awt.Point;
import java.util.Random;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        //Objekte und Spielfeld
        // x = Hoch und Runter < / y= links rechts <
        Point spielerPosition = new Point(10, 8);
        Point powerUpPosition = new Point(33, 7);
        Point alienPosition = new Point(1, 5);
        Point alien2Position = new Point(-100, -100);
        Point frachtPosition = new Point(10, 11);
        Point erdePosition = new Point(6, 9);
        boolean schleife = true;
        boolean frachtEingesammelt = false;
        boolean powerUPEingesammelt = false;
        int credits = 0;
        int hoehe = 20;
        int breite = 40;

        //spielfeld
        while (schleife) {
            if (schleife = true) {
                // Clear the console
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println("╔══════════════════════════════════════════════════════════════════╗");
            System.out.println(" ");
            System.out.println("Versuche, mit dem Player (P), mithilfe der W, A, S, D + ENTER Tasten,");
            System.out.println("die Fracht (F) auf die Erde (E) zu bringen,");
            System.out.println("ohne von Alien (A) & (M) erwischt zu werden.");
            System.out.println("(§) Powerup, lässt das Alien (A) an einer anderen Stelle spawnen.");
            System.out.println(" ");
            System.out.println("╚══════════════════════════════════════════════════════════════════╝");
            System.out.println(" ");
            System.out.println("╔════════════════════════════════════════╗");

            //Punkte erstellung für den spielinhalt
            for (int y = 0; y < hoehe; y++) {
                System.out.print("║");
                for (int x = 0; x < breite; x++) {
                    Point p = new Point(x, y);
                    if (p.equals(spielerPosition)) {
                        System.out.print("P");
                    } else if (p.equals(alienPosition)) {
                        System.out.print("A");
                    } else if (p.equals(alien2Position)) {
                        System.out.print("M");
                    } else if (p.equals(frachtPosition)) {
                        System.out.print("F");
                    } else if (p.equals(erdePosition)) {
                        System.out.print("E");
                    } else if (p.equals(powerUpPosition)) {
                        System.out.print("§");
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println("║");

            }
            System.out.println("╚════════════════════════════════════════╝");
            if (true) {

            }
            if (alienPosition.equals(spielerPosition)) {
                credits = 0;
            }
            System.out.println("Punkte gesamt: " + credits);

            //Spiellogik
            //╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
            if (alienPosition.equals(spielerPosition)) {
                credits = 0;
                System.err.println("Ich hab dich Kleines Menschlein!");
                System.err.println("Du bist *** [ GAME OVER! ] ***");
                break;
            }
            if (alien2Position.equals(spielerPosition)) {
                credits = 0;
                System.err.println("Ich hab dich Kleines Menschlein!");
                System.err.println("Du bist *** [ GAME OVER! ] ***");
                break;
            }
            if (spielerPosition.equals(frachtPosition)) {
                frachtEingesammelt = true;
                frachtPosition = new Point(-1, -1);
                System.out.println("Fracht ist an Board des Schiffes! +1 Frachtpunkt");
                credits += 1;
                if (credits > 5) {
                    alien2Position = new Point(11, 15);
                }
                // Generiere neue Frachtposition (z.B. mit Zufallszahlen)
                frachtPosition = generiereNeueFrachtPosition(spielerPosition, alienPosition, erdePosition, hoehe, breite);
            }
            if (spielerPosition.equals(erdePosition)) {

                if (credits < 1) {
                    System.out.println("Willkommen auf der Erde!");
                    System.out.println("Aber dir fehlt die Fracht =(  ");
                }
                if (spielerPosition.equals(erdePosition) && frachtEingesammelt) {

                    if (credits > 0) {
                        schleife = false;
                        System.out.println(" # *** GEWONNEN *** # ");
                        System.out.println("Willkommen auf der Erde, du hast es Geschafft!");
                        break;
                    }
                }
            }

            if (spielerPosition.equals(powerUpPosition)) {
                powerUPEingesammelt = true;
                powerUpPosition = new Point(-1, -1);
                System.out.println("Powerup eingesammelt, Alien für 5 schritte Gefreezet");

                // Generiere neue Alien-Position
                alienPosition = genNewAlienPosition(spielerPosition, alienPosition, alien2Position, powerUpPosition, hoehe, breite, credits);

                // Generiere neue Power-Up-Position
                powerUpPosition = generiereNeuePowerUpPosition(spielerPosition, alienPosition, erdePosition, hoehe, breite);
            }

            if (alienPosition.equals(erdePosition)) {

                System.out.println("ALARM ! Alien Invasion auf der Erde ");
                System.out.println("Das ist eine verdammt harte Galaxis. Wenn man hier überleben will, muss man immer wissen, wo sein Handtuch ist.");
            }
            //╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝
            bewegeSpieler(spielerPosition);
            bewegeAlien(alienPosition, alien2Position, spielerPosition, breite, hoehe, credits, powerUPEingesammelt);
        }
    }

    //input einlesen
    private static void bewegeAlien(Point alienPosition, Point alien2Position, Point spielerPosition, int breite, int hoehe, int credits, boolean powerUPEingesammelt) {
        Random random = new Random();

        // Wahrscheinlichkeiten für jede Richtung (0-100, Summe = 100)
        int chanceLinks = 30;
        int chanceRechts = 30;
        int chanceOben = 20;
        int chanceUnten = 20;
        int zufallszahl = random.nextInt(100);

        if (zufallszahl < chanceLinks && alienPosition.x > 0) {
            alienPosition.x--;
        } else if (zufallszahl < chanceLinks + chanceRechts && alienPosition.x < breite - 1) {
            alienPosition.x++;
        } else if (zufallszahl < chanceLinks + chanceRechts + chanceOben && alienPosition.y > 0) {
            alienPosition.y--;
        } else if (zufallszahl < chanceLinks + chanceRechts + chanceUnten && alienPosition.y > 0) {
            alienPosition.y--;
        } else {
            if (alienPosition.y < hoehe - 1) {
                alienPosition.y++;
            }
        }

        if (zufallszahl < chanceLinks && alien2Position.x > 0) {
            alien2Position.x--;
        } else if (zufallszahl < chanceLinks + chanceRechts && alien2Position.x < breite - 1) {
            alien2Position.x++;
        } else if (zufallszahl < chanceLinks + chanceRechts + chanceOben && alien2Position.y > 0) {
            alien2Position.y--;
        } else if (zufallszahl < chanceLinks + chanceRechts + chanceUnten && alien2Position.y > 0) {
            alien2Position.y--;
        } else {
            if (alien2Position.y < hoehe - 1) {
                alien2Position.y++;
            }
        }
// Annäherung an den Spieler priorisieren
        if (credits > 5) {
            if (spielerPosition.x < alienPosition.x) {
                alienPosition.x--;
            } else if (spielerPosition.x > alienPosition.x) {
                alienPosition.x++;
            }
        }
        if (credits > 10) {
            if (spielerPosition.x < alienPosition.x) {
                alienPosition.x--;
            } else if (spielerPosition.x > alienPosition.x) {
                alienPosition.x++;
            } else if (spielerPosition.y > alienPosition.x) {
                alienPosition.y++;
            } else if (spielerPosition.y > alienPosition.x) {
                alienPosition.y--;
            }
        }

    }

    public static void bewegeSpieler(Point spielerPosition) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        String input = scan.next();

        //Nutzer eingabe W,A,S,D
        switch (input) {
            case "w":
                if (spielerPosition.y > 0) {
                    spielerPosition.y--;

                }
                break;
            case "s":
                if (spielerPosition.y < 19) {
                    spielerPosition.y++;
                }
                break;
            case "a":
                if (spielerPosition.x > 0) {
                    spielerPosition.x--;
                }
                break;
            case "d":
                if (spielerPosition.x < 39) {
                    spielerPosition.x++;
                }
                break;
            default:
                break;
        }

    }

    public static void punktestand(Point spielerPosition, Point alienPosition, Point erdePosition, Point frachtPosition, int credits) {
        if (spielerPosition.equals(frachtPosition)) {
            credits += 1;
            System.out.println("Fracht Punkte: " + credits);
        }
        if (alienPosition.equals(frachtPosition)) {
            credits -= 1;
            System.out.println("Fracht Punkte: " + credits);
        }

        if (spielerPosition.equals(alienPosition)) {

            System.out.println("Fracht Punkte: " + credits);
        }
    }

    //Randomizer funktionen 
    private static Point generiereNeueFrachtPosition(Point spielerPosition, Point alienPosition, Point erdePosition, int hoehe, int breite) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(breite);
            y = random.nextInt(hoehe);
        } while (spielerPosition.equals(new Point(x, y)) || alienPosition.equals(new Point(x, y)) || erdePosition.equals(new Point(x, y)));

        return new Point(x, y);
    }

    private static Point generiereNeuePowerUpPosition(Point spielerPosition, Point alienPosition, Point erdePosition, int hoehe, int breite) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(breite);
            y = random.nextInt(hoehe);
        } while (spielerPosition.equals(new Point(x, y)) || alienPosition.equals(new Point(x, y)) || erdePosition.equals(new Point(x, y)));

        return new Point(x, y);
    }

    private static Point genNewAlienPosition(Point spielerPosition, Point alienPosition, Point alien2Position, Point powerUpPosition, int hoehe, int breite, int credits) {
        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(breite);
            y = random.nextInt(hoehe);
        } while (spielerPosition.equals(new Point(x, y)) || alien2Position.equals(new Point(x, y)) || alienPosition.equals(new Point(x, y)) || powerUpPosition.equals(new Point(x, y)));

        return new Point(x, y);
    }
}
