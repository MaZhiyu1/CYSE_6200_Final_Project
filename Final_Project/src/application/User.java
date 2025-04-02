package application;
//2) Inheritance/Polymorphism

public class User extends GeneralUser {
	public User(String name, int age, String gender, double height, double weight) {
		super(name, age, gender, height, weight);
	}

	@Override
	public double calculateBMI() {
		return weight / ((height / 100) * (height / 100));
	}
}
