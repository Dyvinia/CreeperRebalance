# CreeperRebalance
Simple server-side mod (works in singleplayer as well) that disables creeper explosion griefing, while also increasing explosion knockback to compensate.

## Config
The knockback amount for players and other entities are configurable, as well as the falloff for the exponent. You can also keep the normal creeper explosions if you want the knockback without disabling griefing.
```toml
# Prevents creeper explosions from breaking blocks. 
# Default: true
disableGriefing = true

# Multiplier for extra knockback caused by the explosion.
# Set to 0.0 for vanilla behavior.
# Default: 4.0
knockbackMult = 4.0

# Multiplier for extra knockback caused by the explosion to players.
# Set to -1.0 to default to the value of knockbackMult.
# Default: 3.5
playerKnockbackMult = 3.5

# Adjusts the upward velocity added by the explosion as part of its knockback.
# Default: 0.1
knockbackUp = 0.1

# Exponent used for calculating the falloff.
# Default: 2.5
falloffExponent = 2.5
```
