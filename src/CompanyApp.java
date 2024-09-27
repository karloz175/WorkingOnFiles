import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CompanyApp {
    public static void main(String[] args) {
        Scanner userDataScanner = new Scanner(System.in);
        System.out.println("Chcesz zapisać dane do pliku czy odczytać dane z pliku(odczyt/zapis)?");
        boolean error=true;
        while(error){
            try{
                switch(userDataScanner.nextLine()){
                    case "odczyt":
                        readUserFile();
                        error = false;
                        break;
                    case "zapis":
                        saveToFile(writeUserFile(userDataScanner));
                        error = false;
                        break;
                    default:
                        throw new IllegalArgumentException("Podaj prawidłową wartość");
                }
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }


        }



    }

    public static void readUserFile(){
        String fileName = "company.obj";
        Company company1 = null;

        try(
                var fis = new FileInputStream(fileName);
                var ois = new ObjectInputStream(fis);
        ){
            company1 = (Company) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Nie udało się odczytać pliku");
            e.printStackTrace();
        }

        if (company1 != null){
            System.out.println("Wczytano dane: ");
            int i = 0;
            while(i < 3 && company1.getEmployees()[i] != null){
                System.out.println("Pracownik " + (i+1) +" :");
                System.out.println("Imię: " + company1.getEmployees()[i].getName());
                System.out.println("Nazwisko: " + company1.getEmployees()[i].getSurname());
                System.out.println("Wypłata: " + company1.getEmployees()[i].getSalary());
                i++;
            }
        }


    }

    public static Company writeUserFile(Scanner uDS){
        Company company1 = new Company();
        final int MAX_EMPLOYEES = 3;
        int employeeIndex = 0;
        boolean stillRead = true;
        boolean wannaContinue = true;
        Employee[] employees = new Employee[MAX_EMPLOYEES];

        while(stillRead){
            try{
                if(employeeIndex < MAX_EMPLOYEES){
                    employees[employeeIndex] = new Employee();;
                    System.out.println("Wprowadź dane " + (employeeIndex+1) + " pracownika:");
                    System.out.println("Podaj imię:");
                    employees[employeeIndex].setName(uDS.nextLine());
                    System.out.println("Podaj nazwisko:");
                    employees[employeeIndex].setSurname(uDS.nextLine());
                    System.out.println("Podaj wypłatę:");
                    employees[employeeIndex].setSalary(uDS.nextDouble());
                    uDS.nextLine();

                    System.out.println("Czy chcesz wprowadzić więcej pracowników(tak/nie)?");
                    String answer = uDS.nextLine();
                    wannaContinue = true;
                    while(wannaContinue){
                        try{
                            if(answer.equalsIgnoreCase("nie")) {
                                company1.setEmployees(employees);
                                wannaContinue = false;
                                stillRead = false;
                                uDS.close();
                            } else if (answer.equalsIgnoreCase("tak")) {
                                wannaContinue = false;

                            } else {
                                throw new IllegalArgumentException("Podaj właściwą odpowiedź.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            uDS.nextLine();
                        }

                    }
                    employeeIndex++;
                } else{
                    stillRead = false;
                    throw new ArrayIndexOutOfBoundsException("Osiągnięto maksymalną ilość praconików!");
                }

            } catch(InputMismatchException e){
                System.out.println("Podaj właściwe wartości... Zacznij od nowa.");
                uDS.nextLine();
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }

        }
        return company1;

    }
    public static void saveToFile(Company company){
        String fileName = "company.obj";
        try(
                var fs = new FileOutputStream(fileName);
                var os = new ObjectOutputStream(fs);
        ){
            os.writeObject(company);
            System.out.println("Zapisano obiekt do pliku.");

        } catch(IOException e){
            System.err.println("Błąd zapisu pliku " + fileName);
            e.printStackTrace();
        }

    }
}


