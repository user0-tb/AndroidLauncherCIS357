# AndroidLauncherCIS357
A simple Android launcher
Authors: Kyler Kupres, Tim VanDyke

This project was designed to show up the features of a home screen launcher as well as showing customizable features.

# Functionality:
- A phone will ask the user if it wants to set the home launcher to ours, and will then ask for storage permission.
- There is a bottom drawer which can be swiped up to show all of the apps which are installed on a phone.
- To move an app from the drawer to the home screen, the user long clicks the app, and will then tap on a grid space on the main screen.\
- Pages on the home screen can be swiped throuh using right or left gestures.
- The plus button (+) will add a page to the home screen, (up to 10 pages)
- The minus button (-) will remove the last page added from the screen. This will not remove the very first screen ever.
- The photo icon is pressed when the user desires to change the background image of the launcher
- The gear icon is a list of settings which the user can change.

# The Settings Menu:
# - Color Settings -
    - These settings are used to change the color of the navigation bar at the bottom of the screen as well as the status bar at the
    top of the screen. This does not persist to any other screen and is currently just experimental.
# - System Settings - 
    - This launches the phones system settings, we added this here for easy access in case the user wants to change any settings quickly
# - Launcher Info -
    - Displays a page which has Author information as well as a few other statistics about the launcher itself. We wanted to add more
    phone details here but for now it only shows a few things.
# - Installed Apps - 
    - This is not hooked up currently, it was supposed to be a populated list of apps that are currently installed within a Linear Layout
    but we figured it was best to leave this ommited for our final result.
# - The page size / grid size - 
    - Launching the settings menu when on a certain page of the home screen will pass the current screen number to settings. It retreives 
    how many spots there are on the screen currently (20 by default) and the user is able to type in a number greater than 1 and less than 
    or equal to 100 for the grid size here. 
        - NOTE: A page with greater than 20 grid spots will convert into a vertically scrolling page. You can swipe left and right between
                other pages still but a page with more than 20 spots will also have the ability to scroll up and down through the added 
                spots.
                
                
    

