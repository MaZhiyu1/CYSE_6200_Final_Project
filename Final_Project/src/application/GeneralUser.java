package application;
// 1) Class Definition
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

	public abstract double calculateBMI();
}
