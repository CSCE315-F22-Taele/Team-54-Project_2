from datetime import datetime
import csv
from random import randint, choice
import names # pip install names 

with open('Customers.csv', 'a', newline='') as f:
    csvWrite = csv.writer(f)
    # Uncomment if building file from scratch
    # csvWrite.writerow(['Employees'])
    # csvWrite.writerow(['Employee ID, First Name, Last Name, Pay Rate, Role, Start Date, Manager'])

    # Example Format - 4,Beth,Stewart,15,Kitchen staff,08/14/2021,false

    # Fields: Employee ID,First Name,Last Name,Pay Rate,Role,Start Date,Manager

    customers = []

    genders = ["Male", "Female"] # Only options provided by the library. Do not reflect my views.

    for i in range(19, 300): # We need 2 more cashiers
        g = choice(genders)
        customers.append([i, names.get_first_name(gender = g), names.get_last_name()])

    csvWrite.writerows(customers)