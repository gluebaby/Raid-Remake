package me.undeadguppy.raidenchants;

import org.bukkit.Material;

import com.rit.sucy.CustomEnchantment;

public class BoomEnchantment extends CustomEnchantment {

	static final Material[] BOOM_ITEMS = new Material[] { Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE,
			Material.GOLD_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE };

	public BoomEnchantment() {
		super("Boom", BOOM_ITEMS, 2);

		// These are optional things you can change

		// This is the maximum level for your enchantment
		// Default is 1
		this.max = 1;

		// This changes the minimum modified level needed to get your enchant
		// On not normally enchanted items like blaze rods or pumpkin seeds, you
		// should keep it as 1
		// Default is 1
		this.base = 1;

		// This one changes how easily you can get higher tiers of the
		// enchantment
		// If you have a high maximum level, you probably want a small interval
		// Default is 10
		this.interval = 15;
	}
}