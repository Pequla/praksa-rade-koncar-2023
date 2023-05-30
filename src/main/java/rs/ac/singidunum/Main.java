package rs.ac.singidunum;

import com.fasterxml.jackson.core.JsonProcessingException;
import rs.ac.singidunum.model.FlightModel;
import rs.ac.singidunum.model.PageModel;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FlightService service = FlightService.getInstance();
        Scanner scn = new Scanner(System.in);
        boolean running = true;

        System.out.println("Unesite broj stranice npr. p{pageNumber} ili rezervaciju r{id}");
        System.out.println("Da izadjete iz programa ukucajte exit");

        while (running) {
            System.out.print(">> ");
            String input = scn.nextLine();
            try {
                if (input.startsWith("p")) {
                    int page = Integer.parseInt(input.substring(1));
                    printFlights(page);
                }

                if (input.startsWith("d")) {
                    int id = Integer.parseInt(input.substring(1));
                    System.out.println(getFlightDetails(id));
                }

                if (input.startsWith("r")) {
                    int id = Integer.parseInt(input.substring(1));
                    System.out.println("Odabrali ste rezervaciju za id " + id);
                    String body = getFlightDetails(id);
                    MailService.getInstance().sendEmil("pkresoja@singimail.rs", "Rezervacija leta " + id, body);
                }

                if (input.toLowerCase().equals("exit")) {
                    System.out.println("Izlazim iz aplikacije");
                    running = false;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Neispravan unos: " + nfe.getMessage());
            } catch (JsonProcessingException e) {
                System.out.println("Greska prilikom dopremanja podataka: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Dolso je do nepoznate greske: " + e.getMessage());
            }
        }


    }

    public static void printFlights(int page) throws JsonProcessingException {
        PageModel<FlightModel> flights = FlightService.getInstance().getFlights(page);
        System.out.printf("(%5s) [%16s] [%20s]: %s%n", "ID", "VREME LETA", "DESTINACIJA", "MEDJUNARDNOA OZNAKA LETA");
        System.out.println("--------------------------------------------------------------------------------");
        for (FlightModel flight : flights.getContent()) {
            System.out.printf("(%5s) [%16s] [%20s]: %s%n", flight.getId(), flight.getScheduledAt(), flight.getDestination(), flight.getFlightKey());
        }
        System.out.println("Prikaz stranice " + page + " od ukupno " + (flights.getTotalPages() - 1) + " stranica");
    }

    public static String getFlightDetails(int id) throws JsonProcessingException {
        FlightModel flight = FlightService.getInstance().getFlightById(id);
        return "Destinacija: " + flight.getDestination() + System.lineSeparator() +
                "Vreme leta: " + flight.getScheduledAt().toString().replace("T", " ") + System.lineSeparator() +
                "Medjunarodna oznaka: " + flight.getFlightKey() + System.lineSeparator() +
                "Kapija: " + flight.getGate();
    }
}