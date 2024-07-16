import com.google.gson.JsonArray;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        JsonArray listaRegistroLocal = new JsonArray();
        ArrayList<String> listaRegistroGlobal = new ArrayList<>();

        Monedas listarMonedas = new Monedas();
        Scanner lectura = new Scanner(System.in);

        while (true) {
            try {
                listarMonedas.menu();
                System.out.println("Elige la moneda a convertir: ");
                int eleccion01 = lectura.nextInt();
                lectura.nextLine();

                if (eleccion01 == 8) {
                    break;
                }

                String inicial = "";
                String cambio = "";

                if (eleccion01 == 7) {
                    listarMonedas.escribirMonedas();
                    System.out.println("Ingrese moneda 1: ");
                    inicial = lectura.nextLine().toUpperCase();
                    System.out.println("Ingrese moneda 2 a Conversion: ");
                    cambio = lectura.nextLine().toUpperCase();

                } else {
                    switch (eleccion01) {
                        case 1:
                            inicial = "USD";
                            cambio = "COP";
                            break;
                        case 2:
                            inicial = "COP";
                            cambio = "USD";
                            break;
                        case 3:
                            inicial = "USD";
                            cambio = "EUR";
                            break;
                        case 4:
                            inicial = "EUR";
                            cambio = "USD";
                            break;
                        case 5:
                            inicial = "USD";
                            cambio = "MXN";
                            break;
                        case 6:
                            inicial = "MXN";
                            cambio = "USD";
                            break;
                        default:
                            System.out.println("Opción no valida");
                            continue;
                    }
                }

                System.out.println("Ingrese la cantidad a Convertir");
                Double cantidad = lectura.nextDouble();
                lectura.nextLine(); // Consumir el salto de línea

                String moneda1 = inicial.toUpperCase();
                String moneda2 = cambio.toUpperCase();

                LlamadaApi consulta = new LlamadaApi();
                TasaDeCambio representation = consulta.buscaTasaEnAPi(moneda1, moneda2, cantidad);
                Double tasaDeCambio = representation.conversion_rate();
                Double totalConversion = representation.conversion_result();

                LocalDateTime ahora = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String tiempoRegistro = ahora.format(formatter);

                String registro = String.format("[%s] Se convirtieron %f %s a %s %f", tiempoRegistro, cantidad, moneda1, moneda2, totalConversion);
                String registroGlobal = String.format("[%s]", tiempoRegistro);

                var jsonObject = ObjJson.getJsonObject(moneda1, moneda2, tasaDeCambio, cantidad, totalConversion, registroGlobal);
                System.out.println(cantidad + " " + moneda1 + " x " + tasaDeCambio + " -> " + moneda2 + " = " + totalConversion);

                listaRegistroGlobal.add(registro);
                listaRegistroLocal.add(jsonObject);

                EscrituraArchivo archivoJson = new EscrituraArchivo();
                archivoJson.guardarJson(listaRegistroLocal);
            } catch (InputMismatchException e) {
                System.out.println("Error: La entrada no es un número.");
                lectura.nextLine();
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException("Error al leer/escribir el archivo: " + e.getMessage());
            }
        }

        EscrituraArchivo archivoRegistro = new EscrituraArchivo();
        archivoRegistro.AgregarDatosAJsonGlobal(listaRegistroGlobal);

        lectura.close();

    }
}