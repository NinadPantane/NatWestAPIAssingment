package api.payload;

public class Payload {

	// POJO Class 
	
	String name;
	String createdAt;
	String Generation;
	String Price;
	String Capacity;

	
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getGeneration() {
		return Generation;
	}
	public void setGeneration(String generation) {
		Generation = generation;
	}
	public String getPrice() {
		return Price;
	}
	public void setPrice(String price) {
		Price = price;
	}
	public String getCapacity() {
		return Capacity;
	}
	public void setCapacity(String capacity) {
		Capacity = capacity;
	}
	
}
