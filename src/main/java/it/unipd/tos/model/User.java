////////////////////////////////////////////////////////////////////
// [LUCA] [CARTURAN] [1094033]
////////////////////////////////////////////////////////////////////

package it.unipd.tos.model;

public class User {

    private String name;
    private String surname;
    private int age;

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }
    
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true   
        if (o == this) { 
            return true; 
        } 
            
        // typecast o to Complex so that we can compare data members  
        User c = (User) o; 
          
        // Compare the data members and return accordingly  
        return this.name.equals(c.getName()) && this.surname.equals(c.getSurname()) && this.age == c.getAge(); 
    }
}