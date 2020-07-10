package ru.job4j.pingera.clasez;

import org.springframework.stereotype.Component;

import java.sql.Clob;
import java.sql.SQLException;

@Component
public class ClobUtility {

    public Clob toClob(String str) {
//        return NonContextualLobCreator.INSTANCE.createNClob(str);
        try {
            return  new javax.sql.rowset.serial.SerialClob(str.toCharArray());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Error in Clob module");
            return null;
        }
    }
}
