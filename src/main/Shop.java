package main;

import java.util.ArrayList;
import model.Product;
import model.Sale;
import model.Amount;
import java.util.Scanner;
import model.Client;
import model.Employee;

public class Shop {

    private Amount cash;
    private ArrayList<Product> inventory;
    private int numberProducts;
    private ArrayList<Sale> sales;

    final static double TAX_RATE = 1.04;

    public Shop() {
        cash = new Amount(100.00);
        inventory = new ArrayList<Product>();
        sales = new ArrayList<Sale>();
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();
        shop.initSession();

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Anadir producto");
            System.out.println("3) Anadir stock");
            System.out.println("4) Marcar producto proxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Ver total de todas las ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;
                    
                case 8:
                    shop.showTotalAmountSales();
                    break;
                    
                case 9:
                    shop.removeProduct();
                    break;
                    
                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    /**
     * load initial inventory to shop
     */
    public void loadInventory() {
        addProduct(new Product("Manzana", 10.00, true, 10));
        addProduct(new Product("Pera", 20.00, true, 20));
        addProduct(new Product("Hamburguesa", 30.00, true, 30));
        addProduct(new Product("Fresa", 5.00, true, 20));
    }
    /**
     * employee login
     */
    public void initSession() {
        Scanner scanner = new Scanner(System.in);
        boolean logged = false; // employee verification
                
        while (!logged) {
            System.out.print("Introduzca el numero de empleado: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Introduzca el password: ");
            String pw = scanner.nextLine();
            
            Employee employee = new Employee("Empleado");
            logged = employee.login(id, pw);
            
            if (!logged) { // if data is incorrect, asks again
                System.out.println("Los datos son incorrectos. Intente de nuevo");
            } else {
                System.out.println("Datos ingresados correctos. Bienvenido");
            }
        }
    }

    /**
     * show current total cash
     */
    private void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    /**
     * add a new product to inventory getting data from console
     */
    public void addProduct() {
        /*if (isInventoryFull()) {
            System.out.println("No se pueden anadir mas productos");
            return;
        }*/
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        Product product = findProduct(name);
        
        if (product != null && product.isAvailable()) {
            System.out.println("Este producto ya existe");
        } else {
            addProduct(new Product(name, wholesalerPrice, true, stock));
        }
    }

    /**
     * add stock for a specific product
     */
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();
        Product product = findProduct(name);

        if (product != null) {
            // ask for stock
            System.out.print("Seleccione la cantidad a anadir: ");
            int stock = scanner.nextInt();
            // update stock product
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    /**
     * set a product as expired
     */
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);
        product.expire();

        if (product != null) {
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
            
        }
    }

    /**
     * show all inventory
     */
    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    /**
     * make a sale of products to a client
     */
    public void sale() {
        Scanner sc = new Scanner(System.in);
        Client client = new Client("Client");
        
        // ask for client name
        System.out.println("Realizar venta, escribir nombre cliente");
        String clientName = sc.nextLine();

        // sale product until input name is not 0
        Amount totalAmount = new Amount(0.0);
        String name = "";
        ArrayList<Product> purchaseProducts = new ArrayList<>();
        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }
            Product product = findProduct(name);
            boolean productAvailable = false;

            if (product != null && product.isAvailable()) {
                productAvailable = true;
                totalAmount = totalAmount.sum(product.getWholesalerPrice());
                product.setStock(product.getStock() - 1);
                purchaseProducts.add(product);
                // if no more stock, set as not available to sale
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                System.out.println("Producto anadido con exito");
            }

            if (!productAvailable) {
                System.out.println("Producto no encontrado o sin stock");
            }
        }
        
        if (client.pay(totalAmount)) { // if client pays the exact amount, adds a sale
            Sale sale = new Sale (client, purchaseProducts, totalAmount);
            sales.add(sale);

            // show cost total        
            totalAmount = totalAmount.multiply(TAX_RATE);
            cash = cash.sum(totalAmount);
            client.pay(totalAmount);
            System.out.println("Venta realizada con exito, total: " + totalAmount);
        } else {
            System.out.println("Saldo insuficiente. El cliente debe: " + totalAmount);
        }
    }

    /**
     * show all sales
     */
    private void showSales() {
        System.out.println("Lista de ventas:");
        for (Sale sale : sales) {
            if (sale != null) {
                System.out.print("Sale [client= " + sale.getClient());
                for (Product product : sale.getProducts()) {
                    if (product != null) {
                        System.out.print(", products= " + product.getName());
                    }
                }
                System.out.println(", amount= " + sale.getAmount() + "]");
            }
        }
    }
    
    /**
     * show the total amount of all sales
     */    
    private void showTotalAmountSales() {
        System.out.println("Importe total de todas las ventas:");
        Amount totalAmount = new Amount(0.0);
        for (Sale sale : sales) {
            totalAmount = totalAmount.sum(sale.getAmount());
        }
        
        System.out.println(totalAmount);
    }
    
    /**
     * remove a product from inventory
     */
    public void removeProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        
        Product product = findProduct(name);
        
        if (product != null && product.isAvailable()) {
            inventory.remove(product);
            numberProducts--;
            System.out.println("Producto removido");
        } else {
            System.out.println("Este producto no existe");
        }
    }

    /**
     * add a product to inventory
     *
     * @param product
     */
    public void addProduct(Product product) {
        inventory.add(product);
        numberProducts++;
    }

    /**
     * check if inventory is full or not
     *
     * @return true if inventory is full
     */

    /**
     * find product by name
     *
     * @param name
     * @return product found by name
     */
    public Product findProduct(String name) {
        for (Product product : inventory) {
            if (product.isAvailable() && product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

}
