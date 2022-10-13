from datetime import datetime
import csv
from random import randint, choice
import names # pip install names 

with open('Customers.csv', 'a', newline='') as f:
    csvWrite = csv.writer(f)
    
    
    # Fields: Customer ID, First Name, Last Name

    customers = []

    genders = ["Male", "Female"] # Only options provided by the library. Do not reflect my views.

    for i in range(19, 300):
        g = choice(genders)
        customers.append([i, names.get_first_name(gender = g), names.get_last_name()])

    csvWrite.writerows(customers)