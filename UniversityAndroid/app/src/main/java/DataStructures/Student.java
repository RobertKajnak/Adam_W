package DataStructures;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class Student implements Serializable{
	private long id;
	public String nume;
	public String prenume;
	public int an;
	public Student(long id, String nume, String prenume, int an){
		this.id=id;
		this.nume=nume;
		this.prenume=prenume;
		this.an=an;
	}
	
	public Student(String nume, String prenume, int an) {
		super();
		this.nume = nume;
		this.prenume = prenume;
		this.an = an;
	}

	public long getId(){
		return id;
	}
	public void print(){ //Mainly for debugging purposes
        System.out.print("ID: " + id);
        System.out.print(", Nume: " + nume);
        System.out.print(", Prenume: " + prenume);
        System.out.println(", An: " + an);
	}
	@Override
	public String toString(){
		return "ID: " + id+ ", Nume: " + nume+
			   ", Prenume: " + prenume+", An: " + an;
	}
	public String serialize(){
		return "elev"+'\f'+id+'\f'+nume+'\f'+prenume+'\f'+an;
	}
	public static Student deserialize(String s) throws  InvalidObjectException {
		String temp[]=s.split("\f");
		if (!temp[0].equals("elev") || temp.length!=5){
			throw new InvalidObjectException("String not valid");
		}
		else{
			Student elev=new Student("","",0);
			try{
				elev.id = Integer.parseInt(temp[1]);
				elev.an = Integer.parseInt(temp[4]);
				elev.nume = temp[2];
				elev.prenume = temp[3];
				return elev;
			}
			catch(NumberFormatException e){
				throw new InvalidObjectException("String not valid");
			}
			
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (an != other.an)
			return false;
		if (id != other.id)
			return false;
		if (nume == null) {
			if (other.nume != null)
				return false;
		} else if (!nume.equals(other.nume))
			return false;
		if (prenume == null) {
			if (other.prenume != null)
				return false;
		} else if (!prenume.equals(other.prenume))
			return false;
		return true;
	}


}
