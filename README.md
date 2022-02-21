# HORTUS - A Plant Tracking Application
####(*Latin:* Garden)



*What does the application do?*
1) Allows user to create/delete individual profiles for each plant they own, 
each profile will show the plant name, species, days since purchase, watering status
   (last watering time, next watering time), and additional notes.
2) Has a to-do list that lists the upcoming watering time for each plant that allows 
users tick off the check-boxes once the task is done.


*Who will use it?*

 - plant carers who wish to record information about their plants and 
track their plant watering status.


*Why is this project of interest to me?*

- During summer break, I bought a lot of plants and had trouble keeping track of the time to water them. 
I tried many different apps on my phone, but they either require paid premium subscription
or doesn't include a watering reminder feature that I wanted. So I thought this would be useful tool for 
myself as well as other plant caretakers.

## User Stories

As a user, I want to be able to:

###PHASE 1
1) Add a plant to my garden (collection of plant profiles)
2) View my garden that shows all names of my plants
3) Select a plant in my garden and view its information
4) Check the watering status
5) Mark the current watering task for an individual plant as complete
<br><br>

###PHASE 2
1) save my garden to file
2) load my garden from file
<br><br>

###PHASE 4

#####Task 2

Mon Nov 22 14:47:45 PST 2021 <br>
Removed plant: Joe

Mon Nov 22 14:49:55 PST 2021 <br>
Added plant: Linda the Eucalyptus

Mon Nov 22 14:50:00 PST 2021 <br>
Linda has been watered.

Mon Nov 22 14:51:21 PST 2021 <br>
Vi's information has been changed!

Mon Nov 22 14:51:31 PST 2021 <br>
Alex's information has been changed!
<br><br><br>
#####Task 3

If you had more time to work on the project, is there any refactoring
that you would do to improve your design?

- GardenView and TodoView have very similar ui's (both have a list of plant profile
cards that are created with JLabels) and therefore could both extend an abstract class that 
contains their shared methods to reduce redundancy.
<br><br>
- The resizeImage and centerFrameLocation methods within HomeView class are used in every 
other "view" class, and every time they would be called with HomeView.resizeImage(...). 
I believe that it would make more sense for them to be all put into an individual class for 
styling the ui which improves cohesion.
<br><br>

