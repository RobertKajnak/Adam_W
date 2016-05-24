package DataStructures;

import java.io.InvalidObjectException;

public class Lecture {

	private long id;

	public String denumire;

	// public Professor p;

	public int credite;// no unsigned byte in java

	public int prof;

	public Lecture(long id, String denumire, int credite, int profesor) {
		this.id = id;
		this.denumire = denumire;
		prof = profesor;
		this.credite = credite;
	}

	public Lecture(String denumire, int credite, int prof) {
		super();
		this.denumire = denumire;
		this.prof = prof;
		this.credite = credite;
	}

	public long getId() {
		return id;
	}

	public void print() { // Mainly for debugging purposes
		System.out.print("ID: " + id);
		System.out.print(", Denumire: " + denumire);
		System.out.print(", Credite: " + credite);
		System.out.println(", Professor: " + prof);
	}

	@Override
	public String toString() {
		return "ID: " + id + ", Denumire: " + denumire + ", Credite: " + credite
				+ ", Professor: " + prof;
	}

	public String serialize() {
		return "curs" + '\f' + id + '\f' + denumire + '\f' + credite + '\f' + prof;
	}

	public static Lecture deserialize(String s) throws InvalidObjectException {
		String temp[] = s.split("\f");
		if (!temp[0].equals("curs") || temp.length != 5) {
			throw new InvalidObjectException("String not valid");
		} else {
			Lecture lecture = new Lecture(0, "",0, 0);
			try {
				lecture.id = Integer.parseInt(temp[1]);
				lecture.credite = Integer.parseInt(temp[3]);
				lecture.prof = Integer.parseInt(temp[4]);
				lecture.denumire = temp[2];
				return lecture;
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
		Lecture other = (Lecture) obj;
		if (credite != other.credite)
			return false;
		if (denumire == null) {
			if (other.denumire != null)
				return false;
		} else if (!denumire.equals(other.denumire))
			return false;
		if (id != other.id)
			return false;
		if (prof != other.prof)
			return false;
		return true;
	}

}
