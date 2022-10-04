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

f = open("Employees.csv", "a") # Appends to the file
csvWrite = csv.writer(f)
# Uncomment if building file from scratch
# csvWrite.writerow(['Employees'])
# csvWrite.writerow(['Employee ID, First Name, Last Name, Pay Rate, Role, Start Date, Manager'])

# Example Format - 4,Beth,Stewart,15,Kitchen staff,08/14/2021,false

# Fields: Employee ID,First Name,Last Name,Pay Rate,Role,Start Date,Manager

employees = []

CASHIERPAYRATE = 15
KITCHENPAYRATE = 15

getRandomID = lambda IDlength: randint(10**(IDlength-1), 10**(IDlength))

genders = ["Male", "Female"] # Only options provided by the library. Do not reflect my views.

for i in range(2): # We need 2 more cashiers
    g = choice(genders)
    employees.append([getRandomID(6), names.get_first_name(gender = g), names.get_last_name(),  CASHIERPAYRATE, "Cashier", "10/4/21", False])

csvWrite.writerows(employees)
f.close()

