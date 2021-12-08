package centdb;

import centdb.dbquery.SelectQuery;
import centdb.dbquery.UpdateQuery;
import centdb.lock.ApplyLock;
import centdb.lock.LockException;
import centdb.lock.ReleaseLock;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String databaseName = "database";
    public static final ApplyLock applyNewLock = new ApplyLock("database");
    ReleaseLock releaseOldLock = new ReleaseLock("database");
    public static void main(String[] args) {

        Scanner scn = new Scanner(System.in);
        System.out.println("Enter the select query");
        String input;
        SelectQuery select = new SelectQuery();
        UpdateQuery update = new UpdateQuery();
//        input = scn.nextLine();
        List<String> resources = new ArrayList<>();
        resources.add("Employee");
        resources.add("Product");
        resources.add("Name");
        resources.add("University");
        resources.add("School");
        select.selectQuery("SELECT * FROM Product;","database");
        //update.updateQuery("update Employee set product_name = bike, product_price = 102, status = true where product_price = 56;",databaseName);
        //select.selectQuery("select id, product_name, product_price, status from Employee where product_price = 56.78;", databaseName);

        //ApplyLock.applyNewLock(resources);
        //ReleaseLock.releaseOldLock(resources);

    }
}
