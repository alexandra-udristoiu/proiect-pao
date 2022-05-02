package products;

import java.util.HashMap;

import person.Customer;

public class BookReview {
	
	private HashMap<Customer, Integer> reviews;

	public BookReview() {
		this.reviews = new HashMap<>();
	}
	
	public void addReview(Customer customer, int mark) {
		if (!reviews.containsKey(customer)) {
			reviews.put(customer, mark);
		}
	}
	
	public float getAverage() {
		if (reviews.size() == 0) {
			return 0;
		}
		float sum = 0;
		for (int i : reviews.values()) {
			sum += i;
		}
		return sum / reviews.size();
	}
	
	public int getNumberOfReviews() {
		return reviews.size();
	}

}
