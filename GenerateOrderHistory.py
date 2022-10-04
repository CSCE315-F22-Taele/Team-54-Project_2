from datetime import datetime
import csv




# The orders table needs to be: Order ID, Order Number, Total Price Due, Date, Employee ID, Customer ID, Order Satisfied, Items Ordered

menu_items = {
    "Chick-fil-A Chicken Sandwich":	3.05,
    "Chick-fil-A Chicken Sandwich – Combo":	5.95,
    "Chick-fil-A Chicken Deluxe Sandwich":	3.65,
    "Chick-fil-A Chicken Deluxe Sandwich – Combo": 6.55,
    "Spicy Chicken Sandwich":  3.29,
    "Spicy Chicken Sandwich – Combo": 6.19,
    "Spicy Chicken Deluxe Sandwich": 3.89,
    "Spicy Chicken Deluxe Sandwich – Combo": 6.79,
    "Chick-fil-A Nuggets 8 Pc.": 3.05,
    # "Chick-fil-A Nuggets	12 Pc.	4.45
    "Chick-fil-A Nuggets – Combo 8 Pc.": 5.95,
    # "Chick-fil-A Nuggets – Combo	12 Pc.	8.59
    "Chick-fil-A Nuggets (Grilled) 8 Pc.": 3.85,
    # "Chick-fil-A Nuggets (Grilled)	12 Pc.	5.75
    # "Chick-fil-A Nuggets (Grilled) – Combo	8 Pc.	6.75
    # "Chick-fil-A Nuggets (Grilled) – Combo	12 Pc.	8.59
    "Chick-n-Strips	3 Pc.":	3.35,
    "Chick-n-Strips – Combo	3 Pc.":	6.25,
    "Grilled Chicken Sandwich":	4.39,
    "Grilled Chicken Sandwich – Combo":	7.19,
    "Grilled Chicken Club Sandwich": 5.59,
    "Grilled Chicken Club Sandwich – Combo": 8.39,
    "Chicken Salad Sandwich": 3.99,
    "Chicken Salad Sandwich – Combo": 6.79,
    "Grilled Chicken Cool Wrap": 5.19,
    "Soup & Salad (Large Chicken Soup and Side Salad)":	8.35,
    "Substitute Medium Premium Side":	1.00,
    "Upsize Fries & Drink":0.46,
    "Yogurt Parfait":	2.45,
    # Fruit Cup	Small	$2.05
    # Fruit Cup	Medium	$2.75
    # Fruit Cup	Large	$4.25
    "Side Salad": 2.89,
    "Chicken Soup":	2.65,
    # Chicken Soup	Large	$4.35
    # Chicken Tortilla Soup (Limited Time)	Medium	$3.75
    # Chicken Tortilla Soup (Limited Time)	Large	$6.05
    # Superfood Salad (Limited Time)	Medium	$2.89
    # Superfood Salad (Limited Time)	Large	$3.89
   " Chicken Salad Cup": 3.19, 
    # Chicken Salad Cup	Large	$5.1
    # Loaded Baked Potato (Limited Time)		$2.65
}




