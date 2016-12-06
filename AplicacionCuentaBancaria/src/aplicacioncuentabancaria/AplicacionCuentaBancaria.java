package aplicacioncuentabancaria;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Joaquín
 */
public class AplicacionCuentaBancaria {

    int opcion;

    /**
     * Método que mostrará por consola las opciones de la aplicación que estamos
     * realizando
     */
    void menuAplicacion() {
        System.out.println("\n-----------MENÚ DE OPCIONES APLICACIONES BANCARIAS-----------\n");
        System.out.println("1. Ver el número de cuenta completo (XXXX-XXXX-XX-XXXXXXXXXX)");
        System.out.println("2. Ver el titular de la cuenta.");
        System.out.println("3. Ver el código de la entidad.");
        System.out.println("4. Ver el código de la oficina.");
        System.out.println("5. Ver el número de la cuenta (10 últimos dígitos)");
        System.out.println("6. Ver los dígitos de control de la cuenta.");
        System.out.println("7. Realizar un ingreso.");
        System.out.println("8. Retirar efectivo.");
        System.out.println("9. Consultar saldo.");
        System.out.println("0. Salir de la aplicación\n");
    }

    /**
     * Método que determinará que la opción que recibirá el switch sea un int
     *
     * @return opcion. Valor que recbirá el método datoMenu()
     */
    int datoMenu() {
        boolean error = false;
        opcion = 10;
        do {
            Scanner teclado = new Scanner(System.in);
            error = false;
            try {
                System.out.print("Seleccione una opción de las anteriores: ");
                opcion = teclado.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Debe introducir un numero y sin decimales.");
                error = true;
            }
        } while (error == true);
        return opcion;
    }

    //Sólo podrá acceder a la información de la clase y no la de un objeto en particular
    static CuentaBancaria introducirDatos;
    static AplicacionCuentaBancaria menu;

    public static void main(String[] args) throws Exception {

        //Creamos los objetos de las clases CuentaBancaria y AplicacionCuentaBancaria,
        //en ambos casos uso el método constructor sin parámetros
        introducirDatos = new CuentaBancaria();
        menu = new AplicacionCuentaBancaria();

        //El nombre y el número de cuenta se debe introducir antes de que aparezca el menú
        introducirDatos.introducirNombre();
        introducirDatos.introducirCuenta();

        /**
         * El switch recoge las distintas acciones que puede realizar nuestra
         * aplicación. Recibe el valor introducido en el método datoMenu() Debe
         * existir conconrdancia entre los valores definidos en el switch y los
         * definidos en el método menuAplicacion()
         */
        do { //Es necesario que el bucle do-while se repita hasta que se pulse 0
            switch (menu.opcion) {
                case 0:
                    break;
                case 1:
                    System.out.println("El número de cuenta completo es: " + introducirDatos.getCuenta());
                    System.out.println("O si le damos formato: " + introducirDatos.mostrarCuenta());
                    break;
                case 2:
                    System.out.println("El titular de la cuenta es: " + introducirDatos.getNombre());
                    break;
                case 3:
                    System.out.println("Los digitos del código de la entidad son: " + introducirDatos.getEntidad());
                    break;
                case 4:
                    System.out.println("Los dígitos del código de la oficina son: " + introducirDatos.getOficina());
                    break;
                case 5:
                    System.out.println("Los dígitos del número de cuenta son: " + introducirDatos.getNumeroCuenta());
                    break;
                case 6:
                    System.out.println("Los dígitos de control de la cuenta son: " + introducirDatos.getDigitosControl());
                    break;
                case 7:
                    System.out.println("Introduzca la cantidad que desea ingresar: ");
                    introducirDatos.ingresarSaldo(introducirDatos.operarCantidad());
                    break;
                case 8:
                    System.out.println("Introduzca la cantidad que desea retirar: ");
                    introducirDatos.retirarSaldo(introducirDatos.operarCantidad());
                    break;
                case 9:
                    System.out.println("Su saldo actual es: ");
                    System.out.println(introducirDatos.getSaldo() + " €");
                    break;
                default:
                    System.out.println("Introduzca un valor entre 0 y 9");
            }
            menu.menuAplicacion(); //Para que cada vez que se pulse en una opción vuelvan a aparecer las opciones
        } while (menu.datoMenu() != 0);
        System.out.println("Gracias por utilizar nuestra aplicación"); //Mensaje que se mostrará al salir del Switch
    }
}
