import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CompanyApp {
    private static final String FILE_NAME = "employees.info";
    private static Scanner userDataScanner = new Scanner(System.in);
    public static void main(String[] args) {

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
                        saveToFile(writeUserFile());
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
        Company company1 = null;
        userDataScanner.close();

        try(
                var fis = new FileInputStream(FILE_NAME);
                var ois = new ObjectInputStream(fis);
        ){
            company1 = (Company) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Nie udało się odczytać pliku");
            e.printStackTrace();
        }

        if (company1 != null){
            System.out.println("Wczytano dane: ");
            System.out.println(company1.toString());
        } else {
            System.out.println("Brak pracowników w pliku.");
        }


    }

    public static Company writeUserFile(){
        Company company1 = new Company();
        int employeeIndex = 0;
        boolean stillRead = true;
        boolean wannaContinue = true;

        while(stillRead){
            try{
                if(employeeIndex < Company.MAX_EMPLOYEES){
                    System.out.println("Wprowadź dane " + (employeeIndex+1) + " pracownika:");
                    System.out.println("Podaj imię:");
                    String name = userDataScanner.nextLine();
                    System.out.println("Podaj nazwisko:");
                    String surname = userDataScanner.nextLine();
                    System.out.println("Podaj wypłatę:");
                    double salary = userDataScanner.nextDouble();
                    userDataScanner.nextLine();
                    company1.addEmployee(new Employee(name, surname, salary));

                    System.out.println("Czy chcesz wprowadzić więcej pracowników(tak/nie)?");
                    String answer;
                    wannaContinue = true;
                    while(wannaContinue){
                        try{
                            answer = userDataScanner.nextLine();
                            if(answer.equalsIgnoreCase("nie")) {
                                wannaContinue = false;
                                stillRead = false;
                                userDataScanner.close();
                            } else if (answer.equalsIgnoreCase("tak")) {
                                wannaContinue = false;

                            } else {
                                throw new IllegalArgumentException("Podaj właściwą odpowiedź.");
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    employeeIndex++;
                } else{
                    stillRead = false;
                    throw new ArrayIndexOutOfBoundsException("Osiągnięto maksymalną ilość praconików!");
                }

            } catch(InputMismatchException e){
                System.out.println("Podaj właściwe wartości... Zacznij od nowa.");
                userDataScanner.nextLine();
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println(e.getMessage());
            }

        }
        return company1;

    }
    public static void saveToFile(Company company){
        try(
                var fs = new FileOutputStream(FILE_NAME);
                var os = new ObjectOutputStream(fs);
        ){
            os.writeObject(company);
            System.out.println("Zapisano obiekt do pliku.");

        } catch(IOException e){
            System.err.println("Błąd zapisu pliku " + FILE_NAME);
            e.printStackTrace();
        }

    }
}


