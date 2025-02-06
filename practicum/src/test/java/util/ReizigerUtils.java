package util;

import domain.Reiziger;
import org.junit.jupiter.api.Assertions;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReizigerUtils {

    static String[] lastNames = {"Lin", "Simmons", "Esparza", "Lozano", "Hendrix", "Salgado", "Ortega", "Wood", "Olsen", "Frederick", "Armstrong", "Lyons", "Beard", "Bravo", "Yang", "Hanna", "Tate", "Mullen", "Jackson", "Preston", "Kerr", "Copeland", "Nguyen", "Gonzalez", "Villarreal", "Huffman", "Cisneros", "Santana", "O’Neal", "Lamb", "Schneider", "Oliver", "Buckley", "Casey", "Hickman", "Hodges", "Figueroa", "Logan", "Dodson", "Archer", "Villegas", "McKee", "Gentry", "Schmitt", "Hurley", "Meyer", "Strickland", "Schultz", "Swanson", "Vargas", "Ellison", "Gallagher", "Noble", "Olsen", "Lewis", "Gilbert", "Ramirez", "Hamilton", "Dennis", "Olsen", "Pruitt", "Acevedo", "Medrano", "Henson", "Little", "Hopkins", "Morrow", "Hall", "Liu", "Curtis", "Villarreal", "Navarro", "Walton", "Cannon", "Coleman", "Sandoval", "Cochran", "Ramsey", "Carey", "Bridges", "O’Donnell", "McLean", "Norton", "Kirby", "Jefferson", "Hanson", "Johns", "Evans", "Porter", "Harris", "Hicks", "Wilcox", "Roman", "Hendricks", "Zimmerman", "Mercado", "Blevins", "Beard", "Wolf", "Ponce", "Hoover", "Nunez", "Escobar", "Sullivan", "Bautista", "Tran", "Newman", "Miller", "Rhodes", "Craig", "Owens", "Beasley", "Boyer", "Horton", "Powell", "McKay", "Cardenas", "Shaffer", "Sheppard", "Oliver", "Magana", "Corona", "Jenkins", "Kaur", "Sellers", "Russo", "Harrington", "Peralta", "Nichols", "Clay", "Hardin", "Lowery", "Rosas", "Mejia", "Kent", "Huber", "Sawyer", "Mendez", "Salgado", "Rodgers", "Reese", "Randolph", "Medina", "Kelley", "Wolf", "Harmon", "Huber", "Ahmed", "Schmidt", "Campbell", "Bell", "Shepherd", "Barker", "Campbell", "Beasley", "Duarte", "Wagner", "Hebert", "Gonzalez", "Powell", "Sanders", "Hogan", "Lim", "Mora", "Murray", "Yoder", "Jaramillo", "Cole", "Foster", "Pope", "Ahmed", "Daniel", "Powers", "Figueroa", "Benton", "Stone", "Bond", "Lucas", "English", "Quintana", "Gutierrez", "Yates", "Simpson", "Gill", "Fischer", "Wilkinson", "Watkins", "McFarland", "Huff", "Harrington", "Perkins", "Tran", "Cunningham", "Lester", "Stein", "Gaines", "Cisneros", "Stanley", "Browning", "McClure", "Quinn", "Larsen", "Lewis", "Cameron", "Moore", "Garrett", "Leach", "McIntyre", "Morrow", "Anderson", "George", "Fuentes", "May", "Bond", "Weeks", "Fisher", "Curry", "Beasley", "Hendrix", "Lin", "Pittman", "Goodman", "Trevino", "Douglas", "Crane", "Randall", "Livingston", "Boyle", "Sexton", "Best", "Colon", "Nash", "McDowell", "Simon", "Travis", "Miranda", "Santiago", "Beard", "Atkinson", "Shaffer", "Peters", "Wang", "Sullivan", "Moody", "Holloway", "Stark", "Rhodes", "Cherry", "Leon", "Francis", "Acosta", "Tang", "Hutchinson", "Hernandez", "Torres", "O’Connor", "Parsons", "McCoy", "Finley", "Santana", "Harper", "Reese", "Bautista", "Nielsen", "Craig", "Valdez", "Quinn", "Estes", "Underwood", "McKinney", "Little", "Davis", "Avalos", "Erickson", "Figueroa", "Huber", "Price", "Holloway", "Benitez", "Walter", "McGee", "Esparza", "Macias", "Gilmore", "Cummings", "Schaefer", "Morse", "Morrison", "Choi", "Berry", "Barr", "Trevino", "Stevenson", "Young", "Jacobson", "Mason", "Blanchard", "Huffman", "Montes", "Santana", "Dyer", "O’Connell", "Curtis", "Herring", "Stuart", "Schroeder", "Norton", "Farmer", "Gibson", "Kim", "Huffman", "Park", "Schultz", "Tang", "Bryant", "Rojas", "Ware", "Bell", "Cook", "Miller", "Reynolds", "Norman", "Hale", "Stone", "Porter", "Floyd", "Marquez", "Stafford", "Keller", "Perry", "Mora", "Barton", "McKee", "Williamson", "Edwards", "Schwartz", "Schaefer", "Moon", "Cunningham", "McKee", "Lu", "Hall", "Sosa", "Wood", "Newton", "Pitts", "Winters", "Powers", "Osborne", "Dalton", "Mendez", "Wood", "Kramer", "Payne", "Fowler", "Young", "Duke", "Fitzgerald", "Snow", "Espinoza", "Nunez", "Melendez", "Ochoa", "Griffith", "Lynn", "Cabrera", "Hines", "Horton", "Herman", "Spears", "Griffith", "Schaefer", "Owen", "Frye", "Tapia", "Blair", "Stone", "Bates", "Blair", "Flowers", "Tang", "Cano", "McCullough", "Rocha", "Zamora", "Schroeder", "Harmon", "Hardy", "Hale", "Johnson", "Prince", "Wood", "Ross", "Garrison", "Saunders", "Lyons", "Kirk", "McBride", "Wells", "Tyler", "Bravo", "Briggs", "Lam", "Watts", "Mendoza", "Grimes", "Arellano", "Mata", "Carrillo", "Cook", "McKay", "Chambers", "Dodson", "Avila", "Olson", "Leblanc", "Gould", "Morse", "Webster", "Hughes", "Payne", "Smith", "Huff", "Farley", "Mathis", "Payne", "Martin", "Leach", "Huerta", "Chung", "Bowers", "Shelton", "Branch", "Rowe", "Nixon", "Berger", "McIntyre", "Maldonado", "Newman", "Burke", "Barrett", "Ali", "Arnold", "Hudson", "Grant", "Small", "Hampton", "Chase", "Bush", "Guevara", "Fitzpatrick", "Chavez", "Dickson", "Estes", "Peters", "Stark", "Bentley", "Suarez", "Gutierrez", "Freeman", "Jenkins", "Vu", "Villarreal", "McMillan", "Trejo", "Stark", "May", "Burch", "Hall", "Henderson", "Koch", "Chapman", "Conner", "Calderon", "Banks", "Bernard", "Holland", "Booth", "Anthony", "Harvey", "Clarke", "Ballard", "Roman", "Leon", "Davidson", "Faulkner", "Vazquez", "Snow", "Alexander", "Skinner", "Hester", "Hancock", "Hale", "Holt", "Nava", "Cunningham", "Santos", "Rios", "Berg", "Salgado", "Pierce", "Webster", "Bailey", "Blackwell", "Roach", "Wyatt", "Arellano", "Ramos", "Bush", "Leal", "Kramer", "Griffith", "Hughes", "Duke", "Russo", "Lowe", "Miranda", "Park", "Small", "Wong", "Norman", "Doyle", "Hayes", "Marshall", "Castro", "Waters", "Coleman", "McPherson", "Walls", "Burnett", "Harding", "Barr", "Hood", "Galindo", "Snow", "Maxwell", "Rios", "Hodge", "Underwood", "Williams", "O’Neill", "Lindsey", "Kaur", "Vasquez", "McKee", "Poole", "Harmon", "Bryan", "Barajas", "Gaines", "Zhang", "Webb", "Conrad", "Hutchinson", "Marks", "Wilson", "Mathis", "McClain", "Ortiz", "Blake", "Wilkinson", "Ryan", "Keller", "Rivers", "Mayo", "Calderon", "Barrera", "Bauer", "Weber", "Koch", "Palacios", "Humphrey", "Pitts", "Hensley", "Harmon", "Choi", "Velazquez", "Jensen", "Fitzgerald", "Hutchinson", "Swanson", "Patel", "Duke", "Jackson", "Stein", "Xiong", "Floyd", "Henry", "Campos", "Rivers", "Goodwin", "Sheppard", "Donaldson", "Odom", "Barber", "Gould", "Sierra", "Guzman", "Strickland", "Conrad", "Graham", "Barton", "Pollard", "Ross", "Gutierrez", "Lucero", "Huff", "Palmer", "Reed", "Brennan", "Farmer", "Levy", "Rush", "Cano", "Cross", "Walker", "Parker", "Booker", "Bond", "Wilkins", "Curry", "Booth", "Malone", "Randolph", "Lu", "Lloyd", "Proctor", "Blankenship", "Bernard", "Hill", "Long", "Hoover", "Chan", "Willis", "Cortez", "Lara", "Perry", "Ahmed", "Chan", "Ortega", "Davidson", "Duarte", "Barrett", "Hammond", "Hensley", "Byrd", "Hancock", "Warren", "Bruce", "Hines", "Moss", "Patton", "Ayers", "Lawson", "Davila", "Frank", "Nguyen", "Mathis", "Stanton", "Cobb", "Taylor", "Barry", "Gates", "Reese", "Woodward", "Vega", "Gray", "Enriquez", "Ibarra", "Pollard", "Koch", "Weeks", "Wang", "Vu", "Davis", "Palmer", "Poole", "Rojas", "Bryan", "Erickson", "Rosales", "Barron", "May", "Cox", "Meadows", "Sherman", "Francis", "Moyer", "Cabrera", "Stein", "Goodman", "Quinn", "Myers", "McClain", "Li", "Oliver", "Keller", "Johnston", "Watson", "Villanueva", "Rosas", "Parra", "Nicholson", "Jones", "Sparks", "Shaffer", "Gaines", "Dawson", "Anderson", "Watts", "Vaughn", "Waters", "Clements", "Kemp", "Chapman", "Bond", "Morse", "Sanders", "King", "Lin", "Lara", "Blake", "Sparks", "Arroyo", "Molina", "Massey", "Vang", "Vance", "Roberts", "Knapp", "Edwards", "McIntyre", "Huerta", "Henson", "Bravo", "Spencer", "Hammond", "Hail", "Wilson", "Foley", "Gomez", "Prince", "Felix", "House", "Suarez", "Avila", "Foster", "Villanueva", "Nixon", "Torres", "Lester", "Collins", "Mitchell", "Romero", "Flynn", "Bernal", "Cisneros", "Bush", "Scott", "Cobb", "Dickson", "Hansen", "Nielsen", "Mayer", "Franco", "Hensley", "Wiggins", "Peterson", "Stanley", "Barnett", "Blevins", "Hancock", "Huffman", "Taylor", "Terrell", "Moon", "Conner", "Byrd", "Sanchez", "Torres", "Curry", "Caldwell", "Phelps", "Baxter", "Murillo", "Haynes", "Cabrera", "Davila", "Holloway", "Peralta", "Bryant", "Fleming", "Giles", "Bradley", "Lane", "Moody", "Steele", "Zhang", "Estrada", "Holland", "Figueroa", "Thompson", "Preston", "Ho", "Warren", "Novak", "Woodward", "Villa", "Saunders", "Silva", "Underwood", "Alvarez", "McIntyre", "Stanton", "Adkins", "Bentley", "McCormick", "Herman", "Austin", "Pierce", "Hurley", "Kemp", "Melendez", "Perez", "Lucas", "Kent", "Kelly", "Beck", "Aguirre", "Bradford", "Bruce", "Reeves", "Yates", "Prince", "Velasquez", "Reilly", "Wiley", "Pugh", "Nash", "Bentley", "Ortiz", "Stafford", "Flowers", "Cortez", "Kline", "Hickman", "Patterson", "Andersen", "Savage", "Bridges", "Nash", "Meyers", "Stein", "Clements", "Mahoney", "Lucero", "West", "Castaneda", "Dean", "O’Donnell", "Sutton", "Jones", "Gonzales", "Burgess", "Person", "Crane", "Gaines", "Ponce", "Andersen", "Hartman", "Jenkins", "Coffey", "Marquez", "Crosby", "Thomas", "Mayer", "Fry", "Baxter", "Mullen", "Blankenship", "Ferguson", "Blankenship", "Zhang", "Rodgers", "Jimenez", "Madden", "Berger", "Lopez", "Kline", "Patel", "Quintero", "Weiss", "Caldwell", "Dixon", "Wright", "Zimmerman", "Cobb", "Galvan", "Kelley", "Trujillo", "Haynes", "Chapman", "Jaramillo", "Sampson", "Zavala", "Barnes", "Harper", "Hendricks", "Bell", "Andersen", "Villalobos", "Lowe", "Patton", "Tucker", "Sanders", "Terry", "Olsen", "Terrell", "Figueroa", "Stafford", "Wise", "Thompson", "Rasmussen", "Lindsey", "Russell", "Larsen", "Strong", "Wade", "Hernandez", "Colon", "Frazier", "McCall", "Fletcher", "Short", "Silva", "Spencer", "Bell", "Jaramillo", "Rogers", "Marin", "Stephenson", "Mitchell", "Nielsen", "Price", "Booker", "Rollins", "Rogers", "Lee", "Hahn", "Avila", "Weiss", "Schultz", "Fry", "Montgomery", "Marsh", "Galindo", "Carr", "Scott", "Russo", "Medina", "Keith", "Pittman", "Cain", "O’Donnell", "Garcia", "Bruce", "McGee", "Esparza", "Deleon", "Leal", "Allison", "Watson", "Bravo", "Patrick", "Ray", "Harrison", "Peters", "Sanford", "Leon", "Livingston", "Huerta", "Torres", "Chung", "Watkins", "Barnes", "Hamilton", "Stout", "Kirk", "McGuire", "Burke", "Craig", "Watson", "Schneider", "Booth", "Howard", "Richard", "Osborne", "Burns", "Lin", "Webb"};
    static String[] tussenVoegsels = {"van", "de", "van der"};
    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static Random random = new Random();

    public static Reiziger generateReiziger(int id) {
        Reiziger reiziger = new Reiziger();

        reiziger.setReizigerId(id);
        reiziger.setVoorletters(getRandomVoorletter());
        reiziger.setAchternaam(getRandomLastName());
        reiziger.setTussenvoegsel(getRandomTussenvoegsel());
        reiziger.setGeboortedatum(getRandomDateOfBirth());
        reiziger.setOvChipkaart(new ArrayList<>());

        return reiziger;
    }

    public static void assertEquals(Reiziger expected, Reiziger actual) {
        assertAll(
                () -> Assertions.assertEquals(expected.getReizigerId(), actual.getReizigerId()),
                () -> Assertions.assertEquals(expected.getVoorletters(), actual.getVoorletters()),
                () -> Assertions.assertEquals(expected.getTussenvoegsel(), actual.getTussenvoegsel()),
                () -> Assertions.assertEquals(expected.getAchternaam(), actual.getAchternaam()),
                () -> Assertions.assertEquals(expected.getGeboortedatum(), actual.getGeboortedatum())
        );
    }

    private static String getRandomLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    private static String getRandomVoorletter() {
        return Character.toString(alphabet.charAt(random.nextInt(alphabet.length())));
    }

    private static String getRandomTussenvoegsel() {
        return tussenVoegsels[random.nextInt(tussenVoegsels.length)];
    }

    private static Date getRandomDateOfBirth() {
        int randomYear = random.nextInt(80) + 1940;
        int randomMonth = random.nextInt(12) + 1;
        int randomDay = random.nextInt(26) + 1;

        return Date.valueOf(String.format("%d-%d-%d", randomYear, randomMonth, randomDay));
    }
}
