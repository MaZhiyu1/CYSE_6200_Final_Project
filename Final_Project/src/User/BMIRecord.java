package User;

//1) Class Definition
public class BMIRecord {
	private static int counter = 0;

	private final int id;
	private final GeneralUser user;
	private final double bmi;

	public BMIRecord(GeneralUser user) {
		this.id = ++counter;
		this.user = user;
		this.bmi = user.calculateBMI();
	}

	public int getId() {
		return id;
	}

	public double getHeight() {
		return user.height;
	}

	public double getWeight() {
		return user.weight;
	}

	public double getBmi() {
		return bmi;
	}

	public String getUserName() {
		return user.name;
	}
}

