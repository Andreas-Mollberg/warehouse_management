
import org.example.Warehouse;
import org.example.WarehouseManager;

import org.junit.Test;


public class WarehouseManagerTests {
    @Test
    public void testAddWarehouseToList(){
        var warehouseManager = new WarehouseManager();
        var warehouse = new Warehouse(1, "Kista");

        warehouseManager.addWarehouseToList(warehouse);

        assert warehouseManager.getAllWarehouses().size() == 1;
    }






}
