# My Personal Project - Manic Shooter Battlefield

## Proposal

**Manic Shooter Battlefield** is a manic shooter–style game that offers both local PvP modes. It is designed for fans of manic shooters who also enjoy playing with others. Personally, I enjoy manic shooter games but often find the limited number of levels and unlockable items unsatisfying. By adding a PvP mode, this game gives players more ways to stay engaged through direct competition and interaction with others.

Objectives:
- Basic manic shooter functionalities
    - Varies shooting depending on items obtained
    - Monster generation
    - Item drops
- Two player local controls
    - Health system, damage detection, and player death respawn mechanics
    - Level up system
    - Barrier system for blocking bullets
    - Player movement and camera control
    - UI elements for each player
    
    
## User Stories
- As a user, I want to **add an weapon** to my **weapon list**.
- As a user, I want to **view the list of weapons** in my weapon list.
- As a user, I want to shot bullets on the game canvas.
- As a user, I want to control my character movement with keyboard keys.
- As a user, I want to upgrade an item in my weapon list.
- As a user, I want to view my character health and experience status.
- As a user, I want to save my game progress, including player status, weapon list, and enemy status to file.
- As a user, when I start the game, I want to be given an option to load my game progress from file.

## Phase 4 Task 2
Fri Nov 28 12:50:53 PST 2025
 Enemy (Hp: 130, Damage: 1) is added to game canvas.
Fri Nov 28 12:50:58 PST 2025
 Item (x: 4637, y: 2603) is added to game canvas.
Fri Nov 28 12:50:58 PST 2025
 Enemy (Hp: 180, Damage: 1) is added to game canvas.
Fri Nov 28 12:51:01 PST 2025
 Player picked up weapon: (Bullets: 8, Damage: 14, Fire Rate: 180)
Fri Nov 28 12:51:03 PST 2025
 Enemy (Hp: 180, Damage: 1) is added to game canvas.
Fri Nov 28 12:51:08 PST 2025
 Item (x: 7256, y: 5940) is added to game canvas.
Fri Nov 28 12:51:08 PST 2025
 Enemy (Hp: 107, Damage: 1) is added to game canvas.
Fri Nov 28 12:51:14 PST 2025
 Enemy (Hp: 52, Damage: 1) is added to game canvas.
Fri Nov 28 12:51:18 PST 2025
 Item (x: 7815, y: 3326) is added to game canvas.
Fri Nov 28 12:51:19 PST 2025
 Enemy (Hp: 126, Damage: 1) is added to game canvas.
Fri Nov 28 12:51:19 PST 2025
 Player picked up weapon: (Bullets: 2, Damage: 10, Fire Rate: 180)
Fri Nov 28 12:51:21 PST 2025
 Player picked up weapon: (Bullets: 2, Damage: 18, Fire Rate: 180)
Fri Nov 28 12:51:24 PST 2025
 Enemy (Hp: 139, Damage: 1) is added to game canvas.
Fri Nov 28 12:51:28 PST 2025
 Item (x: 2670, y: 3463) is added to game canvas.
Fri Nov 28 12:51:29 PST 2025
 Enemy (Hp: 172, Damage: 1) is added to game canvas.
 
## Phase 4 Task 3
If i have more time working on this project, the first design change is that I would make a parent interface for object shape and children interfaces for different shape, show that the draw function can draw different kind of shapes based on the interface the objects implemented. Currently in my game all objects are in circular shape and this is definitely can be improved.

Another refactoring that I would have done if I have more time is to make Weapon, Bullets and BulletController as abstract classes so that I can create different kind of bullets children classes with special shooting paths.

Once the above change is done, a UI refactoring that I would like to do is that different weapon display differently in the ui and players can choose which weapon upgrade they like when they gain enough experience.

The above changes provide more variety in playing this game, and can make it more fun to play. 

Another change that I wanted to make is to associate weapon to player and add bullet list to player, this give me more flexibility to perform actions including change bullet paths when player press a button.

Also about the buttons, I think there is a design change that can be made so that I can reduce number of buttons classes. Currently I create one class for each button and I believe this can be improved.