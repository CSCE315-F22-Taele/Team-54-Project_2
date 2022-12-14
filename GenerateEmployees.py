from datetime import datetime
import csv
from random import randint, choice
import names # pip install names 

# This to add more workers.
# 20 Kitchen Staff
# 4 Cashiers
# 1 Manager

# Currently we have - 
# 1,Tom,Johnson,20,Manager,8/1/2022,true
# 2,Bob,Mathew,15,Cashier,9/20/2022,false
# 3,Scarlett,Smith,15,Cashier,05/02/2021,false
# 4,Beth,Stewart,15,Kitchen staff,08/14/2021,false
# 5,John,Quincy,15,Kitchen staff,01/25/2022,false

f = open("Employees.csv", "a", newline="") # Appends to the file
csvWrite = csv.writer(f)
# Uncomment if building file from scratch
# csvWrite.writerow(['Employees'])
# csvWrite.writerow(['Employee ID, First Name, Last Name, Pay Rate, Role, Start Date, Manager'])


# Fields: Employee ID,First Name,Last Name,Pay Rate,Role,Start Date,Manager

employees = []

CASHIERPAYRATE = 15
KITCHENPAYRATE = 15

getRandomID = lambda IDlength: randint(10 ** (IDlength - 1), 10 ** (IDlength))

genders = ["Male", "Female"] # Only options provided by the library. Do not reflect my views.

# currDay = datetime.day(year = '2021', month = '8', day = str(randint(1, 31)))

currDay = f"8/{randint(1, 31)}/2021"

for i in range(4): # We need 2 more cashiers
    g = choice(genders)
    employees.append([getRandomID(6), names.get_first_name(gender = g), names.get_last_name(),  CASHIERPAYRATE, "Cashier", str(currDay), False])

for i in range(15):
    g = choice(genders)
    employees.append([getRandomID(6), names.get_first_name(gender = g), names.get_last_name(),  KITCHENPAYRATE, "Kitchen staff", str(currDay), False])

csvWrite.writerows(employees)
f.close()

