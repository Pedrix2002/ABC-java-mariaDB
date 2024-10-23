import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class principal {

    private static final String URL = "jdbc:mariadb://localhost:3307/mi_basedatos"; // Cambia por tu base de datos
    private static final String USER = "root"; 
    private static final String PASSWORD = "mi_password"; 

    // Conectar a la base de datos
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // INSERTAR un nuevo cliente
    public static void insertarCliente(String nombre, int edad) throws SQLException {
        String query = "INSERT INTO clientes (nombre, edad) VALUES (?, ?)";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, edad);
            stmt.executeUpdate();
            System.out.println("Cliente insertado: " + nombre);
        }
    }

    // ACTUALIZAR un cliente
    public static void actualizarCliente(int id, String nuevoNombre, int nuevaEdad) throws SQLException {
        String query = "UPDATE clientes SET nombre = ?, edad = ? WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, nuevaEdad);
            stmt.setInt(3, id);
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Cliente actualizado con ID: " + id);
            } else {
                System.out.println("No se encontró el cliente con ID: " + id);
            }
        }
    }

    // ELIMINAR un cliente
    public static void eliminarCliente(int id) throws SQLException {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            int filasEliminadas = stmt.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Cliente eliminado con ID: " + id);
            } else {
                System.out.println("No se encontró el cliente con ID: " + id);
            }
        }
    }

    // SELECCIONAR todos los clientes
    public static void listarClientes() throws SQLException {
        String query = "SELECT * FROM clientes";
        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad);
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Insertar cliente
            insertarCliente("Juan Pérez", 35);
            insertarCliente("Ana López", 28);

            // Actualizar cliente
            actualizarCliente(1, "Juan Gómez", 36);

            // Listar clientes
            listarClientes();

            // Eliminar cliente
            eliminarCliente(2);

            // Listar nuevamente después de eliminar
            listarClientes();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
