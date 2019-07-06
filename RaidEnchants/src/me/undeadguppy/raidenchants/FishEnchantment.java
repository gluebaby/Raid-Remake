package me.undeadguppy.raidenchants;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.rit.sucy.CustomEnchantment;

public class FishEnchantment extends CustomEnchantment {

	// Items able to be enchanted with fish
	static final Material[] FISH_ITEMS = new Material[] { Material.DIAMOND_HELMET, Material.IRON_HELMET,
			Material.GOLD_HELMET, Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET };

	public FishEnchantment() {
		super("Fish", FISH_ITEMS, 2);

		// These are optional things you can change

		// This is the maximum level for your enchantment
		// Default is 1
		this.max = 1;

		// This changes the minimum modified level needed to get your enchant
		// On not normally enchanted items like blaze rods or pumpkin seeds, you
		// should keep it as 1
		// Default is 1
		this.base = 11;

		// This one changes how easily you can get higher tiers of the
		// enchantment
		// If you have a high maximum level, you probably want a small interval
		// Default is 10
		this.interval = 15;
	}

	@Override
	public void applyEquipEffect(Player user, int level) {
		if (level == 1) {
			user.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 9));
		}
	}

	@Override
	public void applyUnequipEffect(Player user, int level) {
		if (level == 1) {
			user.removePotionEffect(PotionEffectType.WATER_BREATHING);
		}
	}
}
