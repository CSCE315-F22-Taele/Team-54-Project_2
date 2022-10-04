import csv
from random import randint
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

f = open("Employee.csv", "w")
csvWrite = csv.writer(f)
csvWrite.writerow(['Employees'])
csvWrite.writerow(['Employee ID, First Name, Last Name, Pay Rate, Role, Start Date, Manager'])

# Example Format - 4,Beth,Stewart,15,Kitchen staff,08/14/2021,false

# Fields: Employee ID,First Name,Last Name,Pay Rate,Role,Start Date,Manager

getRandomID = lambda IDlength: randint(10**IDlength-1, 10**IDlength)

