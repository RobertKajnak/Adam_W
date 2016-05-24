package DataStructures;

import java.io.InvalidObjectException;


public class Professor {
	private long id;
	public String nume;
	public String prenume;
	public String departament;
	public String birou;
	
	public Professor(long id, String nume, String prenume, String departament, String birou){
		this.id=id;
		this.nume=nume;
		this.prenume=prenume;
		this.departament=departament;
		this.birou=birou;
	}
	public Professor(String nume, String prenume, String departament, String birou){
		this.nume=nume;
		this.prenume=prenume;
		this.departament=departament;
		this.birou=birou;
	}
	
	public long getId(){
		return id;
	}
	
	public void print(){ //Mainly for debugging purposes
        System.out.print("ID: " + id);
        System.out.print(", Nume: " + nume);
        System.out.print(", Prenume: " + prenume);
        System.out.print(", Departament: " + departament);
        System.out.println(", Birou: " + birou);
	}
	
	@Override
	public String toString() {
		return "Professor [id=" + id + ", nume=" + nume + ", prenume=" + prenume + ", departament=" + departament
				+ ", birou=" + birou + "]";
	}
	public String serialize() {
		return "prof" + '\f' + id + '\f' + nume + '\f' + prenume + '\f' + departament + '\f' + birou;
	}

	public static Professor deserialize(String s) throws InvalidObjectException {
		String temp[] = s.split("\f");
		if (!temp[0].equals("prof") || temp.length != 6) {
			throw new InvalidObjectException("String not valid");
		} else {
			Professor professor = new Professor( "","", "","");
			try {
				professor.id = Integer.parseInt(temp[1]);
				professor.nume = temp[2];
				professor.prenume = temp[3];
				professor.departament = temp[4];
				professor.birou = temp[5];
				return professor;
			} catch (NumberFormatException e) {
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
		Professor other = (Professor) obj;
		if (birou == null) {
			if (other.birou != null)
				return false;
		} else if (!birou.equals(other.birou))
			return false;
		if (departament == null) {
			if (other.departament != null)
				return false;
		} else if (!departament.equals(other.departament))
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
