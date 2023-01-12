import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import services.RentalService;
import services.TaxService;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.println("Entre com os dados do aluguel:");
       
        try{

            System.out.print("Modelo do carro: ");
            String carModel = sc.nextLine();
            
            System.out.print("Retirada (dd/MM/yyyy hh:mm): ");
            LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
    
            System.out.print("Retorno (dd/MM/yyyy hh:mm): ");
            LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);
    
            CarRental carRental = new CarRental(start, finish, new Vehicle(carModel));
    
            System.out.print("Entre com o preço por hora: ");
            double pricePerHour = sc.nextDouble();
    
            System.out.print("Entre com o preço por dia: ");
            double pricePerDay = sc.nextDouble();
    
            RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new TaxService());
    
            rentalService.processInvoice(carRental);
    
            System.out.println("FATURA: ");
            System.out.println(carRental);
            System.out.printf("Pagamento básico: %.2f\n", carRental.getInvoice().getBasicPayment());
            System.out.println("Imposto: " + carRental.getInvoice().getTax());
            System.out.printf("Pagamento total: %.2f\n", carRental.getInvoice().getTotalPayment());
        } catch (InputMismatchException e) {
            System.out.println("Dados inválidos");
        } finally {
            sc.close();
        }

    }
}
