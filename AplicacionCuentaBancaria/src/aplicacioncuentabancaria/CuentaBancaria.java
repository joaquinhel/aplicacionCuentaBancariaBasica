package aplicacioncuentabancaria;

import java.util.Scanner;

/**
 * @author Joaquín
 */
public class CuentaBancaria {

    /**
     * Declaramos las variables globales que serán privadas para que solo puedan
     * ser accesibles desde la propia clase con los métodos get
     */
    private String cuenta;
    private String nombre;
    private double saldo;

    /**
     * Declaramos el método constructor con el que inicialzamos las variables
     * anteriores, este método tiene el mismo nombre que la clase
     *
     * @param cuenta
     * @param nombre
     */
    public CuentaBancaria(String cuenta, String nombre) {
        this.cuenta = cuenta;
        this.nombre = nombre;
        this.saldo = 0;
    }

    /**
     * Declaramos el método constructor sin parámetros a saldo debemos darle un
     * valor inicial, ya que este no se introducirá por teclado
     */
    public CuentaBancaria() {
        this.saldo = 0;
    }

    /**
     * Método que devolverá el valor de la varible cuenta Solo recibirá datos
     * cuando la validación sea correcta
     */
    String getCuenta() {
        return cuenta;
    }

    /**
     * Método que devolverá el valor de la varible nombre Solo recibirá datos
     * cuando la validación sea correcta
     */
    String getNombre() {
        return nombre;
    }

    /**
     * Método que devolverá el valor de la varible saldo Por defecto tiene el
     * valor de 0
     *
     * @return saldo. Por defecto vale 0. Los métodos ingresar saldo y retirar
     * saldo modificarán su valor.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Método que devolverá el numero de cuenta con formato
     * XXXX-XXXX-XX-XXXXXXXXXX
     *
     * @return la cuenta con formato
     */
    String mostrarCuenta() {
        String cuentaSeparada;
        cuentaSeparada = (cuenta.substring(0, 4) + "-" + cuenta.substring(4, 8) + "-" + cuenta.substring(8, 10) + "-" + cuenta.substring(10, 20));
        return cuentaSeparada;
    }

    /**
     * Método que devuelve los 4 dígitos de la entidad
     *
     * @return los cuatro dígitos que definen la entidad
     */
    public String getEntidad() {
        return this.getCuenta().substring(0, 4);
    }

    /**
     * Método que devuelve los 4 dígitos de la oficina
     *
     * @return los cuatro dígitos que definen la oficina
     */
    public String getOficina() {
        return this.getCuenta().substring(4, 8);
    }

    /**
     * Método que devuelve los 10 dígitos del número de cuenta
     *
     * @return los 10 dígitos del número de cuenta
     */
    public String getNumeroCuenta() {
        return this.getCuenta().substring(10, 20);
    }

    /**
     * Método que devuelve los 2 dígitos de control de la cuenta
     *
     * @return los 2 dígitos de control de la cuenta
     */
    public String getDigitosControl() {
        return this.getCuenta().substring(8, 10);
    }

    /**
     * Método que pide la introducción del nombre por teclado, controlando la
     * longitud del mismo.
     *
     * @return el String en el que queda definido el nombre del usuario
     */
    public String introducirNombre() {
        boolean error;
        do {
            Scanner teclado = new Scanner(System.in);
            error = false;
            System.out.println("Introduzca el nombre del usuario: ");
            nombre = teclado.next();

            if (nombre.length() < 4 || nombre.length() > 150) {
                System.err.println("La longitud del nombre introducida no es la correcta. ");
                error = true;
            } else {
                System.out.println("El nombre introducido es correcto.\n");
            }
        } while (error == true);
        return nombre;
    }

    /**
     * Método que pide la introducción del número de cuenta, controlando que: La
     * longitud de la cuenta sea de 20 dígitos, que los dígitos de control
     * introducidos sean los correctos, es decir, que coincidan con los
     * calculados en el método calcularDigitosControl() y que no haya letra
     * entre los caracteres introducidos, esto se realiza mediante el médoto
     * comprobarEnteros().
     *
     * @return cuenta El String en el que queda definido el número de cuenta
     * introducido por el usuario.
     */
    public String introducirCuenta() {
        boolean error;
        do {
            Scanner teclado = new Scanner(System.in);
            error = false;
            System.out.println("Introduzca los 20 digitos del número de su número de cuenta: ");
            cuenta = teclado.next();

            if (cuenta.length() != 20) { //Comprobamos que la longitud de la cuenta sea la correcta
                System.out.println("El número de digitos introducido es incorrecto. Deben ser 20 dígitos.");
                error = true;
            } else {
                if (this.comprobarEnteros(cuenta) && (this.calcularDigitosControl(cuenta).equals(this.getCuenta().substring(8, 10)))) {
                    error = false;
                    System.out.println("Los datos son correctos.");
                } else {
                    error = true;
                    System.out.println("El número de cuenta introducido es incorrecto.");
                }
            }
        } while (error == true);

        return cuenta;
    }

    /**
     * Método que comprueba si los números de la cuenta son enteros, al ser 20
     * dígitos la entrada no puede ser ni int ni long Tratamos la cuenta como si
     * fuera un array y recorremos cada uno de sus dígitos asegurandonos que
     * sean int
     *
     * @param cuenta
     * @return true o false dependiendo del resultado de la validación
     */
    boolean comprobarEnteros(String cuenta) {
        this.cuenta = cuenta;
        for (int x = 0; x < cuenta.length(); x++) {
            try {
                Integer.parseInt(cuenta.substring(x, x + 1)); //Convertimos uno por uno los String a int
            } catch (NumberFormatException e) { //Excepción que saltará cuando intentemos convertir un caracter que no sea dígito
                System.out.println("Debe introducir sólo números enteros.");
                return false;
            }
        }
        return true;
    }

    /**
     * Método para calcular los dígitos de control del número de cuenta y
     * comprabar si estos coinciden con los dígitos introducidos por el usuario
     *
     * @param cuenta
     * @return digitosControlCalculados
     */
    String calcularDigitosControl(String cuenta) {
        this.cuenta = cuenta;
        String control1 = "00" + cuenta.substring(0, 8); //Añadimos dos 0 al comienzo del array
        String control2 = cuenta.substring(10, 20);
        int d1 = 0;
        int d2 = 0;

        int x; //Lo usaremos para los bucles for

        int[] coeficientes = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6}; //Array con los valores de los coeficientes

        //Realimaos la multiplicación de control1 por los coeficientes para obetener d1
        //Para ello vamos recorriendo el String de los coeficientes y cada valor de estos lo multiplicamos por un dígito del 
        //número de cuenta que tenemos que pasar de String a Int.
        for (x = 0; x < coeficientes.length; x++) {
            d1 = d1 + coeficientes[x] * Integer.parseInt(control1.substring(x, x + 1)); //Tb podríamos poner d1 += coef...
        }
        for (x = 0; x < coeficientes.length; x++) {
            d2 = d2 + coeficientes[x] * Integer.parseInt(control2.substring(x, x + 1)); //Vamos sumando los valores de la multiplicación
        }

        // Restamos a 11 el resto de la división entre el valor obtenido y el número 11
        d1 = 11 - (d1 % 11);
        d2 = 11 - (d2 % 11);

        //Mediante los métodos if damos el valor 0 y 1 al resto 10 y 11 respectivamente
        if (d1 == 10) {
            d1 = 1;
        }
        if (d1 == 11) {
            d1 = 0;
        }
        if (d2 == 10) {
            d2 = 1;
        }
        if (d2 == 11) {
            d2 = 0;
        }
//Por último necesitamos pasar los int a String
        String digitosControlCalculados = String.valueOf(d1) + String.valueOf(d2);
        return digitosControlCalculados;
    }

    /**
     * Método con el introducimos la cantidad que queremos ingresar o retirar de
     * la cuenta, controlamos que no sea negativo.
     *
     * @return cantidad
     * @throws Exception
     */
    public double operarCantidad() throws Exception { //Determinamos los errores
        double cantidad = 0;
        boolean error;
        do {
            Scanner teclado = new Scanner(System.in);
            try {
                cantidad = teclado.nextDouble();
                error = false;
                if (cantidad < 0) {
                    throw new Exception("No se puede operar con una cantidad negativa, introduzca un número mayor a 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage()); //Describimos la excepción producida
                System.out.println("Debes introducir un número.");
                error = true;

            } catch (Exception e) {
                System.out.println(e.getMessage());
                error = true;
            }
        } while (error == true);
        return cantidad; //Debemos inicializarla
    }

    /**
     * Método para sumar al saldo de la cuenta la cantidad que hemos introducido
     * en el método operarCantidad()
     *
     * @param cantidad
     */
    public void ingresarSaldo(double cantidad) {
        saldo = saldo + cantidad;
    }

    /**
     * Método para restar al saldo de la cuenta la cantidad que hemos
     * introducido en el método operarCantidad()
     *
     * @param cantidad
     */
    public void retirarSaldo(double cantidad) {
        if (saldo < cantidad) {
            System.out.println("No puede retirar más del saldo disponible.");
        } else {
            saldo -= cantidad;
        }
    }

}//Cierre de clase CuentaBancaria

