package user;

// 1) Class Definition
// 3) Abstract Classes/Interfaces
public abstract class GeneralUser {
	protected String name;
	protected int age;
	protected String gender;
	protected double height;
	protected double weight;

	public GeneralUser(String name, int age, String gender, double height, double weight) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
	}
	
	

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public double getHeight() {
		return height;
	}



	public void setHeight(double height) {
		this.height = height;
	}



	public double getWeight() {
		return weight;
	}



	public void setWeight(double weight) {
		this.weight = weight;
	}


    //Abstract method
	public abstract double calculateBMI();
}
