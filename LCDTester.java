import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LCDTester {

    static final String CADENA_FINAL = "0,0";
    static List<String> listaComando;
    static String comando;
    static int espacioDig;
    
    public static void main(String[] args) 
    {
        try 
        {
            listaComando = new ArrayList<>();
            leerValores();
            imprimirValores();
        } catch (Exception ex) 
        {
            System.out.println("Error: " + ex.getMessage());
        }

    }
    
    private static void leerValores(){
        try (Scanner lector = new Scanner(System.in)) 
        {       
            System.out.print("Espacio entre Digitos (0 a 5): ");
            comando = lector.next();
            espacioDig = ImpresorLCD.isNumeric(comando);
            ImpresorLCD.estaEnRango(espacioDig, 0, 5); 

            do
            {
                System.out.print("Entrada: ");
                comando = lector.next();
                if(!comando.equalsIgnoreCase(CADENA_FINAL))
                {
                    listaComando.add(comando);
                }
            } while (!comando.equalsIgnoreCase(CADENA_FINAL)); 
        }
    }
    
    private static void imprimirValores(){
        ImpresorLCD impresorLCD = new ImpresorLCD();
        for (String e : listaComando)
        {
            impresorLCD.procesar(e, espacioDig);
        }
    }

}