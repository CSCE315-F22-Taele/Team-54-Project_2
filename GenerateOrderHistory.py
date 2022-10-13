from datetime import datetime
import csv
from random import choice, randint, uniform, sample

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
    "Chick-fil-A Nuggets – Combo 8 Pc.": 5.95,
    "Chick-fil-A Nuggets (Grilled) 8 Pc.": 3.85,
    "Chick-n-Strips 3 Pc.":	3.35,
    "Chick-n-Strips – Combo 3 Pc.":	6.25,
    "Grilled Chicken Sandwich":	4.39,
    "Grilled Chicken Sandwich – Combo":	7.19,
    "Grilled Chicken Club Sandwich": 5.59,
    "Grilled Chicken Club Sandwich – Combo": 8.39,
    "Grilled Chicken Cool Wrap": 5.19,
    "Grilled Chicken Cool Wrap - Combo": 8.15,
    "Greek Yogurt Parfait": 2.45,
    "Fruit Cup Small": 2.05,
    "Fruit Cup Medium": 2.75,
    "Fruit Cup Large": 4.25,
    "Chicken Soup":	2.65,
    "Waffle Potato Fries - Small": 1.55,
    "Waffle Potato Fries - Medium": 1.65,
    "Waffle Potato Fries - Large": 1.85,
    "Chick-n-Minis": 4.99,
    "Hash Browns": 1.69,
    "Chicken Biscuit": 2.26,
    "Spicy Chicken Biscuit": 2.50,
    "Hash Brown Scramble Bowl": 5.59,
    "Hash Brown Scramble Burrito": 5.59,
    "Chicken, Egg, & Cheese Biscuit": 4.99,
    "Breakfast Filets": 2.19,
    "Buttered Biscuit": 1.59,
    "Egg White Grill": 3.64,
    "Bacon, Egg, & Cheese Biscuit": 4.35,
    "English Muffin": 1.95,
    "Sausage, Egg, & Cheese Biscuit": 4.65,
    "Chicken, Egg, & Cheese Muffin": 5.29,
    "Bacon, Egg, & Cheese Muffin": 4.65,
    "Kale Crunch": 2.79,
    "Waffle Potato Chips": 2.59,
    "Buddy Fruits Apple Sauce": 2.85,
    "Coca-Cola": 2.45,
    "Diet Coke": 2.45,
    "Dr Pepper": 2.45,
    "Iced Coffee": 3.85,
    "Sprite": 2.45,
    "Freshly Brewed Iced Tea Sweetened": 2.45,
    "Gallon Beverage": 15.59,
    "Simply Orange": 3.59,
    "Lemonade": 2.89,
    "Diet Lemonade": 2.89,
    "Freshly Brewed Iced Tea Unsweetened": 2.45,
    "Sunjoy (1/2 Sweet Tea, 1/2 Lemonade)": 2.89,
    "Sunjoy (1/2 Sweet Tea, 1/2 Diet Lemonade)": 2.89,
    "1/2 Sweet Tea, 1/2 Unsweet Tea": 2.45,
    "1% Chocolate Milk": 1.99,
    "Honest Kids Apple Juice": 1.99,
    "Sunjoy (1/2 Unsweet Tea, 1/2 Diet Lemonade)": 2.89,
    "Sunjoy (1/2 Unsweet Tea, 1/2 Lemonade)": 2.89,
    "1/2 Lemonade, 1/2 Diet Lemonade": 2.89,
    "Dasani Bottled Water": 2.59,
    "Cobb Salad": 11.69,
    "Spicy Southwest Salad": 11.95,
    "Grilled Market Salad": 7.19,
    "Grilled Market Salad w/ Chick-fil-A Filet": 8.19,
    "Grilled Market Salad w/ Chick-n-Strips": 8.19,
    "Grilled Market Salad w/ Grilled Filet": 8.19,
    "Grilled Market Salad w/ Grilled Nuggets": 8.19,
    "Grilled Market Salad w/ Nuggets": 8.19,
    "Grilled Market Salad w/ Spicy Filet": 8.19,
    "Grilled Market Salad w/ Spicy Grilled Filet": 8.19,
    "Frosted Coffee": 5.05,
    "Cookies & Cream Milkshake": 5.19,
    "Chocolate Fudge Brownie": 2.59,
    "Chocolate Chunk Cookie": 1.89,
    "Strawberry Milkshake": 5.19,
    "Autumn Spice Milkshake": 5.59,
    "Frosted Lemonade": 5.05,
    "Chocolate Milkshake": 5.19,
    "Vanilla Milkshake": 5.19,
    "Nuggets - 4 Pc.": 3.35,
    "Nuggets - 6 Pc.": 4.05,
    "Grilled Nuggets - 4 Pc.": 3.75,
    "Grilled Nuggets - 6 Pc.": 4.65,
    "Chick-n-Strips 1 Pc.": 3.05,
    "Chick-n-Strips 2 Pc.": 4.09,
}

print("Number of items in the menu:", len(menu_items))


# The orders table needs to be: Order ID, Order Number, Total Price Due, Date, Employee ID, Customer ID, Order Satisfied, Items Ordered
csvFile = open("Orders.csv", "w", newline = "")
cWrite = csv.writer(csvFile)
cWrite.writerow(["Order ID","Order Number","Total Price Due","Date","Employee ID","Customer ID","Order Satisfied","Items Ordered"])
# cWrite.writerow(["Order",,,,,,])
# cWrite.writerow(["Order ID", "Order Number", "Total Price Due", "Date", "Employee ID", "Customer ID", "Order Satisfied", "Items Ordered"])
# For now, we are going to let Order ID == Order Number

# Parameters required to fill up the order table.
IDlength = 6
OrderID = 10 ** (IDlength - 1)

# getRandomID = lambda IDlength: randint(10 ** (IDlength - 1), 10 ** (IDlength))
# Week 1 - 9/4 to 9/10 and 9/10 is a gameday
orders = []
ordersPerDay = 200
orderID = 0
total =  0
year = '2022'
month = '10'

finances = []
dayTotal = 0
ordernum = 0
for w in range(3): # Iterate through the weeks
    for d in range(7): # Iterate through the days in a week
        day = 4 + (7 * w) + d
        dayTotal = 0

        currDay = month + "/" + str(day) + "/" + year
        if d == 6 and w != 2: # (4 + (7 * w) + d)
            n = 450
        else:
            n = ordersPerDay

        while dayTotal = 0
        for order in range(n):
            dayTotal += total
            ordernum += 1
            customerOrdersList = []
            itemsinOrder = randint(1,5)
            customerOrdersList = sample(list(menu_items.keys(), itemsinOrder))
            # (           ["Order ID", "Order Number", "Total Price Due", "Date", "Employee ID", "Customer ID", "Order Satisfied", "Items Ordered"]
            orders.append([OrderID   , ordernum      , f"{var:.2f}"    , currDay, str(randint(2, 3)), str(randint(1, 299)), True, customerOrdersList])  
            OrderID += 1
        finances.append(dayTotal)      

cWrite.writerows(orders)
        
print(total)
print('finances:', finances)
# Week 2 - 9/11 - 9/17 and 9/17 is a gameday

# Week 3 - 9/18 - 9/24


csvFile.close()