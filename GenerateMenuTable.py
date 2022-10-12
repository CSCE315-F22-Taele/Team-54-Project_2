from datetime import datetime
import csv
from random import choice, randint, uniform

# CATEGORY NOT ADDED, PLS ADD IT PLSSSSSSS
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
    "Nuggets - 4 Pc.": 3.75,
    "Nuggets - 6 Pc.": 4.65,
    "Chick-n-Strips 1 Pc.": 3.05,
    "Chick-n-Strips 2 Pc.": 4.09,
}

menu_to_inventory_items = {
    "Chick-fil-A Chicken Sandwich":	{"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickle": 2},
    "Chick-fil-A Chicken Sandwich – Combo":	{"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickle": 2, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Chick-fil-A Chicken Deluxe Sandwich":	{"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickles": 2, "Lettuce": 1, "Tomato": 0.5, "American Cheese": 1},
    "Chick-fil-A Chicken Deluxe Sandwich – Combo": {"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickles": 2, "Lettuce": 1, "Tomato": 0.5, "American Cheese": 1, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Spicy Chicken Sandwich":  {"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickle": 2},
    "Spicy Chicken Sandwich – Combo": {"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickle": 2, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Spicy Chicken Deluxe Sandwich": {"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickles": 2, "Lettuce": 1, "Tomato": 0.5, "Pepper Jack Cheese": 1},
    "Spicy Chicken Deluxe Sandwich – Combo": {"Bread": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Pickles": 2, "Lettuce": 1, "Tomato": 0.5, "Pepper Jack Cheese": 1, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Chick-fil-A Nuggets 8 Pc.": {"Chicken nuggets": 8},
    "Chick-fil-A Nuggets – Combo 8 Pc.": {"Chicken nuggets": 8, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Chick-fil-A Nuggets (Grilled) 8 Pc.": {"Chicken nuggets": 8},
    "Chick-n-Strips 3 Pc.":	{"Chicken strips": 3},
    "Chick-n-Strips – Combo 3 Pc.":	{"Chicken strips": 3, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Grilled Chicken Sandwich":	{"Sesame Bread": 1, "Chicken Breast": 1, "Lettuce": 1, "Tomato": 0.5, "Honey BBQ sauce": 1},
    "Grilled Chicken Sandwich – Combo":	{"Sesame Bread": 1, "Chicken Breast": 1, "Lettuce": 1, "Tomato": 0.5, "Honey BBQ sauce": 1, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Grilled Chicken Club Sandwich": {"Sesame Bread": 1, "Chicken Breast": 1, "Lettuce": 1, "Tomato": 0.5, "Honey BBQ sauce": 1, "Colby Jack Cheese": 1, "Bacon": 0.125},
    "Grilled Chicken Club Sandwich – Combo": {"Sesame Bread": 1, "Chicken Breast": 1, "Lettuce": 1, "Tomato": 0.5, "Honey BBQ sauce": 1, "Colby Jack Cheese": 1, "Bacon": 0.125, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Grilled Chicken Cool Wrap": {"Flat bread": 1, "Lettuce": 1, "Cheddar Cheese": 0.05, "Avocado Lime Ranch": 1},
    "Grilled Chicken Cool Wrap - Combo": {"Flat bread": 1, "Lettuce": 1, "Cheddar Cheese": 0.05, "Avocado Lime Ranch": 1, "Waffle Fries": 0.25, "Coca-cola": 0.125},
    "Greek Yogurt Parfait": {"Greek Yogurt": 0.125, "Granola": 0.05, "Cookie crumbs": 0.05, "Fresh berries": 0.05},
    "Fruit Cup Small": {"Mixed fruits": 0.125},
    "Fruit Cup Medium": {"Mixed fruits": 0.25},
    "Fruit Cup Large": {"Mixed fruits": 0.5},
    "Chicken Soup":	{"Chicken noodle soup": 1},
    "Waffle Potato Fries - Small": {"Waffle Fries": 0.125},
    "Waffle Potato Fries - Medium": {"Waffle Fries": 0.25},
    "Waffle Potato Fries - Large": {"Waffle Fries": 0.5},
    "Chick-n-Minis": {"Chicken nuggets": 4, "Yeast roll": 4},
    "Hash Browns": {"Hash Brown": 0.125},
    "Chicken Biscuit": {"Buttermilk biscuit": 1},
    "Spicy Chicken Biscuit": {"Buttermilk biscuit": 1},
    "Hash Brown Scramble Bowl": {"Chicken nuggets": 4, "Hash Brown": 0.125, "Egg": 1, "Cheddar Cheese": 0.05, "Jalapeno salsa": 1},
    "Hash Brown Scramble Burrito": {"Chicken nuggets": 4, "Hash Brown": 0.125, "Egg": 1, "Cheddar Cheese": 0.05, "Jalapeno salsa": 1, "Tortilla": 1},
    "Chicken, Egg, & Cheese Biscuit": {"Buttermilk biscuit": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Egg": 1, "American Cheese": 1},
    "Breakfast Filets": {"Filet": 1},
    "Buttered Biscuit": {"Buttermilk biscuit": 1, "Butter": 0.0325},
    "Egg White Grill": {"English muffin": 1, "Egg": 1, "American Cheese": 1},
    "Bacon, Egg, & Cheese Biscuit": {"Buttermilk biscuit": 1, "Bacon": 0.125, "Peanut Oil": 0.03125, "Egg": 1, "American Cheese": 1},
    "English Muffin": {"English muffin": 1},
    "Sausage, Egg, & Cheese Biscuit": {"Buttermilk biscuit": 1, "Sausage": 1, "Peanut Oil": 0.03125, "Egg": 1, "American Cheese": 1},
    "Chicken, Egg, & Cheese Muffin": {"English muffin": 1, "Chicken Breast": 1, "Peanut Oil": 0.03125, "Egg": 1, "American Cheese": 1},
    "Bacon, Egg, & Cheese Muffin": {"English muffin": 1, "Bacon": 0.125, "Peanut Oil": 0.03125, "Egg": 1, "American Cheese": 1},
    "Kale Crunch": {"Kale crunch": 1},
    "Waffle Potato Chips": {"Waffle potato chips": 1},
    "Buddy Fruits Apple Sauce": {"Apple sauce": 1},
    "Coca-Cola": {"Coca-cola": 0.125},
    "Diet Coke": {"Diet coke": 0.125},
    "Dr Pepper": {"Dr Pepper": 0.125},
    "Iced Coffee": {"Iced coffee": 0.125},
    "Sprite": {"Sprite": 0.125},
    "Freshly Brewed Iced Tea Sweetened": {"Iced-tea sweetened": 0.125},
    "Gallon Beverage": {"Gallon beverage": 1},
    "Simply Orange": {"Simply orange": 0.125},
    "Lemonade": {"Lemonade": 0.125},
    "Diet Lemonade": {"Diet lemonade": 0.125},
    "Freshly Brewed Iced Tea Unsweetened": {"Iced-tea unsweetened": 0.125},
    "Sunjoy (1/2 Sweet Tea, 1/2 Lemonade)": {"Iced-tea sweetened": 0.125, "Lemonade": 0.125},
    "Sunjoy (1/2 Sweet Tea, 1/2 Diet Lemonade)": {"Iced-tea sweetened": 0.125, "Diet lemonade": 0.125},
    "1/2 Sweet Tea, 1/2 Unsweet Tea": {"Iced-tea sweetened": 0.125, "Iced-tea unsweetened": 0.125},
    "1% Chocolate Milk": {"Chocolate milk": 0.125},
    "Honest Kids Apple Juice": {"Honest Kids Apple Juice": 1},
    "Sunjoy (1/2 Unsweet Tea, 1/2 Diet Lemonade)": {"Iced-tea unsweetened": 0.125, "Diet lemonade": 0.125},
    "Sunjoy (1/2 Unsweet Tea, 1/2 Lemonade)": {"Iced-tea unsweetened": 0.125, "Lemonade": 0.125},
    "1/2 Lemonade, 1/2 Diet Lemonade": {"Diet lemonade": 0.125, "Lemonade": 0.125},
    "Dasani Bottled Water": {"Dasani Bottled water": 1},
    "Cobb Salad": {"Cobb salad": 1},
    "Spicy Southwest Salad": {"Southwest salad": 1},
    "Grilled Market Salad": {"Market salad": 1},
    "Grilled Market Salad w/ Chick-fil-A Filet": {"Market salad": 1, "Filet": 1},
    "Grilled Market Salad w/ Chick-n-Strips": {"Market salad": 1, "Chicken strips": 1},
    "Grilled Market Salad w/ Grilled Filet": {"Market salad": 1, "Filet": 1},
    "Grilled Market Salad w/ Grilled Nuggets": {"Market salad": 1, "Chicken nuggets": 1},
    "Grilled Market Salad w/ Nuggets": {"Market salad": 1, "Chicken nuggets": 1},
    "Grilled Market Salad w/ Spicy Filet": {"Market salad": 1, "Filet": 1},
    "Grilled Market Salad w/ Spicy Grilled Filet": {"Market salad": 1, "Filet": 1},
    "Frosted Coffee": {"Frosted coffee": 0.125},
    "Cookies & Cream Milkshake": {"Cookies and Cream milkshake": 0.125},
    "Chocolate Fudge Brownie": {"Chocolate fudge brownie": 1},
    "Chocolate Chunk Cookie": {"Chocolate chunk cookie": 1},
    "Strawberry Milkshake": {"Strawberry milkshake": 0.125},
    "Autumn Spice Milkshake": {"Autumn spice milkshake": 0.125},
    "Frosted Lemonade": {"Lemonade": 0.125},
    "Chocolate Milkshake": {"Chocolate milkshake": 0.125},
    "Vanilla Milkshake": {"Vanilla milkshake": 0.125},
    "Nuggets - 4 Pc.": {"Chicken nuggets": 4},
    "Nuggets - 6 Pc.": {"Chicken nuggets": 6},
    "Nuggets - 4 Pc.": {"Chicken nuggets": 4},
    "Nuggets - 6 Pc.": {"Chicken nuggets": 6},
    "Chick-n-Strips 1 Pc.": {"Chicken strips": 1},
    "Chick-n-Strips 2 Pc.": {"Chicken strips": 2},
}

csvFile = open("Menu.csv", "w", newline = "")
cWrite = csv.writer(csvFile)
IDlength = 6
getRandomID = lambda IDlength: randint(10 ** (IDlength - 1), 10 ** (IDlength))
menu_list = []
menu_num = 0
for key in menu_items:
    menu_num += 1
    menu_list.append([menu_num, key.replace('–',''), menu_items[key], menu_to_inventory_items[key]])  

cWrite.writerows(menu_list)
csvFile.close()