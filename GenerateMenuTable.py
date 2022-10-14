from datetime import datetime
import csv
from random import choice, randint, uniform

menu_items = {
    "Chick-fil-A Chicken Sandwich":	[3.05, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickle 2"]],
    "Chick-fil-A Chicken Sandwich - Combo":	[5.95, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickle 2", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Chick-fil-A Chicken Deluxe Sandwich":	[3.65, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickles 2", "Lettuce 2", "Tomato 0.5", "American Cheese 1"]],
    "Chick-fil-A Chicken Deluxe Sandwich - Combo": [6.55, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickles 2", "Lettuce 1", "Tomato 0.5", "American Cheese 1", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Spicy Chicken Sandwich":  [3.29, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickle 2"]],
    "Spicy Chicken Sandwich - Combo": [6.19, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickle 2", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Spicy Chicken Deluxe Sandwich": [3.89, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickles 2", "Lettuce 1", "Tomato 0.5", "Pepper Jack Cheese 1"]],
    "Spicy Chicken Deluxe Sandwich - Combo": [6.79, "Entree", ["Bread 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Pickles 2", "Lettuce 1", "Tomato 0.5", "Pepper Jack Cheese 1", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Chick-fil-A Nuggets 8 Pc.": [3.05, "Entree", ["Chicken nuggets 8"]],
    "Chick-fil-A Nuggets - Combo 8 Pc.": [5.95, "Entree", ["Chicken nuggets 8", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Chick-fil-A Nuggets (Grilled) 8 Pc.": [3.85, "Entree", ["Chicken nuggets 8"]],
    "Chick-n-Strips 3 Pc.":	[3.35, "Sides", ["Chicken strips 3"]],
    "Chick-n-Strips - Combo 3 Pc.":	[6.25, "Sides", ["Chicken strips 3", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Grilled Chicken Sandwich":	[4.39, "Entree", ["Sesame Bread 1", "Chicken Breast 1", "Lettuce 1", "Tomato 0.5", "Honey BBQ sauce 1"]],
    "Grilled Chicken Sandwich - Combo":	[7.19, "Entree", ["Sesame Bread 1", "Chicken Breast 1", "Lettuce 1", "Tomato 0.5", "Honey BBQ sauce 1", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Grilled Chicken Club Sandwich": [5.59, "Entree", ["Sesame Bread 1", "Chicken Breast 1", "Lettuce 1", "Tomato 0.5", "Honey BBQ sauce 1", "Colby Jack Cheese 1", "Bacon 0.125"]],
    "Grilled Chicken Club Sandwich - Combo": [8.39, "Entree", ["Sesame Bread 1", "Chicken Breast 1", "Lettuce 1", "Tomato 0.5", "Honey BBQ sauce 1", "Colby Jack Cheese 1", "Bacon 0.125", "Waffle Fries 0.125", "Coca-cola 0.125"]],
    "Grilled Chicken Cool Wrap": [5.19, "Entree", ["Flat bread 1", "Lettuce 1", "Cheddar Cheese 0.05", "Avocado Lime Ranch 1"]],
    "Grilled Chicken Cool Wrap - Combo": [8.15, "Entree", ["Flat bread 1", "Lettuce 1", "Cheddar Cheese 0.05", "Avocado Lime Ranch 1", "Waffle Fries 0.25", "Coca-cola 0.125"]],
    "Greek Yogurt Parfait": [2.45, "Breakfast", ["Greek Yogurt 0.125", "Granola 0.05", "Cookie crumbs 0.05", "Fresh berries 0.05"]],
    "Fruit Cup Small": [2.05, "Breakfast", ["Mixed fruits 0.125"]],
    "Fruit Cup Medium": [2.75, "Breakfast", ["Mixed fruits 0.25"]],
    "Fruit Cup Large": [4.25, "Breakfast", ["Mixed fruits 0.5"]],
    "Chicken Soup":	[2.65, "Sides", ["Chicken noodle soup 1"]],
    "Waffle Potato Fries - Small": [1.55, "Sides", ["Waffle Fries 0.125"]],
    "Waffle Potato Fries - Medium": [1.65, "Sides", ["Waffle Fries 0.25"]],
    "Waffle Potato Fries - Large": [1.85, "Sides", ["Waffle Fries 0.5"]],
    "Chick-n-Minis": [4.99, "Breakfast", ["Chicken nuggets 4", "Yeast roll 4"]],
    "Hash Browns": [1.69, "Breakfast", ["Hash Brown 0.125"]],
    "Chicken Biscuit": [2.26, "Breakfast", ["Buttermilk biscuit 1", "Chicken breast 1"]],
    "Spicy Chicken Biscuit": [2.50, "Breakfast", ["Buttermilk biscuit 1", "Chicken breast 1"]],
    "Hash Brown Scramble Bowl": [5.59, "Breakfast", ["Chicken nuggets 4", "Hash Brown 0.125", "Egg 1", "Cheddar Cheese 0.05", "Jalapeno salsa 1"]],
    "Hash Brown Scramble Burrito": [5.59, "Breakfast", ["Chicken nuggets 4", "Hash Brown 0.125", "Egg 1", "Cheddar Cheese 0.05", "Jalapeno salsa 1", "Tortilla 1"]],
    "Chicken and Egg and Cheese Biscuit": [4.99, "Breakfast", ["Buttermilk biscuit 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Egg 1", "American Cheese 1"]],
    "Breakfast Filets": [2.19, "Breakfast", ["Filet 1"]],
    "Buttered Biscuit": [1.59, "Breakfast", ["Buttermilk biscuit 1", "Butter 0.0325"]],
    "Egg White Grill": [3.64, "Breakfast", ["English muffin 1", "Egg 1", "American Cheese 1"]],
    "Bacon Egg & Cheese Biscuit": [4.35, "Breakfast", ["Buttermilk biscuit 1", "Bacon 0.125", "Peanut Oil 0.03125", "Egg 1", "American Cheese 1"]],
    "English Muffin": [1.95, "Breakfast", ["English muffin 1"]],
    "Sausage Egg & Cheese Biscuit": [4.65, "Breakfast", ["Buttermilk biscuit 1", "Sausage 1", "Peanut Oil 0.03125", "Egg 1", "American Cheese 1"]],
    "Chicken Egg & Cheese Muffin": [5.29, "Breakfast", ["English muffin 1", "Chicken Breast 1", "Peanut Oil 0.03125", "Egg 1", "American Cheese 1"]],
    "Bacon Egg & Cheese Muffin": [4.65, "Breakfast", ["English muffin 1", "Bacon 0.125", "Peanut Oil 0.03125", "Egg 1", "American Cheese 1"]],
    "Kale Crunch": [2.79, "Sides", ["Kale crunch 1"]],
    "Waffle Potato Chips": [2.59, "Sides", ["Waffle potato chips 1"]],
    "Buddy Fruits Apple Sauce": [2.85, "Breakfast", ["Apple sauce 1"]],
    "Coca-Cola": [2.45, "Drinks", ["Coca-cola 0.125"]],
    "Diet Coke": [2.45, "Drinks", ["Diet coke 0.125"]],
    "Dr Pepper": [2.45, "Drinks", ["Dr Pepper 0.125"]],
    "Iced Coffee": [3.85, "Drinks", ["Iced coffee 0.125"]],
    "Sprite": [2.45, "Drinks", ["Sprite 0.125"]],
    "Freshly Brewed Iced Tea Sweetened": [2.45, "Drinks", ["Iced-tea sweetened 0.125"]],
    "Gallon Beverage": [15.59, "Drinks", ["Gallon beverage 1"]],
    "Simply Orange": [3.59, "Drinks", ["Simply orange 0.125"]],
    "Lemonade": [2.89, "Drinks", ["Lemonade 0.125"]],
    "Diet Lemonade": [2.89, "Drinks", ["Diet lemonade 0.125"]],
    "Freshly Brewed Iced Tea Unsweetened": [2.45, "Drinks", ["Iced-tea unsweetened 0.125"]],
    "Sunjoy (1/2 Sweet Tea 1/2 Lemonade)": [2.89, "Drinks", ["Iced-tea sweetened 0.125", "Lemonade 0.125"]],
    "Sunjoy (1/2 Sweet Tea 1/2 Diet Lemonade)": [2.89, "Drinks", ["Iced-tea sweetened 0.125", "Diet lemonade 0.125"]],
    "1/2 Sweet Tea 1/2 Unsweet Tea": [2.45, "Drinks", ["Iced-tea sweetened 0.125", "Iced-tea unsweetened 0.125"]],
    "1% Chocolate Milk": [1.99, "Drinks", ["Chocolate milk 0.125"]],
    "Honest Kids Apple Juice": [1.99, "Drinks", ["Honest Kids Apple Juice 1"]],
    "Sunjoy (1/2 Unsweet Tea 1/2 Diet Lemonade)": [2.89, "Drinks", ["Iced-tea unsweetened 0.125", "Diet lemonade 0.125"]],
    "Sunjoy (1/2 Unsweet Tea 1/2 Lemonade)": [2.89, "Drinks", ["Iced-tea unsweetened 0.125", "Lemonade 0.125"]],
    "1/2 Lemonade 1/2 Diet Lemonade": [2.89, "Drinks", ["Diet lemonade 0.125", "Lemonade 0.125"]],
    "Dasani Bottled Water": [2.59, "Drinks", ["Dasani Bottled water 1"]],
    "Cobb Salad": [11.69, "Salads", ["Cobb salad 1"]],
    "Spicy Southwest Salad": [11.95, "Salads", ["Southwest salad 1"]],
    "Grilled Market Salad": [7.19, "Salads", ["Market salad 1"]],
    "Grilled Market Salad w/ Chick-fil-A Filet": [8.19, "Salads", ["Market salad 1", "Filet 1"]],
    "Grilled Market Salad w/ Chick-n-Strips": [8.19, "Salads", ["Market salad 1", "Chicken strips 1"]],
    "Grilled Market Salad w/ Grilled Filet": [8.19, "Salads", ["Market salad 1", "Filet 1"]],
    "Grilled Market Salad w/ Grilled Nuggets": [8.19, "Salads", ["Market salad 1", "Chicken nuggets 1"]],
    "Grilled Market Salad w/ Nuggets": [8.19, "Salads", ["Market salad 1", "Chicken nuggets 1"]],
    "Grilled Market Salad w/ Spicy Filet": [8.19, "Salads", ["Market salad 1", "Filet 1"]],
    "Grilled Market Salad w/ Spicy Grilled Filet": [8.19, "Salads", ["Market salad 1", "Filet 1"]],
    "Frosted Coffee": [5.05, "Treats", ["Frosted coffee 0.125"]],
    "Cookies & Cream Milkshake": [5.19, "Treats", ["Cookies and Cream milkshake 0.125"]],
    "Chocolate Fudge Brownie": [2.59, "Treats", ["Chocolate fudge brownie 1"]],
    "Chocolate Chunk Cookie": [1.89, "Treats", ["Chocolate chunk cookie 1"]],
    "Strawberry Milkshake": [5.19, "Treats", ["Strawberry milkshake 0.125"]],
    "Autumn Spice Milkshake": [5.59, "Treats", ["Autumn spice milkshake 0.125"]],
    "Frosted Lemonade": [5.05, "Treats", ["Lemonade 0.125"]],
    "Chocolate Milkshake": [5.19, "Treats", ["Chocolate milkshake 0.125"]],
    "Vanilla Milkshake": [5.19, "Treats", ["Vanilla milkshake 0.125"]],
    "Nuggets - 4 Pc.": [3.35, "Kids Meals", ["Chicken nuggets 4"]],
    "Nuggets - 6 Pc.": [4.05, "Kids Meals", ["Chicken nuggets 6"]],
    "Grilled Nuggets - 4 Pc.": [3.75, "Kids Meals", ["Chicken nuggets 4"]],
    "Grilled Nuggets - 6 Pc.": [4.65, "Kids Meals", ["Chicken nuggets 6"]],
    "Chick-n-Strips 1 Pc.": [3.05, "Kids Meals", ["Chicken strips 1"]],
    "Chick-n-Strips 2 Pc.": [4.09, "Kids Meals", ["Chicken strips 2"]],
}

csvFile = open("Menu.csv", "w", newline = "")
cWrite = csv.writer(csvFile)
cWrite.writerow(["Menu ID","Name","Price","Category","Ingredients used"])

IDlength = 6
getRandomID = lambda IDlength: randint(10 ** (IDlength - 1), 10 ** (IDlength))
menu_list = []
menu_num = 0
for key in menu_items:
    menu_num += 1
    menu_list.append([menu_num, key.replace(",", ""), menu_items[key][0], menu_items[key][1], " | ".join(menu_items[key][2])])  

# print(sorted(list(menu_items.keys())))


cWrite.writerows(menu_list)
csvFile.close()