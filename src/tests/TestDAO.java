package tests;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TestDAO {

    public static void main(String[] args) throws SQLException {

    	ChauffeurDAO dao = new ChauffeurDAO();

    	Chauffeur c = new Chauffeur(
    	    1,
    	    "Karim",
    	    "Benali",
    	    "karim@test.com",
    	    "0666123456"
    	);

    	dao.update(c);



    }
}
