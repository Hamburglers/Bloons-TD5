# Bloons-TD5
An advanced tower defense game using Java, inspired by the popular game Bloons TD5\

# GALLERY
97% Test Coverage with JacocoTestReport\
<img width="942" alt="Screenshot 2024-05-15 at 9 53 44 AM" src="https://github.com/Hamburglers/Bloons-TD5/assets/97949512/d5abb88c-6653-471b-8a3d-48dbb5dff381">
<img width="739" alt="Screenshot 2024-05-15 at 10 15 28 AM" src="https://github.com/Hamburglers/Bloons-TD5/assets/97949512/a9486e9d-df82-4494-a5d2-d3bd4643296e">
<img width="739" alt="Screenshot 2024-05-15 at 10 17 09 AM" src="https://github.com/Hamburglers/Bloons-TD5/assets/97949512/f3b56684-adcf-49c8-b6da-a5e874b2ed1f">


# HOW TO RUN
Make sure Gradle is installed\
In the main directory run\
`gradle run`\
or optionally build again then run\
`gradle build`\
`gradle run`

# CONFIGURATIONS
The config.json takes in a layout attribute which will need to be a file in the current directory.\
Here you can specify the map loaded\
' ' = grass\
'X' = path\
'W' = wizard house (goal)

• Grass – towers can be placed here

• Shrub – towers cannot be placed here

• Path – enemies traverse the path to try and reach the Wizard’s house

• Tower – it shoots fireballs at enemies within its range

• Wizard’s house – there can only be one on each map. If enemies reach here, they are banished by the wizard, causing loss of mana equivalent to the monster’s remaining HP and then the monster is respawned on the map.

## WAVES
The configuration file contains a list of waves in the “waves” attribute. Each wave occurs in the sequence it is provided there. During a wave, monsters spawn randomly on paths leading outside the map. A wave is defined in the config file with the following properties:

• pre_wave_pause: Number of seconds to wait before the wave begins. This begins counting after the duration of the previous wave has ended (or in the case of the first wave, immediately when the game starts).

• duration: Seconds during which this wave spawns monsters. The monsters will spawn randomly in equal intervals. For example, if the duration is 5 seconds, and there are a total of 25 monsters in this wave, there should be 5/25 = 0.2 seconds between each monster spawn, or at 60 frames per second (FPS), 12 frames in between monster spawns.

• monsters: A list of monster types to spawn during this wave. The wave may contain multiple different types of monsters, and will choose a type at random when spawning each one (as long as there is quantity of that type remaining for this wave). The total number of monsters in the
wave is the sum of quantities for all types provided in this list.

## MONSTERS
Monsters initially spawn outside the map on paths that join with the edge of the map. The spawn point is determined randomly out of all available locations. They travel on paths towards the Wizard’s house. The below configuration options define a monster type (as part of a wave):

• type: the sprite image to use for the monster (png file extension)\
• hp: amount of max hit points (initial health) the monster has\
• speed: how fast the monster’s movement is in pixels per frame\
• armour: percentage multiplier to damage received by this monster\
• mana_gained_on_kill: How much mana the wizard gains when this monster is killed

## TOWERS
Towers can be placed by the player in empty grass tiles, by first activating the tower action (either clicking on the T button in the right sidebar, or pressing the key ‘t’), then clicking on the space they want the tower to be placed. The following configuration options are relevant for towers:

• tower_cost: The cost in mana to place a tower. Depending on if upgrades are selected, the initial tower cost may have +20 (one upgrade), +40 (two upgrades) or +60 (three upgrades).

• initial_tower_range: The radius in pixels from the center of this tower within which it is able to target and shoot at monsters.

• initial_tower_firing_speed: Fireballs per second rate that this tower shoots at.

• initial_tower_damage: Amount of hitpoints deducted from a monster when a fireball from this tower hits it. If the monster’s health reaches 0, then it dies.

## TOWER UPGRADES
• Range upgrade: tower range radius increases by 1 tile (32 pixels)\
• Speed upgrade: Firing speed increases by 0.5 fireballs per second\
• Damage upgrade: Damage dealt increases by half of initial_tower_damage

Towers may be upgraded by the player by selecting the upgrade action for either range, speed or damage (either via clicking the option in the left sidebar, or pressing keys ‘1’, ‘2’ or ‘3’ respectively). Then, the player must click a tower to upgrade. Before clicking the tower, just by hovering over it, the cost of the upgrade will be shown in the bottom right corner of the screen.

## MANA
The wizard’s mana is visible in an aqua bar at the top of the screen. There is a mana cap – additional mana is not gained if the cap is reached. The initial values of these are set by the configuration options initial_mana and initial_mana_cap. A small trickle of mana is also gained as time passes, specified in the configuration attribute initial_mana_gained_per_second.

The mana pool spell is a special action that can be done at any time to increase the wizard’s mana cap and
add a multiplier to mana gained from monster kills and the mana trickle. The following configuration
options are relevant:

• mana_pool_spell_initial_cost: The initial cost of the spell, in mana.

• mana_pool_spell_cost_increase_per_use: How much the spell’s cost should increase after each use.

• mana_pool_spell_cap_multiplier: The multiplier to use for increasing the mana cap when the spell is cast (its effect is multiplied when activated multiple times).

• mana_pool_spell_mana_gained_multiplier: The multiplier to use for increasing mana gained from killing monsters and the mana trickle when the spell is cast. Its effect is added if the spell is cast multiple times (eg. 1.1 is +10% bonus to mana gained, which would become +20% if the spell is cast twice, or +30% if cast 3 times). 

## GOAL
The Wizard’s house is the destination for monsters. When a monster reaches the wizard’s house, the monster is ‘banished’ – mana equal to the monster’s remaining HP is expended in order to make the monster respawn and navigate the path again.

# GAMEPLAY ACTIONS
The gameplay actions available to the player are listed in the right sidebar. When the player hovers their mouse over one of these options (when unselected), it should turn grey. When selected, it should be highlighted yellow. If clicked while selected, it will be unselected. Multiple options may be selected at once. Each action has a corresponding key that also may be pressed to toggle the option. The actions are:

• Fast forward (FF) – key: f. The game will run at 2x speed – meaning anything that takes time is sped up, including monster movement, wave timers, tower firing speed, and mana increment. Unselecting this option will resume normal speed.

• Pause (P) – key: p. The game will be paused and in a frozen state. Unselecting this option resumes the game. While paused, the player is still able to build and upgrade towers or cast the mana pool spell.

• Build tower (T) – key: t. When selected, allows the player to click a grass tile on the map to place a tower there (provided they have enough mana). If they don’t click a grass tile, or don’t have enough mana, this selection has no effect. The cost should be displayed when the mouse hovers
over this option. The cost is only deducted when the tower is placed though, not when this option is selected.

• Upgrades (U1, U2, U3) – keys: 1,2,3. Allows the player to upgrade towers. May also be combined with build tower.

• Mana pool spell (M) – key: m. When selected, will activate the Mana Pool spell and then immediately deactivate. Cost should be displayed when the mouse hovers over it, and in the text description.

# TEST REPORT
To run the entire testing suite and generate the test report yourself run\
`gradle test`\
`gradle jacocotestreport`\
`open build/reports/jacoco/test/html/index.html`

or optionally, since the test report is already included just run\
`open build/reports/jacoco/test/html/index.html`
