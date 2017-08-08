public class Item(Cart) {

	// break lists into singles items. is this where this goes? do I create 
	// instances inside the item class and intake each one of the list.
	private double beforetax = 0;
	private double tax = 0;
	private double total = 0;
	private boolean = exempt; 
	// books, food, and medical, 10% 
	private boolean = duty; 
	private String = name;
	// 5%

	// cart array 

	// says if its duty free or tax exempt

	Cart input1 = new Cart(item1, item2, item3);
	// if I do it this way where do they items get their attributes? also does this
	// belong in main 

	

	public Item(double price, duty, exempt, string) {
		double beforetax = price;
		boolean duty = if (item.name.contains? "imported" = true) ? false;
		// refer to database of foods, only populated with chocolate
		boolean exempt = if (food = true) ? false;
		String name = string;
			


	};


	public getItem() {

	}


	public double Tax(double item.price, boolean duty, boolean exempt) {
		aftertax = item.price
		duty = boolean item.duty
		exempt = boolean item.exempt
		if (duty == true) {
			aftertax = aftertax + (aftertax * .05)
		}

		if (item.exempt == false) {
			aftertax = (item.price * .1) + item.price
		}

		return aftertax 

	}

	// instead of Car.each.tax(); ?
	afterTax = item.tax();

}



