package comidaapp.ModelView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

// Clase de conexion 
public class FirebaseSaveObject {
    
    public static FirebaseSaveObject conexionglobal;
    private FirebaseDatabase firebaseDatabase;
    
    static {
        try {
            conexionglobal = new FirebaseSaveObject();
        } catch (IOException e) {
            System.err.println("Error al inicializar FirebaseSaveObject: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // constructor que ejecuta el metodo de conexion
    public FirebaseSaveObject() throws FileNotFoundException {
        initFirebase();
    }
    
// Metodo que conecta con la base de datos 
    public void initFree() throws FileNotFoundException{
        initFirebase();
    }
    
    private void initFirebase() throws FileNotFoundException {
        try {
            
            FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                    .setDatabaseUrl("https://bdatos1-e3869-default-rtdb.firebaseio.com")
                    
                    .setServiceAccount(new FileInputStream(new File("C:\\Users\\Alejandro Padilla\\Documents\\NetBeansProjects\\ZOOFirebase\\ProyectoZooParcial\\src\\bdatos1-e3869-firebase-adminsdk-7mv22-ab55be186c.json")))

                    .build();

            FirebaseApp.initializeApp(firebaseOptions);
            firebaseDatabase = FirebaseDatabase.getInstance();
            System.out.println("Conexión exitosa....");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
         
    }

    /**
     * Save item object in Firebase.
     * @param animal
     * @param nombre
     * 
     */
    
    public void saveFree(Comida alimento,String nombre)throws FileNotFoundException{
        save(alimento, nombre);
    }
    
    public void deleteFree(Comida animal, String nombre) throws FileNotFoundException{
        delete(animal, nombre);
    }
    private void save(Comida alimento,String nombre) throws FileNotFoundException {
        if (alimento != null) {
            
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            DatabaseReference childReference = databaseReference.child(alimento.getAlimento());
            CountDownLatch countDownLatch = new CountDownLatch(1);
            childReference.setValue(alimento, new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void delete(Comida alimento, String nombre) throws FileNotFoundException{
        if (alimento != null) {
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            DatabaseReference childReference = databaseReference.child(alimento.getAlimento());
            CountDownLatch countDownLatch = new CountDownLatch(1);
            //En esta linea se utiliza un metodo de la libreria de firebase para eliminar o poner un valor. 
            childReference.removeValue(new DatabaseReference.CompletionListener() {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    countDownLatch.countDown();
                }
            });
            try {
                countDownLatch.await();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void showDataInJTable(JTable jTable) {
    DatabaseReference databaseReference = firebaseDatabase.getReference("/");
    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
                tableModel.setRowCount(0); // clear the table
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Comida alimento = childSnapshot.getValue(Comida.class);
                    tableModel.addRow(new Object[]{alimento.getAlimento(),alimento.getTipAlimento(),alimento.getCantidadAlimento()});
                }
            }
        @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Error: " + databaseError.getMessage());
            }
        });
    }
}

    
//Realizar la recuperacion de informacion
//perfeccionar el metodo en que se realiza una operacion, suprimir el conteo regresivo